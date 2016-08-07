/*
 * Copyright 2016 Karl Bennett
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package shiver.me.timbers.retrying;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willAnswer;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class RetryerAspectTest {

    private Factory<RetryerService, OptionsService> retryerLoader;
    private Factory<OptionsService, Retry> optionsLoader;
    private RetryerAspect aspect;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        retryerLoader = mock(Factory.class);
        optionsLoader = mock(Factory.class);
        aspect = new RetryerAspect(retryerLoader, optionsLoader);
    }

    @Test
    public void Can_create_a_retryer_aspect() {
        new RetryerAspect();
    }

    @Test
    public void Call_isMethod_for_100_percent_coverage()
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        final Method isMethod = RetryerAspect.class.getDeclaredMethod("isMethod");
        isMethod.setAccessible(true);
        isMethod.invoke(aspect);
    }

    @Test
    public void Can_retry_for_method_in_class() throws Throwable {

        final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        final Retry retry = mock(Retry.class);

        final RetryerService retryerService = mock(RetryerService.class);
        final OptionsService options = mock(OptionsService.class);

        final Signature signature = mock(Signature.class);
        final String[] toString = new String[1];
        final Object[] success = new Object[1];
        final Object expected = new Object();

        // Given
        given(joinPoint.getSignature()).willReturn(signature);
        given(signature.getDeclaringType()).willReturn(Object.class);
        given(signature.getName()).willReturn(someString());
        given(optionsLoader.create(retry)).willReturn(options);
        given(retryerLoader.create(options)).willReturn(retryerService);
        willAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                final Until until = (Until) invocation.getArguments()[0];
                toString[0] = until.toString();
                return success[0] = until.success();
            }
        }).given(retryerService).retry(any(Until.class));
        given(joinPoint.proceed()).willReturn(expected);

        // When
        final Object actual = aspect.retryOnClass(joinPoint, retry);

        // Then
        assertThat(actual, is(success[0]));
        assertThat(actual, is(expected));
        assertThat(joinPoint.toString(), is(toString[0]));
    }

    @Test
    public void Can_retry_for_method() throws Throwable {

        final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        final Retry retry = mock(Retry.class);

        final RetryerService retryerService = mock(RetryerService.class);
        final OptionsService options = mock(OptionsService.class);

        final String[] toString = new String[1];
        final Object[] success = new Object[1];
        final Object expected = new Object();

        // Given
        given(optionsLoader.create(retry)).willReturn(options);
        given(retryerLoader.create(options)).willReturn(retryerService);
        willAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                final Until until = (Until) invocation.getArguments()[0];
                toString[0] = until.toString();
                return success[0] = until.success();
            }
        }).given(retryerService).retry(any(Until.class));
        given(joinPoint.proceed()).willReturn(expected);

        // When
        final Object actual = aspect.retryOnMethod(joinPoint, retry);

        // Then
        assertThat(actual, is(success[0]));
        assertThat(actual, is(expected));
        assertThat(joinPoint.toString(), is(toString[0]));
    }

    @Test
    public void A_new_retryer_is_loaded_every_time() throws Throwable {

        final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        final Retry retry = mock(Retry.class);

        final Signature signature = mock(Signature.class);
        final RetryerService retryerService = mock(RetryerService.class);
        final OptionsService options = mock(OptionsService.class);

        // Given
        given(joinPoint.getSignature()).willReturn(signature);
        given(signature.getDeclaringType()).willReturn(Object.class);
        given(signature.getName()).willReturn(someString());
        given(optionsLoader.create(retry)).willReturn(options);
        given(retryerLoader.create(options)).willReturn(retryerService);

        // When
        aspect.retryOnClass(joinPoint, retry);
        aspect.retryOnMethod(joinPoint, retry);
        aspect.retryOnClass(joinPoint, retry);

        // Then
        verify(optionsLoader, times(3)).create(retry);
        verify(retryerLoader, times(3)).create(options);
        verify(retryerService, times(3)).retry(any(Until.class));
    }

    @Test
    public void Will_ignore_class_annotation_if_method_is_annotated() throws Throwable {

        final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        final Retry retry = mock(Retry.class);

        final Signature signature = mock(Signature.class);
        @Retry
        class RetryMethodOverride {
            @Retry
            private void overrideRetryMethod() {
            }
        }

        final Object expected = new Object();

        // Given
        given(joinPoint.getSignature()).willReturn(signature);
        given(signature.getDeclaringType()).willReturn(RetryMethodOverride.class);
        given(signature.getName()).willReturn("overrideRetryMethod");
        given(joinPoint.proceed()).willReturn(expected);

        // When
        final Object actual = aspect.retryOnClass(joinPoint, retry);

        // Then
        assertThat(actual, is(expected));
        verifyZeroInteractions(optionsLoader, retryerLoader);
    }
}
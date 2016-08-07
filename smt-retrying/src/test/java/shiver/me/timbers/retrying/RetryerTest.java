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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static shiver.me.timbers.data.random.RandomIntegers.someIntegerBetween;

public class RetryerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void Can_create_a_default_retyer() {
        new Retryer();
    }

    @Test
    public void A_retryer_defers_its_option_choosing_until_the_retry_is_attempted() throws Throwable {

        final Options options = mock(Options.class);

        final Chooser chooser = mock(Chooser.class);

        // Given
        given(options.chooser()).willReturn(chooser);

        // When
        new Retryer(options);

        // Then
        verifyZeroInteractions(chooser);
    }

    @Test
    public void Can_retry_a_failed_execution() throws Throwable {

        final Options options = mock(Options.class);
        final Until until = mock(Until.class);

        final Chooser chooser = mock(Chooser.class);
        final Choice choice = mock(Choice.class);
        final Object expected = new Object();

        // Given
        given(options.chooser()).willReturn(chooser);
        given(chooser.choose()).willReturn(choice);
        given(choice.getRetries()).willReturn(2);
        given(until.success()).willThrow(new Exception()).willReturn(expected);

        // When
        final Object actual = new Retryer(options).retry(until);

        // Then
        assertThat(actual, is(expected));
        verify(until, times(2)).success();
    }

    @Test
    public void Can_retry_a_failed_execution_specific_number_of_times() throws Throwable {

        final Options options = mock(Options.class);
        final Until until = mock(Until.class);

        final Chooser chooser = mock(Chooser.class);
        final Choice choice = mock(Choice.class);
        final int retries = someIntegerBetween(1, 10);

        // Given
        given(options.chooser()).willReturn(chooser);
        given(chooser.choose()).willReturn(choice);
        given(choice.getRetries()).willReturn(retries);
        given(until.success()).willThrow(new Exception());

        // When
        try {
            new Retryer(options).retry(until);
        } catch (RuntimeException e) {
            // Don't care what the exception is.
        }

        // Then
        verify(until, times(retries)).success();
    }

    @Test
    public void Can_directly_throw_a_runtime_exception_for_a_failing_execution() throws Throwable {

        final Options options = mock(Options.class);
        final Until until = mock(Until.class);

        final Chooser chooser = mock(Chooser.class);
        final Choice choice = mock(Choice.class);
        final int retries = someIntegerBetween(1, 10);

        final RuntimeException exception = new RuntimeException();

        // Given
        given(options.chooser()).willReturn(chooser);
        given(chooser.choose()).willReturn(choice);
        given(choice.getRetries()).willReturn(retries);
        given(until.success()).willThrow(exception);
        expectedException.expect(is(exception));

        // When
        new Retryer(options).retry(until);
    }

    @Test
    public void Can_directly_throw_an_error_for_a_failing_execution() throws Throwable {

        final Options options = mock(Options.class);
        final Until until = mock(Until.class);

        final Chooser chooser = mock(Chooser.class);
        final Choice choice = mock(Choice.class);
        final int retries = someIntegerBetween(1, 10);

        final Error error = new Error();

        // Given
        given(options.chooser()).willReturn(chooser);
        given(chooser.choose()).willReturn(choice);
        given(choice.getRetries()).willReturn(retries);
        given(until.success()).willThrow(error);
        expectedException.expect(is(error));

        // When
        new Retryer(options).retry(until);
    }

    @Test
    public void Can_throw_a_wrapped_checked_exception_for_a_failing_execution() throws Throwable {

        final Options options = mock(Options.class);
        final Until until = mock(Until.class);

        final Chooser chooser = mock(Chooser.class);
        final Choice choice = mock(Choice.class);
        final int retries = someIntegerBetween(1, 10);

        final Exception exception = new Exception();

        // Given
        given(options.chooser()).willReturn(chooser);
        given(chooser.choose()).willReturn(choice);
        given(choice.getRetries()).willReturn(retries);
        given(until.success()).willThrow(exception);
        expectedException.expect(RetriedToManyTimesException.class);
        expectedException.expectCause(is(exception));

        // When
        new Retryer(options).retry(until);
    }
}
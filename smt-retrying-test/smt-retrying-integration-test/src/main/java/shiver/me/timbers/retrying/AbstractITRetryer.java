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

import org.junit.Test;
import shiver.me.timbers.retrying.execution.RetryerInterval;
import shiver.me.timbers.retrying.execution.RetryerRetries;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public abstract class AbstractITRetryer implements ITRetryer {

    private final AbstractITRetryerRetries retries = new AbstractITRetryerRetries() {
        @Override
        public RetryerRetries retries(int retries) {
            return AbstractITRetryer.this.retries(retries);
        }
    };

    private final AbstractITRetryerInterval interval = new AbstractITRetryerInterval() {
        @Override
        public RetryerInterval interval(long duration, TimeUnit unit) {
            return AbstractITRetryer.this.interval(duration, unit);
        }
    };

    @Test
    public void Can_retry_a_failed_execution() throws Throwable {

        final Callable callable = mock(Callable.class);

        final Object expected = new Object();

        // Given
        given(callable.call()).willThrow(new Exception()).willReturn(expected);

        // When
        final Object actual = defaults().defaultsMethod(callable);

        // Then
        assertThat(actual, is(expected));
        verify(callable, times(2)).call();
    }

    @Test
    @Override
    public void Can_set_the_number_of_retries() throws Throwable {
        retries.Can_set_the_number_of_retries();
    }

    @Test
    @Override
    public void Can_set_the_interval() throws Throwable {
        interval.Can_set_the_interval();
    }
}

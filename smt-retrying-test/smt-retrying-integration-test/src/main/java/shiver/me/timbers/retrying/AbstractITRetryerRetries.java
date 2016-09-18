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

import java.util.concurrent.Callable;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.retrying.random.RandomThrowables.someThrowable;
import static shiver.me.timbers.retrying.util.Constants.DEFAULT_RETRIES;

public abstract class AbstractITRetryerRetries implements ITRetryerRetries {

    @Test
    public void Can_set_the_number_of_retries() throws Throwable {

        final Callable callable = mock(Callable.class);

        final Throwable throwable = someThrowable();

        // Given
        given(callable.call()).willThrow(throwable);

        // When
        try {
            retries(DEFAULT_RETRIES).retryMethod(callable);
        } catch (Throwable e) {
            assertThat(e, is(throwable));
        }

        // Then
        verify(callable, times(DEFAULT_RETRIES)).call();
    }
}

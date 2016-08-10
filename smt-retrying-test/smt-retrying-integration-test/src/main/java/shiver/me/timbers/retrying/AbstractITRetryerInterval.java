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

import org.springframework.util.StopWatch;

import java.util.concurrent.Callable;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public abstract class AbstractITRetryerInterval implements ITRetryerInterval {

    @Override
    public void Can_set_the_interval() throws Throwable {

        final Callable callable = mock(Callable.class);
        final StopWatch stopWatch = new StopWatch();

        // Given
        given(callable.call()).willThrow(new Exception()).willReturn(new Object());
        stopWatch.start();

        // When
        interval(200L, MILLISECONDS).intervalMethod(callable);

        // Then
        stopWatch.stop();
        verify(callable, times(2)).call();
        assertThat(stopWatch.getLastTaskTimeMillis(), greaterThanOrEqualTo(200L));
    }
}

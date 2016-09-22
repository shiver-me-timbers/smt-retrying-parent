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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.retrying.util.Constants.DURATION1;
import static shiver.me.timbers.retrying.util.Constants.DURATION2;
import static shiver.me.timbers.retrying.util.Constants.DURATION3;
import static shiver.me.timbers.retrying.util.Constants.INCREMENT_RETRIES;

public abstract class AbstractITRetryerIntervalIncrements implements ITRetryerIntervalIncrements {

    @Override
    public void Can_set_the_interval_increments() throws Throwable {

        final StopWatch stopWatch = new StopWatch();
        final List<Long> intervals = new ArrayList<>();
        final RuntimeException exception = new RuntimeException();

        // Given
        stopWatch.start();
        final Callable callable = new RecordIncrements(stopWatch, intervals, exception);

        // When
        try {
            intervalIncrements(INCREMENT_RETRIES, MILLISECONDS, DURATION1, DURATION2, DURATION3)
                .intervalIncrementsMethod(callable);
        } catch (RuntimeException e) {
            if (e != exception) {
                throw e;
            }
        }

        // Then
        assertThat(intervals.get(1), greaterThanOrEqualTo(100L));
        assertThat(intervals.get(2), greaterThanOrEqualTo(200L));
        assertThat(intervals.get(3), greaterThanOrEqualTo(300L));
    }

    static class RecordIncrements implements Callable {

        private final StopWatch stopWatch;
        private final List<Long> intervals;
        private final RuntimeException exception;

        RecordIncrements(StopWatch stopWatch, List<Long> intervals, RuntimeException exception) {
            this.stopWatch = stopWatch;
            this.intervals = intervals;
            this.exception = exception;
        }

        @Override
        public Object call() throws Exception {
            stopWatch.stop();
            intervals.add(stopWatch.getLastTaskTimeMillis());
            stopWatch.start();
            throw exception;
        }
    }
}

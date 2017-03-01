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
import org.springframework.util.StopWatch;
import shiver.me.timbers.retrying.execution.RetryerIntervalIncrements;
import shiver.me.timbers.retrying.junit.RetryerPropertyRuleAware;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someAlphanumericString;
import static shiver.me.timbers.retrying.PropertyChoices.INTERVAL_DURATIONS_PROPERTY;
import static shiver.me.timbers.retrying.PropertyChoices.INTERVAL_UNIT_PROPERTY;
import static shiver.me.timbers.retrying.util.Constants.DURATION1;
import static shiver.me.timbers.retrying.util.Constants.DURATION2;
import static shiver.me.timbers.retrying.util.Constants.DURATION3;
import static shiver.me.timbers.retrying.util.Constants.INCREMENT_RETRIES;

public abstract class AbstractITRetryerIntervalIncrementsProperty extends AbstractITRetryerIntervalIncrements
    implements ITRetryerDefaults, RetryerPropertyRuleAware {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Override
    public RetryerIntervalIncrements intervalIncrements(final int retries, final TimeUnit unit, final Long... durations) {
        return new RetryerIntervalIncrements() {
            @Override
            public <T> T intervalIncrementsMethod(Callable<T> callable) throws Throwable {
                properties().setRetries(retries);
                properties().setIntervals(unit, durations);
                return defaults().defaultsMethod(callable);
            }
        };
    }

    @Test
    public void Can_override_the_interval_increments_system_properties() throws Throwable {

        final StopWatch stopWatch = new StopWatch();
        final List<Long> intervals = new ArrayList<>();
        final RuntimeException exception = new RuntimeException();

        // Given
        properties().setIntervals(SECONDS, 1L, 2L, 3L);
        stopWatch.start();
        final Callable callable = new RecordIncrements(stopWatch, intervals, exception);

        // When
        try {
            overrideIntervalIncrements(INCREMENT_RETRIES, MILLISECONDS, DURATION1, DURATION2, DURATION3)
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

    @Test
    public void Can_handle_invalid_interval_increments_time_unit_property() throws Throwable {

        final Callable callable = mock(Callable.class);

        final String invalidTimeUnit = someAlphanumericString();

        // Given
        properties().setProperty(INTERVAL_DURATIONS_PROPERTY, "1,2,3");
        properties().setProperty(INTERVAL_UNIT_PROPERTY, invalidTimeUnit);
        expectedException.expect(instanceOf(IllegalArgumentException.class));

        // When
        defaults().defaultsMethod(callable);
    }

    abstract RetryerIntervalIncrements overrideIntervalIncrements(int retries, TimeUnit unit, Long... durations);
}

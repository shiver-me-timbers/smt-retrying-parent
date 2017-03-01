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
import shiver.me.timbers.retrying.execution.RetryerInterval;
import shiver.me.timbers.retrying.junit.RetryerPropertyRuleAware;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someAlphanumericString;
import static shiver.me.timbers.retrying.PropertyChoices.INTERVAL_DURATIONS_PROPERTY;
import static shiver.me.timbers.retrying.PropertyChoices.INTERVAL_UNIT_PROPERTY;

public abstract class AbstractITRetryerIntervalProperty extends AbstractITRetryerInterval
    implements ITRetryerDefaults, RetryerPropertyRuleAware {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Override
    public RetryerInterval interval(final Long duration, final TimeUnit unit) {
        return new RetryerInterval() {
            @Override
            public <T> T intervalMethod(Callable<T> callable) throws Throwable {
                properties().setInterval(duration, unit);
                return defaults().defaultsMethod(callable);
            }
        };
    }

    @Test
    public void Can_override_the_interval_system_properties() throws Throwable {

        final Callable callable = mock(Callable.class);
        final StopWatch stopWatch = new StopWatch();

        // Given
        properties().setInterval(1L, SECONDS);
        given(callable.call()).willThrow(new Exception()).willReturn(new Object());
        stopWatch.start();

        // When
        overrideInterval(200L, MILLISECONDS).intervalMethod(callable);

        // Then
        stopWatch.stop();
        assertThat(stopWatch.getLastTaskTimeMillis(), greaterThanOrEqualTo(200L));
    }

    @Test
    public void Can_handle_invalid_interval_time_unit_property() throws Throwable {

        final Callable callable = mock(Callable.class);

        final String invalidTimeUnit = someAlphanumericString();

        // Given
        properties().setProperty(INTERVAL_DURATIONS_PROPERTY, "1");
        properties().setProperty(INTERVAL_UNIT_PROPERTY, invalidTimeUnit);
        expectedException.expect(instanceOf(IllegalArgumentException.class));

        // When
        defaults().defaultsMethod(callable);
    }

    protected abstract RetryerInterval overrideInterval(Long duration, TimeUnit unit);
}

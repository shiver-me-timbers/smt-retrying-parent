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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.util.StopWatch;

import static java.lang.String.format;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomIntegers.someNegativeInteger;
import static shiver.me.timbers.data.random.RandomIntegers.somePositiveInteger;
import static shiver.me.timbers.data.random.RandomLongs.someLongBetween;
import static shiver.me.timbers.data.random.RandomLongs.someNegativeLong;

public class ChoiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private Integer retries;
    private Time interval;

    @Before
    public void setUp() {
        retries = somePositiveInteger();
        interval = mock(Time.class);
    }

    @Test
    public void Can_get_the_number_of_retries() {

        // When
        final int actual = new Choice(retries, interval).getRetries();

        // Then
        assertThat(actual, is(retries));
    }

    @Test
    public void Can_sleep_for_the_interval() throws InterruptedException {

        final Time interval = mock(Time.class);
        final StopWatch stopWatch = new StopWatch();

        final Long duration = someLongBetween(200L, 300L);

        // Given
        given(interval.toMillis()).willReturn(duration);
        stopWatch.start();

        // When
        new Choice(retries, interval).sleepForInterval();

        // Then
        stopWatch.stop();
        assertThat(stopWatch.getLastTaskTimeMillis(), greaterThanOrEqualTo(duration));
    }

    @Test
    public void Cannot_set_a_retries_value_of_zero() {

        // Given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("The retries value must be greater than 1. The value (0) is invalid.");

        // When
        new Choice(0, interval);
    }

    @Test
    public void Cannot_set_a_negative_retries() {

        final Integer retries = someNegativeInteger();

        // Given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(
            format("The retries value must be greater than 1. The value (%s) is invalid.", retries)
        );

        // When
        new Choice(retries, interval);
    }

    @Test
    public void Cannot_set_a_negative_interval_duration() {

        final Time interval = mock(Time.class);

        // Given
        given(interval.toMillis()).willReturn(someNegativeLong());
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(
            format("The interval value must be greater than 0. The value (%s) is invalid.", interval)
        );

        // When
        new Choice(retries, interval);
    }
}
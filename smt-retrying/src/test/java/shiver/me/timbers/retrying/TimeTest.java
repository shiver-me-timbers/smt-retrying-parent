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
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomEnums.someEnum;
import static shiver.me.timbers.data.random.RandomLongs.someLong;
import static shiver.me.timbers.matchers.Matchers.hasField;

public class TimeTest {

    private TimeUnit unit;
    private Long duration1;
    private Long duration2;
    private Long duration3;
    private Time time;

    @Before
    public void setUp() {
        unit = someEnum(TimeUnit.class);
        duration1 = someLong();
        duration2 = someLong();
        duration3 = someLong();
        time = new Time(unit, duration1, duration2, duration3);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_start_intervals() {

        final List<Long> durations = mock(List.class);

        final Iterator<Long> iterator = mock(Iterator.class);

        // Given
        given(durations.iterator()).willReturn(iterator);

        // When
        final Intervals actual = new Time(durations, unit).startIntervals();

        // Then
        assertThat(actual, hasField("unit", unit));
        assertThat(actual, hasField("durations", iterator));
    }

    @Test
    public void Can_to_string_time() {

        // When
        final String actual = time.toString();

        // Then
        assertThat(
            actual,
            equalTo(format("Time{durations=%s, unit=%s}", asList(duration1, duration2, duration3), unit.name()))
        );
    }
}
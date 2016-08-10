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

import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomEnums.someEnum;
import static shiver.me.timbers.data.random.RandomLongs.someLong;

public class TimeTest {

    private Long duration;
    private TimeUnit unit;
    private Time time;

    @Before
    public void setUp() {
        duration = someLong();
        unit = someEnum(TimeUnit.class);
        time = new Time(duration, unit);
    }

    @Test
    public void Can_convert_time_to_milliseconds() {

        // When
        final long actual = time.toMillis();

        // Then
        assertThat(actual, equalTo(unit.toMillis(duration)));
    }

    @Test
    public void Can_to_string_time() {

        // When
        final String actual = time.toString();

        // Then
        assertThat(actual, equalTo(format("Time{duration=%s, unit=%s}", duration, unit.name())));
    }
}
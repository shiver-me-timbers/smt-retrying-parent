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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.retrying.PropertyChoices.EXCLUDES_PROPERTY;
import static shiver.me.timbers.retrying.PropertyChoices.INCLUDES_PROPERTY;
import static shiver.me.timbers.retrying.PropertyChoices.INTERVAL_DURATION_PROPERTY;
import static shiver.me.timbers.retrying.PropertyChoices.INTERVAL_UNIT_PROPERTY;
import static shiver.me.timbers.retrying.PropertyChoices.RETRIES_PROPERTY;

public class SystemPropertyChoicesTest {

    @Before
    public void setUp() {
        clearAllRetryerProperties();
    }

    @After
    public void tearDown() {
        clearAllRetryerProperties();
    }

    private static void clearAllRetryerProperties() {
        System.clearProperty(RETRIES_PROPERTY);
        System.clearProperty(INTERVAL_DURATION_PROPERTY);
        System.clearProperty(INTERVAL_UNIT_PROPERTY);
        System.clearProperty(INCLUDES_PROPERTY);
        System.clearProperty(EXCLUDES_PROPERTY);
    }

    @Test
    public void Can_get_the_retries_property() {

        final String expected = someString();

        // Given
        System.setProperty(RETRIES_PROPERTY, expected);

        // When
        final String actual = new SystemPropertyChoices().getRetriesProperty();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_get_the_interval_duration_property() {

        final String expected = someString();

        // Given
        System.setProperty(INTERVAL_DURATION_PROPERTY, expected);

        // When
        final String actual = new SystemPropertyChoices().getIntervalDurationProperty();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_get_the_interval_unit_property() {

        final String expected = someString();

        // Given
        System.setProperty(INTERVAL_UNIT_PROPERTY, expected);

        // When
        final String actual = new SystemPropertyChoices().getIntervalUnitProperty();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_get_the_includes_property() {

        final String expected = someString();

        // Given
        System.setProperty(INCLUDES_PROPERTY, expected);

        // When
        final String actual = new SystemPropertyChoices().getIncludesProperty();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_get_the_excludes_property() {

        final String expected = someString();

        // Given
        System.setProperty(EXCLUDES_PROPERTY, expected);

        // When
        final String actual = new SystemPropertyChoices().getExcludesProperty();

        // Then
        assertThat(actual, is(expected));
    }
}
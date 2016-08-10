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

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomEnums.someEnum;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;
import static shiver.me.timbers.data.random.RandomLongs.someLong;
import static shiver.me.timbers.matchers.Matchers.hasField;
import static shiver.me.timbers.retrying.PropertyChoices.INTERVAL_DURATION_PROPERTY;

public class AbstractPropertyChoicesTest {

    @Test
    public void Can_get_the_retries_property() {

        // Given
        final Integer expected = someInteger();

        // When
        final Integer actual = new AbstractPropertyChoices() {
            @Override
            String getRetriesProperty() {
                return expected.toString();
            }

            @Override
            String getIntervalDurationProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIntervalUnitProperty() {
                throw new UnsupportedOperationException();
            }
        }.getRetries();

        // Then
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void Can_get_no_retries_property() {

        // When
        final Integer actual = new AbstractPropertyChoices() {
            @Override
            String getRetriesProperty() {
                return null;
            }

            @Override
            String getIntervalDurationProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIntervalUnitProperty() {
                throw new UnsupportedOperationException();
            }
        }.getRetries();

        // Then
        assertThat(actual, nullValue());
    }

    @Test
    public void Can_get_the_interval_property() {

        // Given
        final Long duration = someLong();
        final TimeUnit unit = someEnum(TimeUnit.class);

        // When
        final Time actual = new AbstractPropertyChoices() {
            @Override
            String getRetriesProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIntervalDurationProperty() {
                return duration.toString();
            }

            @Override
            String getIntervalUnitProperty() {
                return unit.name();
            }
        }.getInterval();

        // Then
        assertThat(actual, hasField("duration", duration));
        assertThat(actual, hasField("unit", unit));
    }

    @Test
    public void Can_get_no_interval_property_if_the_duration_is_not_set() {

        // When
        final Time actual = new AbstractPropertyChoices() {
            @Override
            String getRetriesProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIntervalDurationProperty() {
                return null;
            }

            @Override
            String getIntervalUnitProperty() {
                return someEnum(TimeUnit.class).name();
            }
        }.getInterval();

        // Then
        assertThat(actual, nullValue());
    }

    @Test
    public void Can_get_no_interval_property_if_the_unit_is_not_set() {

        // Given
        System.setProperty(INTERVAL_DURATION_PROPERTY, someLong().toString());

        // When
        final Time actual = new AbstractPropertyChoices() {
            @Override
            String getRetriesProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIntervalDurationProperty() {
                return someLong().toString();
            }

            @Override
            String getIntervalUnitProperty() {
                return null;
            }
        }.getInterval();

        // Then
        assertThat(actual, nullValue());
    }
}
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

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomEnums.someEnum;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;
import static shiver.me.timbers.data.random.RandomLongs.someLong;
import static shiver.me.timbers.data.random.RandomStrings.someAlphanumericString;
import static shiver.me.timbers.matchers.Matchers.hasField;
import static shiver.me.timbers.retrying.PropertyChoices.INTERVAL_DURATIONS_PROPERTY;
import static shiver.me.timbers.retrying.random.RandomExceptions.someThrowable;

public class AbstractPropertyChoicesTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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

            @Override
            String getIncludesProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getExcludesProperty() {
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

            @Override
            String getIncludesProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getExcludesProperty() {
                throw new UnsupportedOperationException();
            }
        }.getRetries();

        // Then
        assertThat(actual, nullValue());
    }

    @Test
    public void Can_get_the_interval_property() {

        // Given
        final Long duration1 = someLong();
        final Long duration2 = someLong();
        final Long duration3 = someLong();
        final TimeUnit unit = someEnum(TimeUnit.class);

        // When
        final Time actual = new AbstractPropertyChoices() {
            @Override
            String getRetriesProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIntervalDurationProperty() {
                return format("%s,%s,%s", duration1, duration2, duration3);
            }

            @Override
            String getIntervalUnitProperty() {
                return unit.name();
            }

            @Override
            String getIncludesProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getExcludesProperty() {
                throw new UnsupportedOperationException();
            }
        }.getInterval();

        // Then
        assertThat(actual, hasField("durations", asList(duration1, duration2, duration3)));
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

            @Override
            String getIncludesProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getExcludesProperty() {
                throw new UnsupportedOperationException();
            }
        }.getInterval();

        // Then
        assertThat(actual, nullValue());
    }

    @Test
    public void Can_get_no_interval_property_if_the_duration_is_empty() {

        // When
        final Time actual = new AbstractPropertyChoices() {
            @Override
            String getRetriesProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIntervalDurationProperty() {
                return "";
            }

            @Override
            String getIntervalUnitProperty() {
                return someEnum(TimeUnit.class).name();
            }

            @Override
            String getIncludesProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getExcludesProperty() {
                throw new UnsupportedOperationException();
            }
        }.getInterval();

        // Then
        assertThat(actual, nullValue());
    }

    @Test
    public void Can_get_no_interval_property_if_the_unit_is_not_set() {

        // Given
        System.setProperty(INTERVAL_DURATIONS_PROPERTY, someLong().toString());

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

            @Override
            String getIncludesProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getExcludesProperty() {
                throw new UnsupportedOperationException();
            }
        }.getInterval();

        // Then
        assertThat(actual, nullValue());
    }

    @Test
    public void Can_get_no_interval_property_if_the_unit_is_empty() {

        // Given
        System.setProperty(INTERVAL_DURATIONS_PROPERTY, someLong().toString());

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
                return "";
            }

            @Override
            String getIncludesProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getExcludesProperty() {
                throw new UnsupportedOperationException();
            }
        }.getInterval();

        // Then
        assertThat(actual, nullValue());
    }

    @Test
    public void Can_get_the_includes_property() {

        // Given
        final Class<? extends Throwable> throwable1 = someThrowable().getClass();
        final Class<? extends Throwable> throwable2 = someThrowable().getClass();
        final Class<? extends Throwable> throwable3 = someThrowable().getClass();

        // When
        final Set<Class<? extends Throwable>> actual = new AbstractPropertyChoices() {
            @Override
            String getRetriesProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIntervalDurationProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIntervalUnitProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIncludesProperty() {
                return format("%s,%s,%s", throwable1.getName(), throwable2.getName(), throwable3.getName());
            }

            @Override
            String getExcludesProperty() {
                throw new UnsupportedOperationException();
            }
        }.getIncludes();

        // Then
        assertThat(actual, equalTo((Set) new HashSet<>(asList(throwable1, throwable2, throwable3))));
    }

    @Test
    public void Can_get_a_null_includes_property() {

        // When
        final Set<Class<? extends Throwable>> actual = new AbstractPropertyChoices() {
            @Override
            String getRetriesProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIntervalDurationProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIntervalUnitProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIncludesProperty() {
                return null;
            }

            @Override
            String getExcludesProperty() {
                throw new UnsupportedOperationException();
            }
        }.getIncludes();

        // Then
        assertThat(actual, empty());
    }

    @Test
    public void Can_get_an_empty_includes_property() {

        // When
        final Set<Class<? extends Throwable>> actual = new AbstractPropertyChoices() {
            @Override
            String getRetriesProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIntervalDurationProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIntervalUnitProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIncludesProperty() {
                return "";
            }

            @Override
            String getExcludesProperty() {
                throw new UnsupportedOperationException();
            }
        }.getIncludes();

        // Then
        assertThat(actual, empty());
    }

    @Test
    public void Cannot_get_an_invalid_includes_property() {

        final String invalidThrowable = someAlphanumericString(10);

        // Given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(format("Could not load the throwable class (%s).", invalidThrowable));
        expectedException.expectCause(isA(ClassNotFoundException.class));

        // When
        new AbstractPropertyChoices() {
            @Override
            String getRetriesProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIntervalDurationProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIntervalUnitProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIncludesProperty() {
                return format("%s,%s", someThrowable().getClass().getName(), invalidThrowable);
            }

            @Override
            String getExcludesProperty() {
                throw new UnsupportedOperationException();
            }
        }.getIncludes();
    }

    @Test
    public void Can_get_the_excludes_property() {

        // Given
        final Class<? extends Throwable> throwable1 = someThrowable().getClass();
        final Class<? extends Throwable> throwable2 = someThrowable().getClass();
        final Class<? extends Throwable> throwable3 = someThrowable().getClass();

        // When
        final Set<Class<? extends Throwable>> actual = new AbstractPropertyChoices() {
            @Override
            String getRetriesProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIntervalDurationProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIntervalUnitProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIncludesProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getExcludesProperty() {
                return format("%s,%s,%s", throwable1.getName(), throwable2.getName(), throwable3.getName());
            }
        }.getExcludes();

        // Then
        assertThat(actual, equalTo((Set) new HashSet<>(asList(throwable1, throwable2, throwable3))));
    }

    @Test
    public void Can_get_a_null_excludes_property() {

        // When
        final Set<Class<? extends Throwable>> actual = new AbstractPropertyChoices() {
            @Override
            String getRetriesProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIntervalDurationProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIntervalUnitProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIncludesProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getExcludesProperty() {
                return null;
            }
        }.getExcludes();

        // Then
        assertThat(actual, empty());
    }

    @Test
    public void Can_get_an_empty_excludes_property() {

        // When
        final Set<Class<? extends Throwable>> actual = new AbstractPropertyChoices() {
            @Override
            String getRetriesProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIntervalDurationProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIntervalUnitProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIncludesProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getExcludesProperty() {
                return "";
            }
        }.getExcludes();

        // Then
        assertThat(actual, empty());
    }

    @Test
    public void Cannot_get_an_invalid_excludes_property() {

        final String invalidThrowable = someAlphanumericString(10);

        // Given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(format("Could not load the throwable class (%s).", invalidThrowable));
        expectedException.expectCause(isA(ClassNotFoundException.class));

        // When
        new AbstractPropertyChoices() {
            @Override
            String getRetriesProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIntervalDurationProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIntervalUnitProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getIncludesProperty() {
                throw new UnsupportedOperationException();
            }

            @Override
            String getExcludesProperty() {
                return format("%s,%s", someThrowable().getClass().getName(), invalidThrowable);
            }
        }.getExcludes();
    }
}
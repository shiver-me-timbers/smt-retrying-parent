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

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomEnums.someEnum;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;
import static shiver.me.timbers.data.random.RandomLongs.someLong;
import static shiver.me.timbers.matchers.Matchers.hasField;
import static shiver.me.timbers.matchers.Matchers.hasProperty;
import static shiver.me.timbers.retrying.random.RandomExceptions.someThrowable;

public class OptionsTest {

    private DefaultChoices defaultChoices;
    private PropertyChoices propertyChoices;
    private ManualChoices<Options> manualChoices;
    private OptionsService options;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        defaultChoices = mock(DefaultChoices.class);
        propertyChoices = mock(PropertyChoices.class);
        manualChoices = mock(ManualChoices.class);
        options = new Options(defaultChoices, propertyChoices, manualChoices);
    }

    @Test
    public void Can_set_the_number_of_retries() {

        // Given
        final Integer retries = someInteger();

        // When
        final OptionsService actual = options.withRetries(retries);

        // Then
        assertThat(actual, hasField("retries", retries));
    }

    @Test
    public void Can_set_the_interval_time_between_retries() {

        // Given
        final Long duration = someLong();
        final TimeUnit unit = someEnum(TimeUnit.class);

        // When
        final OptionsService actual = options.withInterval(duration, unit);

        // Then
        assertThat(actual, hasProperty("interval.duration", duration));
        assertThat(actual, hasProperty("interval.unit", unit));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_set_the_included_exceptions_that_should_cause_a_retry() {

        // Given
        final Class<? extends Throwable> throwable1 = someThrowable().getClass();
        final Class<? extends Throwable> throwable2 = someThrowable().getClass();

        // When
        final OptionsService actual = options.includes(throwable1, throwable2);

        // Then
        assertThat(actual, hasField("includes", new HashSet<>(asList(throwable1, throwable2))));
    }

    @Test
    public void Can_choose_the_final_options() {

        final Choices manualOptionsChoices = mock(Choices.class);

        // Given
        given(manualChoices.apply((Options) options)).willReturn(manualOptionsChoices);

        // When
        final Chooser actual = ((Options) options).chooser();

        // Then
        assertThat(actual, hasField("defaultChoices", defaultChoices));
        assertThat(actual, hasField("propertyChoices", propertyChoices));
        assertThat(actual, hasField("choices", manualOptionsChoices));
    }
}
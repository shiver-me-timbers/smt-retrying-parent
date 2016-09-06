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
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.retrying.PropertyChoices.INCLUDES_PROPERTY;
import static shiver.me.timbers.retrying.PropertyChoices.INTERVAL_DURATION_PROPERTY;
import static shiver.me.timbers.retrying.PropertyChoices.INTERVAL_UNIT_PROPERTY;
import static shiver.me.timbers.retrying.PropertyChoices.RETRIES_PROPERTY;

@RunWith(MockitoJUnitRunner.class)
public class SpringPropertyChoicesTest {

    @Mock
    private ApplicationContext context;

    @InjectMocks
    private SpringPropertyChoices propertyChoices;

    @Test
    public void Can_get_the_retries_property() {

        final Environment environment = mock(Environment.class);
        final String expected = someString();

        // Given
        given(context.getEnvironment()).willReturn(environment);
        given(environment.getProperty(RETRIES_PROPERTY)).willReturn(expected);

        // When
        final String actual = propertyChoices.getRetriesProperty();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_get_the_interval_duration_property() {

        final Environment environment = mock(Environment.class);
        final String expected = someString();

        // Given
        given(context.getEnvironment()).willReturn(environment);
        given(environment.getProperty(INTERVAL_DURATION_PROPERTY)).willReturn(expected);

        // When
        final String actual = propertyChoices.getIntervalDurationProperty();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_get_the_interval_unit_property() {

        final Environment environment = mock(Environment.class);
        final String expected = someString();

        // Given
        given(context.getEnvironment()).willReturn(environment);
        given(environment.getProperty(INTERVAL_UNIT_PROPERTY)).willReturn(expected);

        // When
        final String actual = propertyChoices.getIntervalUnitProperty();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_get_the_includes_property() {

        final Environment environment = mock(Environment.class);
        final String expected = someString();

        // Given
        given(context.getEnvironment()).willReturn(environment);
        given(environment.getProperty(INCLUDES_PROPERTY)).willReturn(expected);

        // When
        final String actual = propertyChoices.getIncludesProperty();

        // Then
        assertThat(actual, is(expected));
    }
}
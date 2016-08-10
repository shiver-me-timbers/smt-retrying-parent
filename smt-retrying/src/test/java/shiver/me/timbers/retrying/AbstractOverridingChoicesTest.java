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

import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;
import static shiver.me.timbers.matchers.Matchers.hasField;

public class AbstractOverridingChoicesTest {

    private int retries;
    private Time interval;
    private AbstractOverridingChoices abstractChoices;

    @Before
    public void setUp() {
        retries = someInteger();
        interval = mock(Time.class);
        abstractChoices = new AbstractOverridingChoices() {
            @Override
            public Integer getRetries() {
                return retries;
            }

            @Override
            public Time getInterval() {
                return interval;
            }
        };
    }

    @Test
    public void Can_override_choices() {

        final Choices choices = mock(Choices.class);

        final int retries = someInteger();
        final Time interval = mock(Time.class);

        // Given
        given(choices.getRetries()).willReturn(retries);
        given(choices.getInterval()).willReturn(interval);

        // When
        final Choices actual = abstractChoices.overrideWith(choices);

        // Then
        assertThat(actual, hasField("retries", retries));
        assertThat(actual, hasField("interval", interval));
    }

    @Test
    public void Will_not_override_with_empty_choices() {

        final Choices choices = mock(Choices.class);

        // Given
        given(choices.getRetries()).willReturn(null);
        given(choices.getInterval()).willReturn(null);

        // When
        final Choices actual = abstractChoices.overrideWith(choices);

        // Then
        assertThat(actual, hasField("retries", retries));
        assertThat(actual, hasField("interval", interval));
    }
}
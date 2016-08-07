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

public class OptionsTest {

    private DefaultChoices defaultChoices;
    private PropertyChoices propertyChoices;
    private ManualChoices<Options> manualChoices;
    private Options options;

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
        final Options actual = options.withRetries(retries);

        // Then
        assertThat(actual, hasField("retries", retries));
    }

    @Test
    public void Can_choose_the_final_options() {

        final Choices manualOptionsChoices = mock(Choices.class);

        // Given
        given(manualChoices.apply(options)).willReturn(manualOptionsChoices);

        // When
        final Chooser actual = options.chooser();

        // Then
        assertThat(actual, hasField("defaultChoices", defaultChoices));
        assertThat(actual, hasField("propertyChoices", propertyChoices));
        assertThat(actual, hasField("choices", manualOptionsChoices));
    }
}
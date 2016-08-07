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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class CompositeOverridingChooserTest {

    @Test
    public void Can_choose_the_overriding_choices() {

        final DefaultChoices defaultChoices = mock(DefaultChoices.class);
        final PropertyChoices propertyChoices = mock(PropertyChoices.class);
        final Choices choices = mock(Choices.class);

        final Choices overriddenPropertyChoices = mock(Choices.class);
        final Choices overriddenDefaultChoices = mock(Choices.class);

        final Choice expected = mock(Choice.class);

        // Given
        given(propertyChoices.overrideWith(choices)).willReturn(overriddenPropertyChoices);
        given(defaultChoices.overrideWith(overriddenPropertyChoices)).willReturn(overriddenDefaultChoices);
        given(overriddenDefaultChoices.choose()).willReturn(expected);

        // When
        final Choice actual = new CompositeOverridingChooser(defaultChoices, propertyChoices, choices)
            .choose();

        // Then
        assertThat(actual, is(expected));
    }
}
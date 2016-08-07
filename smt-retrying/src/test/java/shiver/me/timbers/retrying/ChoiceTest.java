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

import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomIntegers.someNegativeInteger;
import static shiver.me.timbers.data.random.RandomIntegers.somePositiveInteger;

public class ChoiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void Can_get_the_number_of_retries() {

        // Given
        final int expected = somePositiveInteger();

        // When
        final int actual = new Choice(expected).getRetries();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Cannot_only_set_a_retries_value_of_zero() {

        // Given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("The retries value must be greater than 1. The value (0) is invalid.");

        // When
        new Choice(0);
    }

    @Test
    public void Cannot_only_set_a_negative_retries() {

        final Integer retries = someNegativeInteger();

        // Given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(
            format("The retries value must be greater than 1. The value (%s) is invalid.", retries)
        );

        // When
        new Choice(retries);
    }
}
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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;
import static shiver.me.timbers.retrying.PropertyChoices.RETRIES_PROPERTY;

public class SystemPropertyChoicesTest {

    @Test
    public void Can_get_the_retries_property() {

        final Integer expected = someInteger();

        // Given
        System.setProperty(RETRIES_PROPERTY, expected.toString());

        // When
        final Integer actual = new SystemPropertyChoices().getRetries();

        // Then
        System.clearProperty(RETRIES_PROPERTY);
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void Can_get_no_retries_property() {

        // When
        final Integer actual = new SystemPropertyChoices().getRetries();

        // Then
        assertThat(actual, nullValue());
    }
}
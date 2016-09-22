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

package shiver.me.timbers.retrying.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomStrings.someAlphanumericString;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.data.random.RandomThings.someThing;
import static shiver.me.timbers.retrying.util.Strings.concat;
import static shiver.me.timbers.retrying.util.Strings.toStrings;

public class StringsTest {

    @Test
    public void Can_concatenate_some_strings() {

        // Given
        final String delimiter = someString(1, ",./;'|");
        final String string1 = someAlphanumericString(2);
        final String string2 = someAlphanumericString(3);
        final String string3 = someAlphanumericString(5);
        final String string4 = someAlphanumericString(8);
        final String string5 = someAlphanumericString(13);

        // When
        final String actual = concat(delimiter, new ArrayList<>(asList(string1, string2, string3, string4, string5)));

        // Then
        assertThat(actual, equalTo(format(
            "%s%s%s%s%s%s%s%s%s",
            string1, delimiter, string2, delimiter, string3, delimiter, string4, delimiter, string5
        )));
    }

    @Test
    public void Can_convert_some_objects_to_a_list_of_strings() {

        // Given
        final Object object1 = someThing();
        final Object object2 = someThing();
        final Object object3 = someThing();

        // When
        final List<String> actual = toStrings(object1, object2, object3);

        // Then
        assertThat(actual, equalTo(asList(object1.toString(), object2.toString(), object3.toString())));
    }
}
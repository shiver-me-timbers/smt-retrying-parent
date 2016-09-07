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

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomDoubles.someDouble;
import static shiver.me.timbers.data.random.RandomFloats.someFloat;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;
import static shiver.me.timbers.data.random.RandomLongs.someLong;
import static shiver.me.timbers.data.random.RandomShorts.someShort;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.retrying.util.Classes.toClassNames;
import static shiver.me.timbers.retrying.util.Classes.toClasses;

public class ClassesTest {

    @Test
    public void Can_convert_objects_to_classes() {

        // Given
        final List objects = asList(
            (Object) someShort(), someInteger(), someFloat(), someLong(), someDouble(), someString()
        );

        // When
        final List<Class> actual = toClasses(objects);

        // Then
        assertThat(actual, hasSize(objects.size()));
        for (Object object : objects) {
            assertThat(actual, hasItem(object.getClass()));
        }
    }

    @Test
    public void Can_convert_objects_to_class_names() {

        // Given
        final List objects = asList(
            (Object) someShort(), someInteger(), someFloat(), someLong(), someDouble(), someString()
        );

        // When
        final List<String> actual = toClassNames(objects);

        // Then
        assertThat(actual, hasSize(objects.size()));
        for (Object object : objects) {
            assertThat(actual, hasItem(object.getClass().getName()));
        }
    }
}
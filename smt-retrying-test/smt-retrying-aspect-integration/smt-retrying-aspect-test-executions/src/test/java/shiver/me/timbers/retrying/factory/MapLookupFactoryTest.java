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

package shiver.me.timbers.retrying.factory;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomDoubles.someDouble;
import static shiver.me.timbers.data.random.RandomFloats.someFloat;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;
import static shiver.me.timbers.data.random.RandomLongs.someLong;
import static shiver.me.timbers.data.random.RandomShorts.someShort;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.data.random.RandomThings.someThings;

public class MapLookupFactoryTest {

    private Map<Object, Object> map;
    private MapLookupFactory<Object> factory;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        map = mock(Map.class);
        factory = new MapLookupFactory<>(map);
    }

    @Test
    public void Can_lookup_a_class() {

        final List args = someThings(someShort(), someInteger(), someFloat(), someLong(), someDouble(), someString())
            .list();

        final Object expected = new Object();

        // Given
        given(map.get(args)).willReturn(expected);

        // When
        final Object actual = factory.find(args.toArray(new Object[args.size()]));

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = IllegalArgumentException.class)
    public void Can_fail_to_lookup_a_class() {

        final List args = someThings(someShort(), someInteger(), someFloat(), someLong(), someDouble(), someString())
            .list();

        // Given
        given(map.get(args)).willReturn(null);

        // When
        factory.find(args.toArray(new Object[args.size()]));
    }

    @Test
    public void Can_add_a_class() {

        // Given
        final List args = someThings(someShort(), someInteger(), someFloat(), someLong(), someDouble(), someString())
            .list();
        final Object object = new Object();

        // When
        factory.add(object, args.toArray(new Object[args.size()]));

        // Then
        verify(map).put(args, object);
    }
}
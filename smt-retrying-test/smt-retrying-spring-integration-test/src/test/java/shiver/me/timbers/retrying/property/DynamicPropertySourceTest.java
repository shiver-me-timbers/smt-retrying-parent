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

package shiver.me.timbers.retrying.property;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class DynamicPropertySourceTest {

    private Map<String, String> map;
    private DynamicPropertySource propertySource;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        map = mock(Map.class);
        propertySource = new DynamicPropertySource(map);
    }

    @Test
    public void Can_get_a_property() {

        final String key = someString();

        final String expected = someString();

        // Given
        given(map.get(key)).willReturn(expected);

        // When
        final String actual = propertySource.getProperty(key);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_set_a_property() {

        // Given
        final String key = someString();
        final String value = someString();

        // When
        propertySource.setProperty(key, value);

        // Then
        verify(map).put(key, value);
    }

    @Test
    public void Can_restore_all_properties() {

        // When
        propertySource.restoreProperties();

        // Then
        verify(map).clear();
    }
}
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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomStrings.someAlphanumericString;

public class SystemPropertyManagerTest {

    private PropertyManager propertyManager;

    @Before
    public void setUp() {
        propertyManager = new SystemPropertyManager();
    }

    @After
    public void tearDown() {
        propertyManager.restoreProperties();
    }

    @Test
    public void Can_set_a_property() {

        // Given
        String key = someAlphanumericString(5);
        String value = someAlphanumericString(8);

        // When
        propertyManager.setProperty(key, value);

        // Then
        assertThat(System.getProperty(key), is(value));
    }

    @Test
    public void Can_restore_a_new_property() {

        // Given
        String key = someAlphanumericString(5);
        String value = someAlphanumericString(8);

        // When
        propertyManager.setProperty(key, value);
        propertyManager.restoreProperties();

        // Then
        assertThat(System.getProperties().containsKey(key), is(false));
    }

    @Test
    public void Can_restore_an_existing_property() {

        // Given
        String key = someAlphanumericString(5);
        String value = someAlphanumericString(8);
        System.setProperty(key, value);

        // When
        propertyManager.setProperty(key, someAlphanumericString(13));
        propertyManager.restoreProperties();

        // Then
        assertThat(System.getProperties().containsKey(key), is(true));
        assertThat(System.getProperty(key), is(value));
        System.clearProperty(key);
    }
}
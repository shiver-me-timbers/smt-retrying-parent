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
import static org.junit.Assert.assertThat;

public class ServiceClassLoaderTest {

    @Test
    public void Can_load_a_service() {

        // Given
        final ServiceClassLoader<RegisteredService> loader = new ServiceClassLoader<>(RegisteredService.class, null);

        // When
        final Class actual = loader.load();

        // Then
        assertThat(actual, equalTo((Class) TestRegisteredService.class));
    }

    @Test
    public void Cannot_load_a_service_that_has_not_been_registered() {

        // Given
        final ServiceClassLoader<UnregisteredService> loader =
            new ServiceClassLoader<>(UnregisteredService.class, TestUnregisteredService.class);

        // When
        final Class actual = loader.load();

        // Then
        assertThat(actual, equalTo((Class) TestUnregisteredService.class));
    }
}
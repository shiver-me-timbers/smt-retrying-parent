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

import org.junit.Test;
import shiver.me.timbers.retrying.execution.RetryerDefaults;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class RetryerDefaultsFactoryTest {

    @Test
    public void Can_create_a_retryer_defaults() {

        // Given
        final RetryerDefaults expected = mock(RetryerDefaults.class);

        // When
        final RetryerDefaults actual = new RetryerDefaultsFactory(expected).create();

        // Then
        assertThat(actual, is(expected));
    }
}
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
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;

public class OptionsManualChoicesTest {

    @Test
    public void Can_apply_the_manual_chices_set_in_the_options() {

        final Options options = mock(Options.class);

        final int retries = someInteger();
        final Time interval = mock(Time.class);

        // Given
        given(options.getRetries()).willReturn(retries);
        given(options.getInterval()).willReturn(interval);

        // When
        final BasicChoices actual = new OptionsManualChoices().apply(options);

        // Then
        assertThat(actual.getRetries(), is(retries));
        assertThat(actual.getInterval(), is(interval));
    }
}
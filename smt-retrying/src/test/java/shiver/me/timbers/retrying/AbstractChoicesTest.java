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

import java.util.Set;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomIntegers.somePositiveInteger;
import static shiver.me.timbers.matchers.Matchers.hasField;

public class AbstractChoicesTest {

    @Test
    @SuppressWarnings("unchecked")
    public void Can_choose_the_current_choices() {

        // Given
        final Integer retries = somePositiveInteger();
        final Time interval = mock(Time.class);
        final Set<Class<? extends Throwable>> includes = mock(Set.class);
        final Set<Class<? extends Throwable>> excludes = mock(Set.class);

        // When
        final Choice actual = new AbstractChoices() {
            @Override
            public Integer getRetries() {
                return retries;
            }

            @Override
            public Time getInterval() {
                return interval;
            }

            @Override
            public Set<Class<? extends Throwable>> getIncludes() {
                return includes;
            }

            @Override
            public Set<Class<? extends Throwable>> getExcludes() {
                return excludes;
            }
        }.choose();

        // Then
        assertThat(actual, hasField("retries", retries));
        assertThat(actual, hasField("interval", interval));
        assertThat(actual, hasField("includes", includes));
        assertThat(actual, hasField("excludes", excludes));
    }
}
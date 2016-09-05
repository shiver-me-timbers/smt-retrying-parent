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

import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;

public class BasicChoicesTest {

    private Integer retries;
    private Time interval;
    private BasicChoices choices;
    private Set<Class<? extends Throwable>> includes;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        retries = someInteger();
        interval = mock(Time.class);
        includes = mock(Set.class);
        choices = new BasicChoices(retries, interval, includes);
    }

    @Test
    public void Can_get_the_retries() {

        // When
        final Integer actual = choices.getRetries();

        // Then
        assertThat(actual, is(retries));
    }

    @Test
    public void Can_get_the_interval() {

        // When
        final Time actual = choices.getInterval();

        // Then
        assertThat(actual, is(interval));
    }

    @Test
    public void Can_get_the_includes() {

        // When
        final Set<Class<? extends Throwable>> actual = choices.getIncludes();

        // Then
        assertThat(actual, is(includes));
    }
}
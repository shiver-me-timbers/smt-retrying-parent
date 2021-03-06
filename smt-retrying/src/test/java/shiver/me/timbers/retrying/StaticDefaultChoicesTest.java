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
import static shiver.me.timbers.retrying.StaticDefaultChoices.DEFAULT_EXCLUDES;
import static shiver.me.timbers.retrying.StaticDefaultChoices.DEFAULT_INCLUDES;
import static shiver.me.timbers.retrying.StaticDefaultChoices.DEFAULT_INTERVAL;
import static shiver.me.timbers.retrying.StaticDefaultChoices.DEFAULT_RETRIES;

public class StaticDefaultChoicesTest {

    private StaticDefaultChoices choices;

    @Before
    public void setUp() {
        choices = new StaticDefaultChoices();
    }

    @Test
    public void Can_get_default_retries() {

        // When
        final Integer actual = choices.getRetries();

        // Then
        assertThat(actual, is(DEFAULT_RETRIES));
    }

    @Test
    public void Can_get_default_interval() {

        // When
        final Time actual = choices.getInterval();

        // Then
        assertThat(actual, is(DEFAULT_INTERVAL));
    }

    @Test
    public void Can_get_default_includes() {

        // When
        final Set<Class<? extends Throwable>> actual = choices.getIncludes();

        // Then
        assertThat(actual, is(DEFAULT_INCLUDES));
    }

    @Test
    public void Can_get_default_excludes() {

        // When
        final Set<Class<? extends Throwable>> actual = choices.getExcludes();

        // Then
        assertThat(actual, is(DEFAULT_EXCLUDES));
    }
}
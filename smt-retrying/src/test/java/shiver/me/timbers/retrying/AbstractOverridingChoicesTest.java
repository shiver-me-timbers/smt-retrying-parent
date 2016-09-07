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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;
import static shiver.me.timbers.matchers.Matchers.hasField;
import static shiver.me.timbers.retrying.random.RandomExceptions.someOtherThrowable;
import static shiver.me.timbers.retrying.random.RandomExceptions.someThrowable;

public class AbstractOverridingChoicesTest {

    private int retries;
    private Time interval;
    private Set<Class<? extends Throwable>> includes;
    private AbstractOverridingChoices abstractChoices;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        retries = someInteger();
        interval = mock(Time.class);
        includes = mock(Set.class);
        abstractChoices = new AbstractOverridingChoices() {
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
        };
    }

    @Test
    public void Can_override_choices() {

        final Choices choices = mock(Choices.class);

        final int retries = someInteger();
        final Time interval = mock(Time.class);

        // Given
        given(choices.getRetries()).willReturn(retries);
        given(choices.getInterval()).willReturn(interval);

        // When
        final Choices actual = abstractChoices.overrideWith(choices);

        // Then
        assertThat(actual, hasField("retries", retries));
        assertThat(actual, hasField("interval", interval));
    }

    @Test
    public void Will_not_override_with_null_choices() {

        final Choices choices = mock(Choices.class);

        // Given
        given(choices.getRetries()).willReturn(null);
        given(choices.getInterval()).willReturn(null);

        // When
        final Choices actual = abstractChoices.overrideWith(choices);

        // Then
        assertThat(actual, hasField("retries", retries));
        assertThat(actual, hasField("interval", interval));
    }

    @Test
    public void Can_add_choices() {

        final Choices choices = mock(Choices.class);

        final HashSet<Class<? extends Throwable>> currentIncludes = new HashSet<>(asList(
            someOtherThrowable().getClass(), someOtherThrowable().getClass()
        ));
        final HashSet<Class<? extends Throwable>> originalCurrentIncludes = new HashSet<>(currentIncludes);
        final Set<Class<? extends Throwable>> additionalIncludes = new HashSet<>(asList(
            someThrowable().getClass(), someThrowable().getClass()
        ));
        final Set<Class<? extends Throwable>> expected = new HashSet<>(currentIncludes);
        expected.addAll(additionalIncludes);

        // Given
        given(includes.iterator()).willReturn(currentIncludes.iterator());
        given(choices.getIncludes()).willReturn(additionalIncludes);

        // When
        final Choices actual = abstractChoices.overrideWith(choices);

        // Then
        assertThat(actual, hasField("includes", expected));
        // The addition of the includes should not mutate the includes within the current choices.
        assertThat(currentIncludes, equalTo(originalCurrentIncludes));
    }

    @Test
    public void Will_not_add_null_includes() {

        final Choices choices = mock(Choices.class);

        // Given
        given(choices.getIncludes()).willReturn(null);

        // When
        final Choices actual = abstractChoices.overrideWith(choices);

        // Then
        verifyZeroInteractions(includes);
        assertThat(actual, hasField("includes", includes));
    }

    @Test
    public void Will_not_add_empty_includes() {

        final Choices choices = mock(Choices.class);

        // Given
        given(choices.getIncludes()).willReturn(Collections.<Class<? extends Throwable>>emptySet());

        // When
        final Choices actual = abstractChoices.overrideWith(choices);

        // Then
        verifyZeroInteractions(includes);
        assertThat(actual, hasField("includes", includes));
    }
}
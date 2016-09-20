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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomIntegers.someNegativeInteger;
import static shiver.me.timbers.data.random.RandomIntegers.somePositiveInteger;
import static shiver.me.timbers.retrying.random.RandomExceptions.someOtherThrowable;
import static shiver.me.timbers.retrying.random.RandomExceptions.someThrowable;

public class ChoiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Integer retries;
    private Time interval;
    private Set<Class<? extends Throwable>> includes;
    private Set<Class<? extends Throwable>> excludes;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        retries = somePositiveInteger();
        interval = mock(Time.class);
        includes = mock(Set.class);
        excludes = mock(Set.class);
    }

    @Test
    public void Can_get_the_number_of_retries() {

        // When
        final int actual = new Choice(retries, interval, includes, excludes).getRetries();

        // Then
        assertThat(actual, is(retries));
    }

    @Test
    public void Can_get_the_intervals() {

        final Intervals expected = mock(Intervals.class);

        // Given
        given(interval.startIntervals()).willReturn(expected);

        // When
        final Intervals actual = new Choice(retries, interval, includes, excludes).getIntervals();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Cannot_set_a_retries_value_of_zero() {

        // Given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("The retries value must be greater than 1. The value (0) is invalid.");

        // When
        new Choice(0, interval, includes, excludes);
    }

    @Test
    public void Cannot_set_a_negative_retries() {

        final Integer retries = someNegativeInteger();

        // Given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(
            format("The retries value must be greater than 1. The value (%s) is invalid.", retries)
        );

        // When
        new Choice(retries, interval, includes, excludes);
    }

    @Test
    public void Can_check_if_an_included_exception_is_suppressed() {

        // Given
        final Throwable exception = someOtherThrowable();

        // When
        final boolean actual = new Choice(
            retries,
            interval,
            new HashSet<>(asList(someThrowable().getClass(), exception.getClass(), someThrowable().getClass())),
            Collections.<Class<? extends Throwable>>emptySet()
        ).isSuppressed(exception);

        // Then
        assertThat(actual, is(true));
    }

    @Test
    public void Can_check_if_an_exception_that_is_not_included_is_not_suppressed() {

        // Given
        final Throwable exception = someOtherThrowable();

        // When
        final boolean actual = new Choice(
            retries,
            interval,
            new HashSet<>(asList(someThrowable().getClass(), someThrowable().getClass(), someThrowable().getClass())),
            Collections.<Class<? extends Throwable>>emptySet()
        ).isSuppressed(exception);

        // Then
        assertThat(actual, is(false));
    }

    @Test
    public void Can_check_if_an_excluded_exception_is_not_suppressed() {

        // Given
        final Throwable exception = someOtherThrowable();

        // When
        final boolean actual = new Choice(
            retries,
            interval,
            Collections.<Class<? extends Throwable>>emptySet(),
            new HashSet<>(asList(someThrowable().getClass(), exception.getClass(), someThrowable().getClass()))
        ).isSuppressed(exception);

        // Then
        assertThat(actual, is(false));
    }

    @Test
    public void Can_check_if_an_exception_that_is_not_excluded_is_suppressed() {

        // Given
        final Throwable exception = someOtherThrowable();

        // When
        final boolean actual = new Choice(
            retries,
            interval,
            Collections.<Class<? extends Throwable>>emptySet(),
            new HashSet<>(asList(someThrowable().getClass(), someThrowable().getClass(), someThrowable().getClass()))
        ).isSuppressed(exception);

        // Then
        assertThat(actual, is(true));
    }

    @Test
    public void Can_check_if_an_exception_that_is_included_and_excluded_is_not_suppressed() {

        // Given
        final Throwable exception = someOtherThrowable();

        // When
        final boolean actual = new Choice(
            retries,
            interval,
            new HashSet<>(asList(someOtherThrowable().getClass(), exception.getClass(), someOtherThrowable().getClass())),
            new HashSet<>(asList(someThrowable().getClass(), exception.getClass(), someThrowable().getClass()))
        ).isSuppressed(exception);

        // Then
        assertThat(actual, is(false));
    }

    @Test
    public void Can_check_that_all_exceptions_are_suppressed_if_no_includes_or_excludes_are_set() {

        // Given
        final Throwable exception = someThrowable();

        // When
        final boolean actual = new Choice(
            retries,
            interval,
            Collections.<Class<? extends Throwable>>emptySet(),
            Collections.<Class<? extends Throwable>>emptySet()
        ).isSuppressed(exception);

        // Then
        assertThat(actual, is(true));
    }
}
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
import shiver.me.timbers.retrying.execution.PropertyIncludesWithExcludesRetryerExcludes;
import shiver.me.timbers.retrying.execution.RetryerIncludes;
import shiver.me.timbers.retrying.junit.RetryerPropertyRuleAware;

import java.util.List;
import java.util.concurrent.Callable;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.retrying.random.RandomThrowables.SOME_OTHER_THROWABLES;
import static shiver.me.timbers.retrying.random.RandomThrowables.someThrowable;
import static shiver.me.timbers.retrying.util.Constants.DEFAULT_RETRIES;

public abstract class AbstractITRetryerIncludesProperty extends AbstractITRetryerIncludes
    implements ITRetryerDefaults, RetryerPropertyRuleAware {

    @Override
    public RetryerIncludes includes(final int retries, final Throwable... includes) {
        return new RetryerIncludes() {
            @Override
            public <T> T includeMethod(Callable<T> callable) throws Throwable {
                properties().setRetries(retries);
                properties().setIncludes(includes);
                return defaults().defaultsMethod(callable);
            }
        };
    }

    @Override
    public RetryerIncludes includesWithExcludes(
        final int retries,
        final List<Throwable> includes,
        final List<Throwable> excludes
    ) {
        return new PropertyIncludesWithExcludesRetryerExcludes(properties(), retries, includes, excludes, this);
    }

    protected abstract RetryerIncludes addInclude(final int retries, Throwable include);

    @Test
    public void Can_set_multiple_includes_with_a_system_property() throws Throwable {

        final Callable callable = mock(Callable.class);

        final Throwable exception1 = someThrowable();
        final Throwable exception2 = someThrowable();

        final Object expected = new Object();

        // Given
        properties().setRetries(DEFAULT_RETRIES);
        properties().setIncludes(exception1, exception2);
        given(callable.call()).willThrow(exception1).willThrow(exception2).willReturn(expected);

        // When
        final Object actual = defaults().defaultsMethod(callable);

        // Then
        assertThat(actual, is(expected));
        verify(callable, times(3)).call();
    }

    @Test
    public void Can_add_an_extra_include_to_those_set_with_the_system_property() throws Throwable {

        final Callable callable = mock(Callable.class);

        final Throwable exception1 = someThrowable();
        final Throwable exception2 = SOME_OTHER_THROWABLES[0];

        final Object expected = new Object();

        // Given
        properties().setIncludes(exception1);
        given(callable.call()).willThrow(exception1).willThrow(exception2).willReturn(expected);

        // When
        final Object actual = addInclude(DEFAULT_RETRIES, exception2).includeMethod(callable);

        // Then
        assertThat(actual, is(expected));
        verify(callable, times(3)).call();
    }
}

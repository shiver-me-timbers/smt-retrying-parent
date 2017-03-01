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
import shiver.me.timbers.retrying.execution.RetryerExcludes;
import shiver.me.timbers.retrying.junit.RetryerPropertyRuleAware;

import java.util.List;
import java.util.concurrent.Callable;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.retrying.random.RandomThrowables.SOME_OTHER_THROWABLES;
import static shiver.me.timbers.retrying.random.RandomThrowables.someOtherThrowable;
import static shiver.me.timbers.retrying.random.RandomThrowables.someThrowable;
import static shiver.me.timbers.retrying.util.Constants.DEFAULT_RETRIES;

public abstract class AbstractITRetryerExcludesProperty extends AbstractITRetryerExcludes
    implements ITRetryerDefaults, RetryerPropertyRuleAware {

    @Override
    public RetryerExcludes excludes(final int retries, final Throwable... excludes) {
        return new RetryerExcludes() {
            @Override
            public <T> T excludeMethod(Callable<T> callable) throws Throwable {
                properties().setRetries(retries);
                properties().setExcludes(excludes);
                return defaults().defaultsMethod(callable);
            }
        };
    }

    @Override
    public RetryerExcludes excludesWithIncludes(
        final int retries,
        final List<Throwable> excludes,
        final List<Throwable> includes
    ) {
        return new PropertyIncludesWithExcludesRetryerExcludes(properties(), retries, includes, excludes, this);
    }

    protected abstract RetryerExcludes addExclude(int retries, Throwable exclude);

    @Test
    public void Can_set_multiple_excludes_with_a_system_property() throws Throwable {

        final Callable callable = mock(Callable.class);

        final Throwable expected = someOtherThrowable();

        // Given
        properties().setRetries(DEFAULT_RETRIES);
        properties().setExcludes(someThrowable(), expected);
        given(callable.call()).willThrow(expected);

        // When
        try {
            defaults().defaultsMethod(callable);
        } catch (Throwable t) {
            assertThat(t, isA((Class) expected.getClass()));
        }

        // Then
        verify(callable).call();
    }

    @Test
    public void Can_add_an_extra_exclude_to_those_set_with_the_system_property() throws Throwable {

        final Callable callable = mock(Callable.class);

        final Throwable expected = SOME_OTHER_THROWABLES[0];

        // Given
        properties().setExcludes(someThrowable());
        given(callable.call()).willThrow(expected);

        // When
        try {
            addExclude(DEFAULT_RETRIES, expected).excludeMethod(callable);
        } catch (Throwable t) {
            assertThat(t, is(expected));
        }

        // Then
        verify(callable).call();
    }
}

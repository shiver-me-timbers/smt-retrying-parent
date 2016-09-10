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

package shiver.me.timbers.retrying.util;

import org.junit.Test;
import shiver.me.timbers.retrying.Options;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static shiver.me.timbers.retrying.random.RandomThrowables.someOtherThrowable;
import static shiver.me.timbers.retrying.random.RandomThrowables.someThrowable;
import static shiver.me.timbers.retrying.util.IncludesExcludes.addExcludes;
import static shiver.me.timbers.retrying.util.IncludesExcludes.addIncludes;

public class IncludesExcludesTest {

    @Test
    @SuppressWarnings("unchecked")
    public void Can_add_includes() {

        // Given
        final Options options = mock(Options.class);
        final Throwable throwable1 = someThrowable();
        final Throwable throwable2 = someOtherThrowable();

        // When
        addIncludes(options, throwable1, throwable2);

        // Then
        verify(options).includes(throwable1.getClass(), throwable2.getClass());
        verifyNoMoreInteractions(options);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_add_excludes() {

        // Given
        final Options options = mock(Options.class);
        final Throwable throwable1 = someThrowable();
        final Throwable throwable2 = someOtherThrowable();

        // When
        addExcludes(options, throwable1, throwable2);

        // Then
        verify(options).excludes(throwable1.getClass(), throwable2.getClass());
        verifyNoMoreInteractions(options);
    }
}
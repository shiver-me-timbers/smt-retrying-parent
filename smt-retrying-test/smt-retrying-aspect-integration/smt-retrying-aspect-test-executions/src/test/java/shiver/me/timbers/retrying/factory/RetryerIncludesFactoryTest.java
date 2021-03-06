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

import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.retrying.execution.RetryerIncludes;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;
import static shiver.me.timbers.retrying.random.RandomThrowables.someThrowablesArray;
import static shiver.me.timbers.retrying.util.Classes.toClasses;

public class RetryerIncludesFactoryTest {

    private LookupFactory<RetryerIncludes> lookupFactory;
    private RetryerIncludesFactory factory;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        lookupFactory = mock(LookupFactory.class);
        factory = new RetryerIncludesFactory(lookupFactory);
    }

    @Test
    public void Can_create_a_retryer_retries() {

        final Integer retries = someInteger();
        final Throwable[] includes = someThrowablesArray();

        final RetryerIncludes expected = mock(RetryerIncludes.class);

        // Given
        given(lookupFactory.find(retries, toClasses(asList(includes)))).willReturn(expected);

        // When
        final RetryerIncludes actual = factory.create(retries, includes);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_add_a_retryer_retries() {

        // Given
        final RetryerIncludes retryerRetries = mock(RetryerIncludes.class);
        final Integer retries = someInteger();
        final Throwable[] includes = someThrowablesArray();

        // When
        factory.add(retryerRetries, retries, includes);

        // Then
        verify(lookupFactory).add(retryerRetries, retries, toClasses(asList(includes)));
    }
}
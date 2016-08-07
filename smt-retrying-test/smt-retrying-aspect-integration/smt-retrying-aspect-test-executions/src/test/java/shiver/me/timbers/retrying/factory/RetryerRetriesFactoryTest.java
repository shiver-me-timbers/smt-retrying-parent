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
import shiver.me.timbers.retrying.execution.RetryerRetries;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;

public class RetryerRetriesFactoryTest {

    private LookupFactory<RetryerRetries> lookupFactory;
    private RetryerRetriesFactory factory;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        lookupFactory = mock(LookupFactory.class);
        factory = new RetryerRetriesFactory(lookupFactory);
    }

    @Test
    public void Can_create_a_retryer_retries() {

        final Integer retries = someInteger();

        final RetryerRetries expected = mock(RetryerRetries.class);

        // Given
        given(lookupFactory.find(retries)).willReturn(expected);

        // When
        final RetryerRetries actual = factory.create(retries);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_add_a_retryer_retries() {

        // Given
        final RetryerRetries retryerRetries = mock(RetryerRetries.class);
        final Integer retries = someInteger();

        // When
        factory.add(retryerRetries, retries);

        // Then
        verify(lookupFactory).add(retryerRetries, retries);
    }
}
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
import shiver.me.timbers.retrying.execution.RetryerInterval;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomEnums.someEnum;
import static shiver.me.timbers.data.random.RandomLongs.someLong;

public class RetryerIntervalFactoryTest {

    private LookupFactory<RetryerInterval> lookupFactory;
    private RetryerIntervalFactory factory;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        lookupFactory = mock(LookupFactory.class);
        factory = new RetryerIntervalFactory(lookupFactory);
    }

    @Test
    public void Can_create_a_retryer_interval() {

        final Long duration = someLong();
        final TimeUnit unit = someEnum(TimeUnit.class);

        final RetryerInterval expected = mock(RetryerInterval.class);

        // Given
        given(lookupFactory.find(duration, unit)).willReturn(expected);

        // When
        final RetryerInterval actual = factory.create(duration, unit);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_add_a_retryer_interval() {

        // Given
        final RetryerInterval retryerInterval = mock(RetryerInterval.class);
        final Long duration = someLong();
        final TimeUnit unit = someEnum(TimeUnit.class);

        // When
        factory.add(retryerInterval, duration, unit);

        // Then
        verify(lookupFactory).add(retryerInterval, duration, unit);
    }
}
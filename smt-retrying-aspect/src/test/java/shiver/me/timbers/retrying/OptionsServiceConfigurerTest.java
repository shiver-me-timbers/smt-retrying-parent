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

import java.util.concurrent.TimeUnit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static shiver.me.timbers.data.random.RandomEnums.someEnum;
import static shiver.me.timbers.data.random.RandomIntegers.someNegativeInteger;
import static shiver.me.timbers.data.random.RandomIntegers.somePositiveInteger;
import static shiver.me.timbers.data.random.RandomLongs.someLongs;

public class OptionsServiceConfigurerTest {

    private OptionsServiceConfigurer configurer;

    @Before
    public void setUp() {
        configurer = new OptionsServiceConfigurer();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_configure_an_options_service() {

        final OptionsService optionsService = mock(OptionsService.class);
        final Retry retry = mock(Retry.class);

        final Integer retries = somePositiveInteger();
        final Interval interval = mock(Interval.class);
        final Long[] intervalDurations = someLongs().array();
        final TimeUnit intervalUnit = someEnum(TimeUnit.class);
        final Class[] includes = {RuntimeException.class, IllegalArgumentException.class, Error.class};
        final Class[] excludes = {IllegalStateException.class, ClassCastException.class, IllegalAccessError.class};

        // Given
        given(retry.value()).willReturn(retries);
        given(retry.interval()).willReturn(interval);
        given(interval.duration()).willReturn(toPrimitives(intervalDurations));
        given(interval.unit()).willReturn(intervalUnit);
        given(retry.includes()).willReturn(includes);
        given(retry.excludes()).willReturn(excludes);

        // When
        configurer.configure(optionsService, retry);

        // Then
        verify(optionsService).withRetries(retries);
        verify(optionsService).withIntervals(intervalUnit, intervalDurations);
        verify(optionsService).includes(includes);
        verify(optionsService).excludes(excludes);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Will_not_configure_default_options() {

        final OptionsService optionsService = mock(OptionsService.class);
        final Retry retry = mock(Retry.class);

        final Interval interval = mock(Interval.class);

        // Given
        given(retry.value()).willReturn(someNegativeInteger());
        given(retry.interval()).willReturn(interval);
        given(interval.duration()).willReturn(new long[0]);
        given(interval.unit()).willReturn(someEnum(TimeUnit.class));
        given(retry.includes()).willReturn(new Class[0]);
        given(retry.excludes()).willReturn(new Class[0]);

        // When
        configurer.configure(optionsService, retry);

        // Then
        verifyZeroInteractions(optionsService);
    }

    private static long[] toPrimitives(Long[] longs) {
        final long[] primitives = new long[longs.length];
        for (int i = 0; i < longs.length; i++) {
            primitives[i] = longs[i];
        }
        return primitives;
    }
}
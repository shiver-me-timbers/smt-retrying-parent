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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static shiver.me.timbers.data.random.RandomIntegers.somePositiveInteger;

public class OptionsServiceConfigurerTest {

    private OptionsServiceConfigurer configurer;

    @Before
    public void setUp() {
        configurer = new OptionsServiceConfigurer();
    }

    @Test
    public void Can_configure_an_options_service() {

        final OptionsService optionsService = mock(OptionsService.class);
        final Retry retry = mock(Retry.class);

        final Integer retries = somePositiveInteger();

        // Given
        given(retry.value()).willReturn(retries);

        // When
        configurer.configure(optionsService, retry);

        // Then
        verify(optionsService).withRetries(retries);
    }

    @Test
    public void Will_not_configure_default_options() {

        final OptionsService optionsService = mock(OptionsService.class);
        final Retry retry = mock(Retry.class);

        // Given
        given(retry.value()).willReturn(-1);

        // When
        configurer.configure(optionsService, retry);

        // Then
        verifyZeroInteractions(optionsService);
    }
}
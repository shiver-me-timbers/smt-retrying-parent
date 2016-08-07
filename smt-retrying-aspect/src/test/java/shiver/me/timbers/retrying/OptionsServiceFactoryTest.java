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

import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class OptionsServiceFactoryTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Instantiater<OptionsService> instantiater;
    private Configurer<OptionsService, Retry> configurer;
    private OptionsServiceFactory factory;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        instantiater = mock(Instantiater.class);
        configurer = mock(Configurer.class);
        factory = new OptionsServiceFactory(instantiater, configurer);
    }

    @Test
    public void Can_create_an_options_service()
        throws IllegalAccessException, InvocationTargetException, InstantiationException {

        final Retry retry = mock(Retry.class);

        final OptionsService expected = mock(OptionsService.class);

        // Given
        given(instantiater.instantiate()).willReturn(expected);

        // When
        final OptionsService actual = factory.create(retry);

        // Then
        verify(configurer).configure(expected, retry);
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_fail_to_instantiate_an_options_service()
        throws IllegalAccessException, InvocationTargetException, InstantiationException {

        final Retry retry = mock(Retry.class);

        final InstantiationException exception = new InstantiationException();

        // Given
        given(instantiater.instantiate()).willThrow(exception);
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Could not instantiate the OptionsService.");
        expectedException.expectCause(is(exception));

        // When
        factory.create(retry);
    }
}
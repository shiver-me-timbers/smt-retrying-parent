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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class InstantiaterTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void Can_instantiate_a_class_with_a_default_constructor()
        throws IllegalAccessException, InvocationTargetException, InstantiationException {

        // When
        final TestService actual = new Instantiater<>(TestService.class).instantiate();

        // Then
        assertThat(actual, isA(TestService.class));
    }

    @Test
    public void Can_fail_to_instantiate_a_class_without_a_default_constructor()
        throws IllegalAccessException, InvocationTargetException, InstantiationException {

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectCause(isA(NoSuchMethodException.class));

        // When
        new Instantiater<>(NonDefaultTestService.class).instantiate();
    }

    @Test
    public void Can_instantiate_a_class_with_a_no_default_constructor()
        throws IllegalAccessException, InvocationTargetException, InstantiationException {

        // Given
        final TestInput input = mock(TestInput.class);

        // When
        final NonDefaultTestService actual = new Instantiater<>(NonDefaultTestService.class, TestInput.class)
            .instantiate(input);

        // Then
        assertThat(actual.getInput(), is(input));
    }

    @Test
    public void Can_fail_instantiate_a_class_with_a_default_constructor()
        throws IllegalAccessException, InvocationTargetException, InstantiationException {

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectCause(isA(NoSuchMethodException.class));

        // When
        new Instantiater<>(TestService.class, TestInput.class);
    }

    private static class TestService {
    }

    private static class NonDefaultTestService {

        private final TestInput input;

        NonDefaultTestService(TestInput input) {
            this.input = input;
        }

        TestInput getInput() {
            return input;
        }
    }

    private static class TestInput {
    }
}
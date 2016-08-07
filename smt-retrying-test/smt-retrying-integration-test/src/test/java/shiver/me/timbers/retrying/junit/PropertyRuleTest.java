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

package shiver.me.timbers.retrying.junit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mockito.InOrder;
import shiver.me.timbers.retrying.property.PropertyManager;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class PropertyRuleTest {

    private PropertyManager propertyManager;
    private PropertyRule rule;

    @Before
    public void setUp() {
        propertyManager = mock(PropertyManager.class);
        rule = new PropertyRule(propertyManager);
    }

    @Test
    public void Can_restore_properties_after_a_test_run() throws Throwable {

        // Given
        final Statement statement = mock(Statement.class);

        // When
        rule.apply(statement, mock(Description.class)).evaluate();

        // Then
        final InOrder order = inOrder(statement, propertyManager);
        order.verify(statement).evaluate();
        order.verify(propertyManager).restoreProperties();
    }

    @Test
    public void Can_restore_properties_even_if_the_test_throws_an_exception() throws Throwable {

        final Statement statement = mock(Statement.class);

        final Throwable thrown = mock(Throwable.class);

        // Given
        willThrow(thrown).given(statement).evaluate();

        // When
        try {
            rule.apply(statement, mock(Description.class)).evaluate();
        } catch (Throwable throwable) {
            assertThat(throwable, is(thrown));
        }

        // Then
        final InOrder order = inOrder(statement, propertyManager);
        order.verify(statement).evaluate();
        order.verify(propertyManager).restoreProperties();
    }

    @Test
    public void Can_set_properties() {

        // Given
        final String key = someString();
        final String value = someString();

        // When
        rule.setProperty(key, value);

        // Then
        verify(propertyManager).setProperty(key, value);
    }
}
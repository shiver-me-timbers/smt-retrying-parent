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
import shiver.me.timbers.retrying.property.PropertyManager;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;

public class RetryerPropertyRuleTest {

    private PropertyManager propertyManager;
    private RetryerPropertyRule rule;

    @Before
    public void setUp() {
        propertyManager = mock(PropertyManager.class);
        rule = new RetryerPropertyRule(propertyManager);
    }

    @Test
    public void Can_set_the_retries_property() {

        // Given
        final int retries = someInteger();

        // When
        rule.setRetries(retries);

        // Then
        verify(propertyManager).setProperty("smt.retryer.retries", String.valueOf(retries));
    }

    @Test
    public void Can_build_property_rule_with_retries() {

        // Given
        final int retries = someInteger();

        // When
        final RetryerPropertyRule actual = rule.withRetries(retries);

        // Then
        assertThat(actual, is(rule));
        verify(propertyManager).setProperty("smt.retryer.retries", String.valueOf(retries));
    }
}
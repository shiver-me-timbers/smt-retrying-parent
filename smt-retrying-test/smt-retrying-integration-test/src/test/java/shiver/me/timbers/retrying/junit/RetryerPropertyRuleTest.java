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

import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomEnums.someEnum;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;
import static shiver.me.timbers.data.random.RandomLongs.someLong;
import static shiver.me.timbers.retrying.PropertyConstants.EXCLUDES_PROPERTY;
import static shiver.me.timbers.retrying.PropertyConstants.INCLUDES_PROPERTY;
import static shiver.me.timbers.retrying.PropertyConstants.INTERVAL_DURATION_PROPERTY;
import static shiver.me.timbers.retrying.PropertyConstants.INTERVAL_UNIT_PROPERTY;
import static shiver.me.timbers.retrying.PropertyConstants.RETRIES_PROPERTY;
import static shiver.me.timbers.retrying.random.RandomThrowables.SOME_THROWABLES;
import static shiver.me.timbers.retrying.util.Classes.toClassNames;
import static shiver.me.timbers.retrying.util.Strings.concat;

public class RetryerPropertyRuleTest {

    private PropertyManager propertyManager;
    private RetryerPropertyRule rule;

    @Before
    public void setUp() {
        propertyManager = mock(PropertyManager.class);
        rule = new RetryerPropertyRule(propertyManager);
    }

    @Test
    public void Can_build_property_rule_with_retries() {

        // Given
        final int retries = someInteger();

        // When
        final RetryerPropertyRule actual = rule.withRetries(retries);

        // Then
        assertThat(actual, is(rule));
        verify(propertyManager).setProperty(RETRIES_PROPERTY, String.valueOf(retries));
    }

    @Test
    public void Can_build_property_rule_with_an_interval() {

        // Given
        final Long duration = someLong();
        final TimeUnit unit = someEnum(TimeUnit.class);

        // When
        final RetryerPropertyRule actual = rule.withInterval(duration, unit);

        // Then
        assertThat(actual, is(rule));
        verify(propertyManager).setProperty(INTERVAL_DURATION_PROPERTY, duration.toString());
        verify(propertyManager).setProperty(INTERVAL_UNIT_PROPERTY, unit.name());
    }

    @Test
    public void Can_build_property_rule_with_includes() {

        // When
        final RetryerPropertyRule actual = rule.withIncludes(SOME_THROWABLES);

        // Then
        assertThat(actual, is(rule));
        verify(propertyManager).setProperty(INCLUDES_PROPERTY, concat(",", toClassNames(asList(SOME_THROWABLES))));
    }

    @Test
    public void Can_build_property_rule_with_excludes() {

        // When
        final RetryerPropertyRule actual = rule.withExcludes(SOME_THROWABLES);

        // Then
        assertThat(actual, is(rule));
        verify(propertyManager).setProperty(EXCLUDES_PROPERTY, concat(",", toClassNames(asList(SOME_THROWABLES))));
    }
}
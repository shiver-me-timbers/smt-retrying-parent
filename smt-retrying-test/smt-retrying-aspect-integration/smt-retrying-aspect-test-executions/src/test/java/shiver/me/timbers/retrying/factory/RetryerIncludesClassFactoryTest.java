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

import org.junit.Test;
import shiver.me.timbers.retrying.execution.RetryerIncludes;
import shiver.me.timbers.retrying.execution.RetryerIncludesClass;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.retrying.random.RandomThrowables.SOME_THROWABLES;

public class RetryerIncludesClassFactoryTest {

    @Test
    public void Can_create_a_retryer_retries_class_factory() {

        // When
        final RetryerIncludes actual = new RetryerIncludesClassFactory().create(8, SOME_THROWABLES);

        // Then
        assertThat(actual, instanceOf(RetryerIncludesClass.class));
    }
}
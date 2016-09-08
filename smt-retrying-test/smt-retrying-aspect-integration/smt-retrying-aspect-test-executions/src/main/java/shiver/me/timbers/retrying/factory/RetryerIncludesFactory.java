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

import shiver.me.timbers.retrying.execution.RetryerIncludes;

import static java.util.Arrays.asList;
import static shiver.me.timbers.retrying.random.RandomThrowables.SOME_OTHER_THROWABLES;
import static shiver.me.timbers.retrying.random.RandomThrowables.SOME_THROWABLES;
import static shiver.me.timbers.retrying.util.Classes.toClasses;

public class RetryerIncludesFactory {

    private final LookupFactory<RetryerIncludes> lookupFactory;

    public RetryerIncludesFactory(
        RetryerIncludes retryerIncludes,
        RetryerIncludes retryerNoIncludes,
        RetryerIncludes retryerAddIncludes
    ) {
        this(new MapLookupFactory<RetryerIncludes>());
        add(retryerIncludes, 8, SOME_THROWABLES);
        add(retryerNoIncludes, 8);
        add(retryerAddIncludes, 8, SOME_OTHER_THROWABLES[0]);
    }

    public RetryerIncludesFactory(LookupFactory<RetryerIncludes> lookupFactory) {
        this.lookupFactory = lookupFactory;
    }

    public RetryerIncludes create(int retries, Throwable... includes) {
        return lookupFactory.find(retries, toClasses(asList(includes)));
    }

    public void add(RetryerIncludes retryerIncludes, int retries, Throwable... includes) {
        lookupFactory.add(retryerIncludes, retries, toClasses(asList(includes)));
    }
}

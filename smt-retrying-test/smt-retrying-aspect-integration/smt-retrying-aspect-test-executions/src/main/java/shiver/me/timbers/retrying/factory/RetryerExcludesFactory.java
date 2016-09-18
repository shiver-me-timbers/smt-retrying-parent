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

import shiver.me.timbers.retrying.execution.RetryerExcludes;

import static java.util.Arrays.asList;
import static shiver.me.timbers.retrying.random.RandomThrowables.SOME_OTHER_THROWABLES;
import static shiver.me.timbers.retrying.util.Classes.toClasses;
import static shiver.me.timbers.retrying.util.Constants.DEFAULT_RETRIES;

public class RetryerExcludesFactory {

    private final LookupFactory<RetryerExcludes> lookupFactory;

    public RetryerExcludesFactory(
        RetryerExcludes retryerExcludes,
        RetryerExcludes retryerNoExcludes,
        RetryerExcludes retryerAddExcludes
    ) {
        this(new MapLookupFactory<RetryerExcludes>());
        add(retryerExcludes, DEFAULT_RETRIES, SOME_OTHER_THROWABLES);
        add(retryerNoExcludes, DEFAULT_RETRIES);
        add(retryerAddExcludes, DEFAULT_RETRIES, SOME_OTHER_THROWABLES[0]);
    }

    public RetryerExcludesFactory(LookupFactory<RetryerExcludes> lookupFactory) {
        this.lookupFactory = lookupFactory;
    }

    public RetryerExcludes create(int retries, Throwable... includes) {
        return lookupFactory.find(retries, toClasses(asList(includes)));
    }

    public void add(RetryerExcludes retryerExcludes, int retries, Throwable... includes) {
        lookupFactory.add(retryerExcludes, retries, toClasses(asList(includes)));
    }
}

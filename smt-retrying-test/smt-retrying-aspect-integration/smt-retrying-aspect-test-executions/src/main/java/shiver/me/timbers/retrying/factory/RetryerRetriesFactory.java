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

import shiver.me.timbers.retrying.execution.RetryerRetries;

import static shiver.me.timbers.retrying.util.Constants.DEFAULT_RETRIES;

public class RetryerRetriesFactory {

    private final LookupFactory<RetryerRetries> lookupFactory;

    public RetryerRetriesFactory(RetryerRetries retryerRetries) {
        this(new MapLookupFactory<RetryerRetries>());
        add(retryerRetries, DEFAULT_RETRIES);
    }

    public RetryerRetriesFactory(LookupFactory<RetryerRetries> lookupFactory) {
        this.lookupFactory = lookupFactory;
    }

    public RetryerRetries create(Integer retries) {
        return lookupFactory.find(retries);
    }

    public void add(RetryerRetries retryerRetries, Integer retries) {
        lookupFactory.add(retryerRetries, retries);
    }
}

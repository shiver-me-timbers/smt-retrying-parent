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

import shiver.me.timbers.retrying.execution.RetryerInterval;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class RetryerIntervalFactory {

    private final LookupFactory<RetryerInterval> lookupFactory;

    public RetryerIntervalFactory(RetryerInterval retryerInterval) {
        this(new MapLookupFactory<RetryerInterval>());
        add(retryerInterval, 200L, MILLISECONDS);
    }

    public RetryerIntervalFactory(LookupFactory<RetryerInterval> lookupFactory) {
        this.lookupFactory = lookupFactory;
    }

    public RetryerInterval create(Long duration, TimeUnit unit) {
        return lookupFactory.find(duration, unit);
    }

    public void add(RetryerInterval retryerInterval, Long duration, TimeUnit unit) {
        lookupFactory.add(retryerInterval, duration, unit);
    }
}

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
import shiver.me.timbers.retrying.execution.ManualRetryerDefaults;
import shiver.me.timbers.retrying.execution.ManualRetryerInterval;
import shiver.me.timbers.retrying.execution.RetryerDefaults;
import shiver.me.timbers.retrying.execution.RetryerInterval;
import shiver.me.timbers.retrying.junit.RetryerPropertyRule;
import shiver.me.timbers.retrying.property.SystemPropertyManager;

import java.util.concurrent.TimeUnit;

public class ITManualRetryerIntervalProperty extends AbstractITRetryerIntervalProperty {

    @Rule
    public RetryerPropertyRule properties = new RetryerPropertyRule(new SystemPropertyManager());

    @Override
    public RetryerPropertyRule properties() {
        return properties;
    }

    @Override
    public RetryerDefaults defaults() {
        return new ManualRetryerDefaults();
    }

    @Override
    protected RetryerInterval overrideInterval(Long duration, TimeUnit unit) {
        return new ManualRetryerInterval<>(duration, unit);
    }
}

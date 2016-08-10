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

import shiver.me.timbers.retrying.property.PropertyManager;

import java.util.concurrent.TimeUnit;

import static shiver.me.timbers.retrying.PropertyConstants.INTERVAL_DURATION_PROPERTY;
import static shiver.me.timbers.retrying.PropertyConstants.INTERVAL_UNIT_PROPERTY;
import static shiver.me.timbers.retrying.PropertyConstants.RETRIES_PROPERTY;

public class RetryerPropertyRule extends PropertyRule {

    public RetryerPropertyRule(PropertyManager propertyManager) {
        super(propertyManager);
    }

    public RetryerPropertyRule withRetries(int retries) {
        setRetries(retries);
        return this;
    }

    public void setRetries(int retries) {
        setProperty(RETRIES_PROPERTY, String.valueOf(retries));
    }

    public RetryerPropertyRule withInterval(Long duration, TimeUnit unit) {
        setInterval(duration, unit);
        return this;
    }

    public void setInterval(Long duration, TimeUnit unit) {
        setProperty(INTERVAL_DURATION_PROPERTY, duration.toString());
        setProperty(INTERVAL_UNIT_PROPERTY, unit.name());
    }
}

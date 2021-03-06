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

/**
 * @author Karl Bennett
 */
class SystemPropertyChoices extends AbstractPropertyChoices implements PropertyChoices {

    @Override
    String getRetriesProperty() {
        return System.getProperty(RETRIES_PROPERTY);
    }

    @Override
    String getIntervalDurationProperty() {
        return System.getProperty(INTERVAL_DURATIONS_PROPERTY);
    }

    @Override
    String getIntervalUnitProperty() {
        return System.getProperty(INTERVAL_UNIT_PROPERTY);
    }

    @Override
    String getIncludesProperty() {
        return System.getProperty(INCLUDES_PROPERTY);
    }

    @Override
    String getExcludesProperty() {
        return System.getProperty(EXCLUDES_PROPERTY);
    }
}

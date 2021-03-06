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
interface PropertyChoices extends OverridingChoices {
    String RETRIES_PROPERTY = "smt.retryer.retries";
    String INTERVAL_DURATIONS_PROPERTY = "smt.retryer.interval.durations";
    String INTERVAL_UNIT_PROPERTY = "smt.retryer.interval.unit";
    String INCLUDES_PROPERTY = "smt.retryer.includes";
    String EXCLUDES_PROPERTY = "smt.retryer.excludes";
}

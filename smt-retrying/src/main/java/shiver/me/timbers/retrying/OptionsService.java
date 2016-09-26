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

import java.util.concurrent.TimeUnit;

/**
 * @author Karl Bennett
 */
interface OptionsService {

    /**
     * Sets the number of times an execution should be retried.
     */
    OptionsService withRetries(Integer retries);

    /**
     * Sets a sequence of sleep time intervals that will be used in order for each subsequent retry. If there are more
     * retries than durations then the last duration in the sequence will be used repeatedly.
     */
    OptionsService withIntervals(TimeUnit unit, Long... durations);

    /**
     * Sets the throwables that should produce a retry, any other throwable produced by the executed code will cause an
     * instant failure.
     */
    @SuppressWarnings("unchecked")
    OptionsService includes(Class<? extends Throwable>... includes);

    /**
     * Sets the throwables that should never produce a retry, any other throwable produced by the executed code will
     * cause a retry. This exclusion list takes precedence over the inclusion list.
     */
    @SuppressWarnings("unchecked")
    OptionsService excludes(Class<? extends Throwable>... excludes);
}

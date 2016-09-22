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
 * This annotation can be used to specify a length on time the {@link Retryer} will wait in between each retry.
 *
 * @author Karl Bennett
 */
public @interface Interval {

    /**
     * The number of {@link TimeUnit}s to wait. This can either be one or many values. When more than one value is
     * supplied the durations will be applied in order between each subsequent retry. If the number of retries exceeds
     * the number of supplied durations then the last duration in the list will be reused for any remaining retry
     * intervals.
     */
    long[] durations();

    /**
     * The unit of time related to the duration.
     */
    TimeUnit unit();
}

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

import static java.lang.String.format;

/**
 * @author Karl Bennett
 */
class Choice {

    private final int retries;
    private final Time interval;

    Choice(int retries, Time interval) {
        this.retries = validateRetries(retries);
        this.interval = validateInterval(interval);
    }

    private static int validateRetries(int retries) {
        if (retries > 0) {
            return retries;
        }
        throw new IllegalArgumentException(
            format("The retries value must be greater than 1. The value (%s) is invalid.", retries)
        );
    }

    private static Time validateInterval(Time interval) {
        if (interval.toMillis() >= 0) {
            return interval;
        }
        throw new IllegalArgumentException(
            format("The interval value must be greater than 0. The value (%s) is invalid.", interval)
        );
    }

    int getRetries() {
        return retries;
    }

    void sleepForInterval() throws InterruptedException {
        Thread.sleep(interval.toMillis());
    }
}

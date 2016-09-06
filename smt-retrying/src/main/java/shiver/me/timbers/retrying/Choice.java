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

import java.util.Set;

import static java.lang.String.format;

/**
 * @author Karl Bennett
 */
class Choice {

    private final int retries;
    private final Time interval;
    private final Set<Class<? extends Throwable>> includes;

    Choice(int retries, Time interval, Set<Class<? extends Throwable>> includes) {
        this.retries = validateRetries(retries);
        this.interval = validateInterval(interval);
        this.includes = includes;
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

    boolean isSuppressed(Throwable throwable) {
        if (includes.isEmpty()) {
            return true;
        }

        final Class<? extends Throwable> type = throwable.getClass();

        if (includes.contains(type)) {
            return true;
        }

        return false;
    }
}

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
    private final Set<Class<? extends Throwable>> excludes;

    Choice(
        int retries,
        Time interval,
        Set<Class<? extends Throwable>> includes,
        Set<Class<? extends Throwable>> excludes
    ) {
        this.retries = validateRetries(retries);
        this.interval = interval;
        this.includes = includes;
        this.excludes = excludes;
    }

    private static int validateRetries(int retries) {
        if (retries > 0) {
            return retries;
        }
        throw new IllegalArgumentException(
            format("The retries value must be greater than 1. The value (%s) is invalid.", retries)
        );
    }

    int getRetries() {
        return retries;
    }

    Intervals getIntervals() {
        return interval.startIntervals();
    }

    boolean isSuppressed(Throwable throwable) {
        if (includes.isEmpty() && excludes.isEmpty()) {
            return true;
        }

        final Class<? extends Throwable> type = throwable.getClass();

        if (excludes.contains(type)) {
            return false;
        }

        if (includes.contains(type) || includes.isEmpty()) {
            return true;
        }

        return false;
    }
}

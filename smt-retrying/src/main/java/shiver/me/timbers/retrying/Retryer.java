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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Karl Bennett
 */
public class Retryer implements RetryerService {

    private Logger log = LoggerFactory.getLogger(getClass());

    private final Chooser choices;

    public Retryer() {
        this(new Options());
    }

    public Retryer(Options options) {
        this.choices = options.chooser();
    }

    @Override
    public <T> T retry(Until<T> until) {
        final int retries = choices.choose().getRetries();

        final Thrower thrower = new Thrower();
        for (int i = 0; i < retries; i++) {
            try {
                return until.success();
            } catch (Throwable e) {
                thrower.register(e);
                log.warn(
                    "Retry attempt ({}) for execution ({}) because of exception ({}, {}).",
                    i + 1, until, e.getClass(), e.getMessage()
                );
            }
        }

        final Throwable throwable = thrower.registeredThrowable();

        if (throwable instanceof Error) {
            throw (Error) throwable;
        }

        if (throwable instanceof RuntimeException) {
            throw (RuntimeException) throwable;
        }

        throw new RetriedToManyTimesException(throwable);
    }

    private class Thrower {

        private Throwable throwable;

        void register(Throwable throwable) {
            this.throwable = throwable;
        }

        Throwable registeredThrowable() {
            return throwable;
        }
    }
}

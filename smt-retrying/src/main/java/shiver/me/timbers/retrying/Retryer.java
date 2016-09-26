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
    public <T> T retry(Until<T> until) throws InterruptedException {
        final Choice choice = choices.choose();

        final int retries = choice.getRetries();
        final Thrower thrower = new Thrower(choice);
        final Intervals intervals = choice.getIntervals();

        for (int i = 0; i < retries; i++) {
            try {
                return until.success();
            } catch (Throwable e) {
                thrower.register(e);
                thrower.throwIfNotSuppressed();
                log.warn(
                    "Retry attempt ({}) for execution ({}) because of exception ({}, {}).",
                    i + 1, until, e.getClass(), e.getMessage()
                );
            }
            intervals.sleep();
        }

        // This is a syntactical trick to allow compilation. This method call will never actually return anything, it
        // will only every throw an exception.
        return thrower.throwRegisteredThrowable();
    }

    private class Thrower {

        private final Choice choice;
        private Throwable throwable;

        Thrower(Choice choice) {
            this.choice = choice;
        }

        void register(Throwable throwable) {
            this.throwable = throwable;
        }

        void throwIfNotSuppressed() {
            if (!choice.isSuppressed(throwable)) {
                throwRegisteredThrowable();
            }
        }

        <T> T throwRegisteredThrowable() {
            if (throwable instanceof Error) {
                throw (Error) throwable;
            }

            if (throwable instanceof RuntimeException) {
                throw (RuntimeException) throwable;
            }

            throw new RetriedTooManyTimesException(throwable);
        }
    }
}

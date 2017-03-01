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

package shiver.me.timbers.retrying.execution;

import shiver.me.timbers.retrying.Options;
import shiver.me.timbers.retrying.Retryer;
import shiver.me.timbers.retrying.Until;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class ManualRetryerInterval<R extends Retryer, O extends Options> extends RetryerCreater<R, O>
    implements RetryerInterval {

    private final long duration;
    private final TimeUnit unit;

    public ManualRetryerInterval(long duration, TimeUnit unit) {
        this.duration = duration;
        this.unit = unit;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T intervalMethod(final Callable<T> callable) throws Throwable {
        return retryer((O) options().withIntervals(unit, duration)).retry(new Until<T>() {
            @Override
            public T success() throws Throwable {
                return callable.call();
            }
        });
    }
}

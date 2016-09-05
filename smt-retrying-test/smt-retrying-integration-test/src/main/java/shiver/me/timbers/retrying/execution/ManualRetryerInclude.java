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

import static shiver.me.timbers.retrying.util.Includes.addIncludes;

/**
 * @author Karl Bennett
 */
public class ManualRetryerInclude<R extends Retryer, O extends Options> extends RetryerCreater<R, O>
    implements RetryerInclude {

    private final int retries;
    private final Throwable[] includes;

    public ManualRetryerInclude(int retries, Throwable... includes) {
        this.retries = retries;
        this.includes = includes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T includeMethod(final Callable<T> callable) throws InterruptedException {
        return retryer((O) addIncludes(options(), includes).withRetries(retries)).retry(new Until<T>() {
            @Override
            public T success() throws Throwable {
                return callable.call();
            }
        });
    }
}

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

public class ManualRetryerRetries<R extends Retryer, O extends Options> extends RetryerCreater<R, O>
    implements RetryerRetries {

    private final int retries;

    public ManualRetryerRetries(int retries) {
        this.retries = retries;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T retryMethod(final Callable<T> callable) throws Throwable {
        return retryer((O) options().withRetries(retries)).retry(new Until<T>() {
            @Override
            public T success() throws Throwable {
                return callable.call();
            }
        });
    }
}

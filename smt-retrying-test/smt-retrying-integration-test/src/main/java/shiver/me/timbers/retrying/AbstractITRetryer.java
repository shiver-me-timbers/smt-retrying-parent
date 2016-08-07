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

import org.junit.Test;
import shiver.me.timbers.retrying.execution.RetryerRetries;

public abstract class AbstractITRetryer implements ITRetryer {

    private final AbstractITRetryerRetries retries = new AbstractITRetryerRetries() {
        @Override
        public RetryerRetries retries(int retries) {
            return AbstractITRetryer.this.retries(retries);
        }
    };

    @Test
    public void Can_set_the_number_of_retries() throws Throwable {
        retries.Can_set_the_number_of_retries();
    }
}

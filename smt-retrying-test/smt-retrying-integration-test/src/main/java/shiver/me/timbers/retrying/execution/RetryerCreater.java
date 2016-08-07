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

public class RetryerCreater<R extends Retryer, O extends Options> {

    @SuppressWarnings("unchecked")
    public R retryer() {
        return (R) new Retryer();
    }

    @SuppressWarnings("unchecked")
    public R retryer(O options) {
        return (R) new Retryer(options);
    }

    @SuppressWarnings("unchecked")
    public O options() {
        return (O) new Options();
    }
}

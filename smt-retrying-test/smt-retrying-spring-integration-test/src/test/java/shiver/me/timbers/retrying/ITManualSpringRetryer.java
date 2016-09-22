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

import shiver.me.timbers.retrying.execution.RetryerDefaults;
import shiver.me.timbers.retrying.execution.RetryerExcludes;
import shiver.me.timbers.retrying.execution.RetryerIncludes;
import shiver.me.timbers.retrying.execution.RetryerInterval;
import shiver.me.timbers.retrying.execution.RetryerIntervalIncrements;
import shiver.me.timbers.retrying.execution.RetryerRetries;
import shiver.me.timbers.retrying.execution.SpringManualRetryerDefaults;
import shiver.me.timbers.retrying.execution.SpringManualRetryerExcludes;
import shiver.me.timbers.retrying.execution.SpringManualRetryerIncludeWithExclude;
import shiver.me.timbers.retrying.execution.SpringManualRetryerIncludes;
import shiver.me.timbers.retrying.execution.SpringManualRetryerInterval;
import shiver.me.timbers.retrying.execution.SpringManualRetryerIntervalIncrements;
import shiver.me.timbers.retrying.execution.SpringManualRetryerRetries;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ITManualSpringRetryer extends AbstractITSpringRetryer {

    @Override
    public RetryerDefaults defaults() {
        return new SpringManualRetryerDefaults();
    }

    @Override
    public RetryerRetries retries(int retries) {
        return new SpringManualRetryerRetries(retries);
    }

    @Override
    public RetryerInterval interval(Long duration, TimeUnit unit) {
        return new SpringManualRetryerInterval(duration, unit);
    }

    @Override
    public RetryerIntervalIncrements intervalIncrements(int retries, TimeUnit unit, Long... durations) {
        return new SpringManualRetryerIntervalIncrements(retries, unit, durations);
    }

    @Override
    public RetryerIncludes includes(int retries, Throwable... includes) {
        return new SpringManualRetryerIncludes(retries, includes);
    }

    @Override
    public RetryerIncludes includesWithExcludes(int retries, List<Throwable> includes, List<Throwable> excludes) {
        return new SpringManualRetryerIncludeWithExclude(retries, excludes, includes);
    }

    @Override
    public RetryerExcludes excludes(int retries, Throwable... excludes) {
        return new SpringManualRetryerExcludes(retries, excludes);
    }

    @Override
    public RetryerExcludes excludesWithIncludes(int retries, List<Throwable> excludes, List<Throwable> includes) {
        return new SpringManualRetryerIncludeWithExclude(retries, excludes, includes);
    }
}

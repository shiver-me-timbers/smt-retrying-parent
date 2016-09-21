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

import shiver.me.timbers.retrying.factory.RetryerDefaultsMethodFactory;
import shiver.me.timbers.retrying.factory.RetryerExcludesMethodFactory;
import shiver.me.timbers.retrying.factory.RetryerExcludesWithIncludesMethodFactory;
import shiver.me.timbers.retrying.factory.RetryerIncludesMethodFactory;
import shiver.me.timbers.retrying.factory.RetryerIncludesWithExcludesMethodFactory;
import shiver.me.timbers.retrying.factory.RetryerIntervalIncrementsMethodFactory;
import shiver.me.timbers.retrying.factory.RetryerIntervalMethodFactory;
import shiver.me.timbers.retrying.factory.RetryerRetriesMethodFactory;

public abstract class AbstractITAspectRetryerMethod extends AbstractITAspectRetryer {

    @Override
    public RetryerDefaultsMethodFactory defaultsFactory() {
        return new RetryerDefaultsMethodFactory();
    }

    @Override
    public RetryerRetriesMethodFactory retriesFactory() {
        return new RetryerRetriesMethodFactory();
    }

    @Override
    public RetryerIntervalMethodFactory intervalFactory() {
        return new RetryerIntervalMethodFactory();
    }

    @Override
    public RetryerIntervalIncrementsMethodFactory intervalIncrementsFactory() {
        return new RetryerIntervalIncrementsMethodFactory();
    }

    @Override
    public RetryerIncludesMethodFactory includesFactory() {
        return new RetryerIncludesMethodFactory();
    }

    @Override
    public RetryerIncludesWithExcludesMethodFactory includesWithExcludesFactory() {
        return new RetryerIncludesWithExcludesMethodFactory();
    }

    @Override
    public RetryerExcludesMethodFactory excludesFactory() {
        return new RetryerExcludesMethodFactory();
    }

    @Override
    public RetryerExcludesWithIncludesMethodFactory excludesWithIncludesFactory() {
        return new RetryerExcludesWithIncludesMethodFactory();
    }
}

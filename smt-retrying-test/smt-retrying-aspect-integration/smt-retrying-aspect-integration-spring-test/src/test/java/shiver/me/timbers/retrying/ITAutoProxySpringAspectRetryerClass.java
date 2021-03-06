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

import org.springframework.beans.factory.annotation.Autowired;
import shiver.me.timbers.retrying.factory.AutoProxyRetryerDefaultsClassFactory;
import shiver.me.timbers.retrying.factory.AutoProxyRetryerExcludesClassFactory;
import shiver.me.timbers.retrying.factory.AutoProxyRetryerExcludesWithIncludesClassFactory;
import shiver.me.timbers.retrying.factory.AutoProxyRetryerIncludesClassFactory;
import shiver.me.timbers.retrying.factory.AutoProxyRetryerIncludesWithExcludesClassFactory;
import shiver.me.timbers.retrying.factory.AutoProxyRetryerIntervalClassFactory;
import shiver.me.timbers.retrying.factory.AutoProxyRetryerIntervalIncrementsClassFactory;
import shiver.me.timbers.retrying.factory.AutoProxyRetryerRetriesClassFactory;

public class ITAutoProxySpringAspectRetryerClass extends AbstractITSpringAspectRetryerClass {

    @Autowired
    private AutoProxyRetryerDefaultsClassFactory defaultsFactory;

    @Autowired
    private AutoProxyRetryerRetriesClassFactory retriesFactory;

    @Autowired
    private AutoProxyRetryerIntervalClassFactory intervalFactory;

    @Autowired
    private AutoProxyRetryerIntervalIncrementsClassFactory intervalIncrementsFactory;

    @Autowired
    private AutoProxyRetryerIncludesClassFactory includesFactory;

    @Autowired
    private AutoProxyRetryerIncludesWithExcludesClassFactory includesWithExcludesFactory;

    @Autowired
    private AutoProxyRetryerExcludesClassFactory excludesFactory;

    @Autowired
    private AutoProxyRetryerExcludesWithIncludesClassFactory excludesWithIncludesFactory;

    @Override
    public AutoProxyRetryerDefaultsClassFactory defaultsFactory() {
        return defaultsFactory;
    }

    @Override
    public AutoProxyRetryerRetriesClassFactory retriesFactory() {
        return retriesFactory;
    }

    @Override
    public AutoProxyRetryerIntervalClassFactory intervalFactory() {
        return intervalFactory;
    }

    @Override
    public AutoProxyRetryerIntervalIncrementsClassFactory intervalIncrementsFactory() {
        return intervalIncrementsFactory;
    }

    @Override
    public AutoProxyRetryerIncludesClassFactory includesFactory() {
        return includesFactory;
    }

    @Override
    public AutoProxyRetryerIncludesWithExcludesClassFactory includesWithExcludesFactory() {
        return includesWithExcludesFactory;
    }

    @Override
    public AutoProxyRetryerExcludesClassFactory excludesFactory() {
        return excludesFactory;
    }

    @Override
    public AutoProxyRetryerExcludesWithIncludesClassFactory excludesWithIncludesFactory() {
        return excludesWithIncludesFactory;
    }
}

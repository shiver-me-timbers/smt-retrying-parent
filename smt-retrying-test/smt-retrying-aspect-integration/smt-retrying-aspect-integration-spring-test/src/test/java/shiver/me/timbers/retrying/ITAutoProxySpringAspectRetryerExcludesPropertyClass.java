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

public class ITAutoProxySpringAspectRetryerExcludesPropertyClass extends AbstractITSpringAspectRetryerExcludesPropertyClass {

    @Autowired
    private AutoProxyRetryerDefaultsClassFactory defaultsFactory;

    @Autowired
    private AutoProxyRetryerExcludesClassFactory includesFactory;

    @Autowired
    private AutoProxyRetryerExcludesWithIncludesClassFactory includesWithExcludesFactory;

    @Override
    public AutoProxyRetryerDefaultsClassFactory defaultsFactory() {
        return defaultsFactory;
    }

    @Override
    public AutoProxyRetryerExcludesClassFactory excludesFactory() {
        return includesFactory;
    }

    @Override
    public AutoProxyRetryerExcludesWithIncludesClassFactory excludesWithIncludesFactory() {
        return includesWithExcludesFactory;
    }
}

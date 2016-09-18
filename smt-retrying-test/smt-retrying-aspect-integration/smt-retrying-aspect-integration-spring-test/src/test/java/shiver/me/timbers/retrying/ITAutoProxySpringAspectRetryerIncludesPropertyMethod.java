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
import shiver.me.timbers.retrying.factory.AutoProxyRetryerDefaultsMethodFactory;
import shiver.me.timbers.retrying.factory.AutoProxyRetryerIncludesMethodFactory;
import shiver.me.timbers.retrying.factory.AutoProxyRetryerIncludesWithExcludesMethodFactory;

public class ITAutoProxySpringAspectRetryerIncludesPropertyMethod extends AbstractITSpringAspectRetryerIncludesPropertyMethod {

    @Autowired
    private AutoProxyRetryerDefaultsMethodFactory defaultsFactory;

    @Autowired
    private AutoProxyRetryerIncludesMethodFactory includesFactory;

    @Autowired
    private AutoProxyRetryerIncludesWithExcludesMethodFactory includesWithExcludesFactory;

    @Override
    public AutoProxyRetryerDefaultsMethodFactory defaultsFactory() {
        return defaultsFactory;
    }

    @Override
    public AutoProxyRetryerIncludesMethodFactory includesFactory() {
        return includesFactory;
    }

    @Override
    public AutoProxyRetryerIncludesWithExcludesMethodFactory includesWithExcludesFactory() {
        return includesWithExcludesFactory;
    }
}

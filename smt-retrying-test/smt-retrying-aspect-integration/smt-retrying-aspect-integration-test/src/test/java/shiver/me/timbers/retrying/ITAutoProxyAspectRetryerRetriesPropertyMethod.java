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

import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import shiver.me.timbers.retrying.factory.AutoProxyRetryerDefaultsMethodFactory;
import shiver.me.timbers.retrying.factory.AutoProxyRetryerRetriesMethodFactory;
import shiver.me.timbers.retrying.junit.RetryerPropertyRule;
import shiver.me.timbers.retrying.property.SystemPropertyManager;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RetryerConfiguration.class)
public class ITAutoProxyAspectRetryerRetriesPropertyMethod extends AbstractITAspectRetryerRetriesPropertyMethod {

    @Rule
    public RetryerPropertyRule properties = new RetryerPropertyRule(new SystemPropertyManager());

    @Autowired
    private AutoProxyRetryerDefaultsMethodFactory defaultsFactory;

    @Autowired
    private AutoProxyRetryerRetriesMethodFactory retriesFactory;

    @Override
    public RetryerPropertyRule properties() {
        return properties;
    }

    @Override
    public AutoProxyRetryerDefaultsMethodFactory defaultsFactory() {
        return defaultsFactory;
    }

    @Override
    public AutoProxyRetryerRetriesMethodFactory retriesFactory() {
        return retriesFactory;
    }
}

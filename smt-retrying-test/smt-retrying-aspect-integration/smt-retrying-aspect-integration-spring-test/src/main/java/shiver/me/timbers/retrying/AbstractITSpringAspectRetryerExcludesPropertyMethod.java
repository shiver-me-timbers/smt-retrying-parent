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

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import shiver.me.timbers.retrying.junit.RetryerPropertyRule;
import shiver.me.timbers.retrying.property.DynamicPropertySource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringAspectRetryerConfiguration.class)
public abstract class AbstractITSpringAspectRetryerExcludesPropertyMethod extends AbstractITAspectRetryerExcludesPropertyMethod {

    @Autowired
    private DynamicPropertySource dynamicPropertySource;

    @Rule
    public RetryerPropertyRule properties = new RetryerPropertyRule(null);

    @Before
    public void setUp() {
        properties.setPropertyManager(dynamicPropertySource);
    }

    @Override
    public RetryerPropertyRule properties() {
        return properties;
    }
}

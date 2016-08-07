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

package shiver.me.timbers.retrying.property;

import org.springframework.core.env.PropertySource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DynamicPropertySource extends PropertySource<Map<String, String>> implements PropertyManager {

    public DynamicPropertySource() {
        this(new ConcurrentHashMap<String, String>());
    }

    public DynamicPropertySource(Map<String, String> source) {
        super("mapPropertySource", source);
    }

    @Override
    public String getProperty(String key) {
        return getSource().get(key);
    }

    @Override
    public void setProperty(String key, String value) {
        getSource().put(key, value);
    }

    @Override
    public void restoreProperties() {
        getSource().clear();
    }
}

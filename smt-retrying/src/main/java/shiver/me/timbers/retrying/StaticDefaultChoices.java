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

import java.util.Set;

import static java.util.Collections.emptySet;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * @author Karl Bennett
 */
class StaticDefaultChoices extends AbstractOverridingChoices implements DefaultChoices {

    static final Integer DEFAULT_RETRIES = 5;
    static final Time DEFAULT_INTERVAL = new Time(MILLISECONDS, 100L);
    static final Set<Class<? extends Throwable>> DEFAULT_INCLUDES = emptySet();
    static final Set<Class<? extends Throwable>> DEFAULT_EXCLUDES = emptySet();

    @Override
    public Integer getRetries() {
        return DEFAULT_RETRIES;
    }

    @Override
    public Time getInterval() {
        return DEFAULT_INTERVAL;
    }

    @Override
    public Set<Class<? extends Throwable>> getIncludes() {
        return DEFAULT_INCLUDES;
    }

    @Override
    public Set<Class<? extends Throwable>> getExcludes() {
        return DEFAULT_EXCLUDES;
    }
}

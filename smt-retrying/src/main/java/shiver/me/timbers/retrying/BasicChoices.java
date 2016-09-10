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

/**
 * @author Karl Bennett
 */
class BasicChoices extends AbstractChoices implements Choices {

    private final Integer retries;
    private final Time interval;
    private final Set<Class<? extends Throwable>> includes;
    private final Set<Class<? extends Throwable>> excludes;

    BasicChoices(
        Integer retries,
        Time interval,
        Set<Class<? extends Throwable>> includes,
        Set<Class<? extends Throwable>> excludes
    ) {
        this.retries = retries;
        this.interval = interval;
        this.includes = includes;
        this.excludes = excludes;
    }

    @Override
    public Integer getRetries() {
        return retries;
    }

    public Time getInterval() {
        return interval;
    }

    @Override
    public Set<Class<? extends Throwable>> getIncludes() {
        return includes;
    }

    @Override
    public Set<Class<? extends Throwable>> getExcludes() {
        return excludes;
    }
}

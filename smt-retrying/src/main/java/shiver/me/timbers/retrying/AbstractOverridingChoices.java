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

import java.util.HashSet;
import java.util.Set;

/**
 * @author Karl Bennett
 */
abstract class AbstractOverridingChoices extends AbstractChoices implements OverridingChoices {

    @Override
    public Choices overrideWith(Choices choices) {
        return new BasicChoices(overrideRetries(choices), overrideInterval(choices), addIncludes(choices));
    }

    private Integer overrideRetries(Choices choices) {
        return override(getRetries(), choices.getRetries());
    }

    private Time overrideInterval(Choices choices) {
        return override(getInterval(), choices.getInterval());
    }

    private Set<Class<? extends Throwable>> addIncludes(Choices choices) {
        final Set<Class<? extends Throwable>> additions = choices.getIncludes();
        if (additions == null || additions.isEmpty()) {
            return getIncludes();
        }
        // Need a new set for the current includes so that we don't mutate someone else's Set.
        final Set<Class<? extends Throwable>> includes = new HashSet<>(getIncludes());
        includes.addAll(additions);
        return includes;
    }

    private <T> T override(T current, T override) {
        return override == null ? current : override;
    }
}

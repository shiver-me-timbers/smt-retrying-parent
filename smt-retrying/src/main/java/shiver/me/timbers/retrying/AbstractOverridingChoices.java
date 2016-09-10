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
        return new BasicChoices(
            overrideRetries(choices),
            overrideInterval(choices),
            addIncludes(choices),
            addExcludes(choices)
        );
    }

    private Integer overrideRetries(Choices choices) {
        return override(getRetries(), choices.getRetries());
    }

    private Time overrideInterval(Choices choices) {
        return override(getInterval(), choices.getInterval());
    }

    private Set<Class<? extends Throwable>> addIncludes(final Choices choices) {
        return add(new Values<Class<? extends Throwable>>() {
            @Override
            public Set<Class<? extends Throwable>> getCurrent() {
                return getIncludes();
            }

            @Override
            public Set<Class<? extends Throwable>> getAdditional() {
                return choices.getIncludes();
            }
        });
    }

    private Set<Class<? extends Throwable>> addExcludes(final Choices choices) {
        return add(new Values<Class<? extends Throwable>>() {
            @Override
            public Set<Class<? extends Throwable>> getCurrent() {
                return getExcludes();
            }

            @Override
            public Set<Class<? extends Throwable>> getAdditional() {
                return choices.getExcludes();
            }
        });
    }

    private static <T> T override(T current, T override) {
        return override == null ? current : override;
    }

    private static <T> Set<T> add(Values<T> values) {
        final Set<T> additions = values.getAdditional();
        if (additions == null || additions.isEmpty()) {
            return values.getCurrent();
        }
        // Need a new set so that we don't mutate or expose someone else's Set.
        final Set<T> current = new HashSet<>(values.getCurrent());
        current.addAll(additions);
        return current;
    }

    private interface Values<T> {
        Set<T> getCurrent();

        Set<T> getAdditional();
    }
}

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
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

/**
 * @author Karl Bennett
 */
abstract class AbstractPropertyChoices extends AbstractOverridingChoices implements PropertyChoices {

    @Override
    public Integer getRetries() {
        return toIntegerOrNull(getRetriesProperty());
    }

    @Override
    public Time getInterval() {
        final Long duration = toLongOrNull(getIntervalDurationProperty());
        final TimeUnit unit = toTimeUnitOrNull(getIntervalUnitProperty());
        if (duration == null || unit == null) {
            return null;
        }

        return new Time(duration, unit);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Class<? extends Throwable>> getIncludes() {
        final String includesProperty = getIncludesProperty();
        return (Set) toClasses(
            includesProperty == null || includesProperty.isEmpty() ? new String[0] : includesProperty.split(",")
        );
    }

    abstract String getRetriesProperty();

    abstract String getIntervalDurationProperty();

    abstract String getIntervalUnitProperty();

    abstract String getIncludesProperty();

    private static Integer toIntegerOrNull(String string) {
        return string == null ? null : Integer.valueOf(string);
    }

    private static Long toLongOrNull(String string) {
        return string == null ? null : Long.valueOf(string);
    }

    private static TimeUnit toTimeUnitOrNull(String string) {
        return string == null ? null : TimeUnit.valueOf(string);
    }

    private static Set<Class> toClasses(String[] split) {
        final Set<Class> classes = new HashSet<>(split.length);
        for (String className : split) {
            try {
                classes.add((Class) Class.forName(className));
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException(
                    format("Could not load the throwable class (%s).", className),
                    e
                );
            }
        }
        return classes;
    }
}

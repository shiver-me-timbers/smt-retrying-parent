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

import static java.util.Arrays.asList;

/**
 * This class is used to customise the options for the {@link Retryer}. With this you can set the number of times the
 * execution should be retried.
 * <p>
 * The {@code Options} can also be configured globally with the following properties.
 * <code>
 * smt.retryer.retries            # Integer
 * smt.retryer.interval.durations # A comma separated list of numbers that represent the sequence of durations.
 * smt.retryer.interval.unit      # Name of one of the java.util.concurrent.TimeUnit enums
 * smt.retryer.includes           # A comma separated list of fully qualified exception class names.
 * smt.retryer.excludes           # A comma separated list of fully qualified exception class names.
 * </code>
 *
 * @author Karl Bennett
 */
public class Options implements OptionsService {

    private final DefaultChoices defaultChoices;
    private final PropertyChoices propertyChoices;
    private final ManualChoices<Options> manualChoices;

    private Integer retries;
    private Time interval;
    private Set<Class<? extends Throwable>> includes;
    private Set<Class<? extends Throwable>> excludes;

    public Options() {
        this(new StaticDefaultChoices(), new SystemPropertyChoices(), new OptionsManualChoices());
    }

    Options(
        DefaultChoices defaultChoices,
        PropertyChoices propertyChoices,
        ManualChoices<Options> manualChoices
    ) {
        this.defaultChoices = defaultChoices;
        this.propertyChoices = propertyChoices;
        this.manualChoices = manualChoices;
    }

    @Override
    public Options withRetries(Integer retries) {
        this.retries = retries;
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Options includes(Class<? extends Throwable>... includes) {
        this.includes = new HashSet<>(asList(includes));
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Options excludes(Class<? extends Throwable>... excludes) {
        this.excludes = new HashSet<>(asList(excludes));
        return this;
    }

    @Override
    public Options withIntervals(TimeUnit unit, Long... durations) {
        this.interval = new Time(unit, durations);
        return this;
    }

    Chooser chooser() {
        return new CompositeOverridingChooser(defaultChoices, propertyChoices, manualChoices.apply(this));
    }

    Integer getRetries() {
        return retries;
    }

    Time getInterval() {
        return interval;
    }

    Set<Class<? extends Throwable>> getIncludes() {
        return includes;
    }

    public Set<Class<? extends Throwable>> getExcludes() {
        return excludes;
    }
}

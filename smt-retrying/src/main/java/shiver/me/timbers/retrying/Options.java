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

/**
 * This class is used to customise the options for the {@link Retryer}. With this you can set the number of times the
 * execution should be retried.
 * <p>
 * The {@code Options} can also be configured globally with the following properties.
 * <code>
 * smt.retryer.retries  # Integer
 * </code>
 *
 * @author Karl Bennett
 */
public class Options implements OptionsService {

    private final DefaultChoices defaultChoices;
    private final PropertyChoices propertyChoices;
    private final ManualChoices<Options> manualChoices;

    private Integer retries;

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

    Choice choose() {
        return defaultChoices.overrideWith(
            propertyChoices.overrideWith(
                manualChoices.apply(this)
            )
        ).choose();
    }

    Integer getRetries() {
        return retries;
    }
}

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

import java.lang.reflect.InvocationTargetException;

/**
 * @author Karl Bennett
 */
class OptionsServiceFactory implements Factory<OptionsService, Retry> {

    private final Instantiater<OptionsService> instantiater;
    private final Configurer<OptionsService, Retry> configurer;

    OptionsServiceFactory(
        Instantiater<OptionsService> instantiater,
        Configurer<OptionsService, Retry> configurer
    ) {
        this.instantiater = instantiater;
        this.configurer = configurer;
    }

    @Override
    public OptionsService create(Retry retry) {
        final OptionsService options = instantiate();
        configurer.configure(options, retry);
        return options;
    }

    private OptionsService instantiate() {
        try {
            return instantiater.instantiate();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("Could not instantiate the OptionsService.", e);
        }
    }
}

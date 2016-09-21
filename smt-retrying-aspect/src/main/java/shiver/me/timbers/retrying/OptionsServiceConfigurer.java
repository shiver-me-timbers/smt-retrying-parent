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
 * @author Karl Bennett
 */
class OptionsServiceConfigurer implements Configurer<OptionsService, Retry> {

    @Override
    public void configure(OptionsService optionsService, Retry retry) {
        configureRetries(optionsService, retry.value());
        configureInterval(optionsService, retry.interval());
        configureIncludes(optionsService, retry.includes());
        configureExcludes(optionsService, retry.excludes());
    }

    private static void configureRetries(OptionsService optionsService, int retries) {
        if (retries < 0) {
            return;
        }
        optionsService.withRetries(retries);
    }

    private static void configureInterval(OptionsService optionsService, Interval interval) {
        final long[] durations = interval.duration();
        if (durations.length == 0) {
            return;
        }
        optionsService.withIntervals(interval.unit(), autoBox(durations));
    }

    private static void configureIncludes(OptionsService optionsService, Class<? extends Throwable>[] includes) {
        if (includes.length == 0) {
            return;
        }
        optionsService.includes(includes);
    }

    private static void configureExcludes(OptionsService optionsService, Class<? extends Throwable>[] excludes) {
        if (excludes.length == 0) {
            return;
        }
        optionsService.excludes(excludes);
    }

    private static Long[] autoBox(long[] longs) {
        final Long[] objects = new Long[longs.length];
        for (int i = 0; i < longs.length; i++) {
            objects[i] = longs[i];
        }
        return objects;
    }
}

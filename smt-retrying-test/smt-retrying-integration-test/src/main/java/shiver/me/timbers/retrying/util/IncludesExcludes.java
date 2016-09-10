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

package shiver.me.timbers.retrying.util;


import shiver.me.timbers.retrying.Options;

import java.util.List;

import static java.util.Arrays.asList;
import static shiver.me.timbers.retrying.util.Classes.toClasses;

public class IncludesExcludes {

    public static Options addIncludes(Options options, Throwable... includes) {
        return addIncludes(options, asList(includes));
    }

    @SuppressWarnings("unchecked")
    public static Options addIncludes(Options options, List<Throwable> includes) {
        options.includes(toClasses(includes).toArray(new Class[includes.size()]));
        return options;
    }

    public static Options addExcludes(Options options, Throwable... excludes) {
        return addExcludes(options, asList(excludes));
    }

    @SuppressWarnings("unchecked")
    public static Options addExcludes(Options options, List<Throwable> excludes) {
        options.excludes(toClasses(excludes).toArray(new Class[excludes.size()]));
        return options;
    }
}

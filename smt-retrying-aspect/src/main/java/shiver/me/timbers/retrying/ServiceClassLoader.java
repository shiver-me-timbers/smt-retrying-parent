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

import java.util.Iterator;

/**
 * @author Karl Bennett
 */
class ServiceClassLoader<T> {

    private final Class<T> type;
    private final Class<? extends T> defaultType;

    ServiceClassLoader(Class<T> type, Class<? extends T> defaultType) {
        this.type = type;
        this.defaultType = defaultType;
    }

    @SuppressWarnings("unchecked")
    Class<T> load() {
        final Iterator<T> loaderIterator = java.util.ServiceLoader.load(type).iterator();
        if (loaderIterator.hasNext()) {
            return (Class<T>) loaderIterator.next().getClass();
        }

        return (Class<T>) defaultType;
    }
}

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

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Add this annotation to any class or method to wrap the method call within a {@link Retryer}. Annotating at the class
 * level will wrap all methods within that class.
 * <p>
 * All {@link Options} values can be set with this annotation.
 * <p>
 *
 * @author Karl Bennett
 */
@Target({METHOD, TYPE})
@Retention(RUNTIME)
public @interface Retry {

    /**
     * Set many times to retry the method till it succeeds.
     */
    int value() default -1;

    /**
     * Set how long the retryer should wait between retries of the failing method.
     */
    Interval interval() default @Interval(duration = {}, unit = MILLISECONDS);

    /**
     * Set the exceptions or errors that should result in a retry.
     */
    Class<? extends Throwable>[] includes() default {};

    /**
     * Set the exceptions or errors that should never result in a retry.
     */
    Class<? extends Throwable>[] excludes() default {};
}

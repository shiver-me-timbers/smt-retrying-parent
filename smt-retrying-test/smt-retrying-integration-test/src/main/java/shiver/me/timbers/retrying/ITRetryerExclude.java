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

import shiver.me.timbers.retrying.execution.RetryerExcludes;

import java.util.List;

public interface ITRetryerExclude extends ExpectedExceptionAware {

    void Cannot_ignore_exceptions_that_are_contained_in_the_exclude_list() throws Throwable;

    void Can_ignore_exceptions_that_are_not_contained_in_the_exclude_list() throws Throwable;

    void Can_ignore_all_exceptions_if_no_excludes_set() throws Throwable;

    void Cannot_ignore_exceptions_contained_in_the_exclude_list_and_not_in_the_include_list() throws Throwable;

    void Excludes_take_precedence_over_includes() throws Throwable;

    RetryerExcludes excludes(int retries, Throwable... excludes);

    RetryerExcludes excludesWithIncludes(int retries, List<Throwable> excludes, List<Throwable> includes);
}

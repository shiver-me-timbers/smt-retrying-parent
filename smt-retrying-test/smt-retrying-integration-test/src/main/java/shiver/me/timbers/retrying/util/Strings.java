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

import java.util.ArrayList;
import java.util.List;

public class Strings {

    public static String concat(String delimiter, List<String> strings) {
        return concat(new StringBuilder(), delimiter, strings);
    }

    private static String concat(StringBuilder stringBuilder, String delimiter, List<String> strings) {
        if (strings.isEmpty()) {
            return "";
        }
        if (strings.size() == 1) {
            return strings.remove(0);
        }
        return stringBuilder.append(strings.remove(0)).append(delimiter)
            .append(concat(new StringBuilder(), delimiter, strings))
            .toString();
    }

    @SuppressWarnings("unchecked")
    public static <T> List<String> toStrings(T... objects) {
        final List<String> strings = new ArrayList<>(objects.length);
        for (Object object : objects) {
            strings.add(object.toString());
        }
        return strings;
    }
}

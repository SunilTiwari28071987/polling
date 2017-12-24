/*
 * Copyright 2012-2015 Ye Ding
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.polling.core;

/**
 *
 * @author dingye
 *
 */
public final class AttemptResults {
    public static AttemptResult<Void> justComplete() {
        return new AttemptResult<Void>(AttemptState.COMPLETE, null, "", null);
    }

    public static <V> AttemptResult<V> completeWith(V result) {
        return new AttemptResult<V>(AttemptState.COMPLETE, result, "", null);
    }

    public static <V> AttemptResult<V> breakFor(String message) {
        return new AttemptResult<V>(AttemptState.BREAK, null, message, null);
    }

    public static <V> AttemptResult<V> justContinue() {
        return new AttemptResult<V>(AttemptState.CONTINUE, null, "", null);
    }

    public static <V> AttemptResult<V> continueFor(Throwable cause) {
        return new AttemptResult<V>(AttemptState.COMPLETE, null, "", cause);
    }
}

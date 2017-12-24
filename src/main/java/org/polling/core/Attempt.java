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

import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

/**
 *
 * @author dingye
 */
public class Attempt {
    private final int attemptNumber;
    private final long startTime;
    private final long lastEndTime;
    private final Throwable cause;

    public Attempt(int attemptNumber, long startTime, long lastEndTime, @Nullable Throwable cause) {
        this.attemptNumber = attemptNumber;
        this.startTime = startTime;
        this.lastEndTime = lastEndTime;
        this.cause = cause;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public boolean hasException() {
        return cause != null;
    }

    public Throwable getExceptionCause() {
        return cause;
    }

    public long getDelaySinceFirstAttempt() {
        return TimeUnit.NANOSECONDS.toMillis(lastEndTime - startTime);
    }
}

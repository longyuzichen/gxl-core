/**
 * Copyright [2017] guoxinlei(longyuzichen@126.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package com.longyuzichen.core.exception;

/**
 * com.longyuzichen.core.exception
 *
 * @author longyuzichen@126.com
 * @version V1.0
 * @desc
 * @date 2018-08-03 23:20
 */
public class LongyuzichenException extends RuntimeException {

    private static final long seralVersionUID = -3456789077L;

    public LongyuzichenException() {
        super();
    }

    public LongyuzichenException(String message, Throwable cause) {
        super(message, cause);
    }

    public LongyuzichenException(String message) {
        super(message);
    }

    public LongyuzichenException(Throwable cause) {
        super(cause);
    }
}

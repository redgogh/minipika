package org.raniaia.approve.components.log;

/*
 * Copyright (C) 2020 tiansheng All rights reserved.
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

/*
 * Creates on 2020/3/26.
 */


import org.raniaia.approve.components.log.slf4j.Slf4jLogAdapter;
import org.raniaia.approve.framework.exception.LogException;

import java.lang.reflect.Constructor;

/**
 * @author tiansheng
 */
public class LogFactory {

    private static Constructor<? extends LogAdapter> logAdapterConstructor;

    static {
        tryFindLogImplementation(LogFactory::useSlf4jLogging);
    }

    public static Log getLog(Class<?> key) {
        return getLog(key.getName());
    }

    public static Log getLog(String key) {
        Log log;
        try {
            log = logAdapterConstructor.newInstance().getLog(key);
        } catch (Exception e) {
            throw new LogException("Error creates logger for logger " + key + " . Cause: " + e, e);
        }
        return log;
    }

    private static void useSlf4jLogging() {
        setLogImplementation(Slf4jLogAdapter.class);
    }

    private static void tryFindLogImplementation(Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable e) {
            // ignore
        }
    }

    private static void setLogImplementation(Class<? extends LogAdapter> implClass) {
        try {
            Constructor<? extends LogAdapter> candidate = implClass.getConstructor();
            Log log = candidate.newInstance().getLog(LogFactory.class.getName());
            log.debug("Logger using '" + implClass + "' adapter.");
            logAdapterConstructor = candidate;
        } catch (Throwable e) {
            // ignore
        }
    }

}

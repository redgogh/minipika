package org.raniaia.approve.framework.tools;

/*
 * Copyright (C) 2020 Tiansheng All rights reserved.
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
 * Creates on 2020/2/7.
 */

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author tiansheng
 */
public class NormUtils {

    private boolean IS_INSTANCE = false;

    private Map<String, Pattern> patternMap;

    private static NormUtils INSTANCE = null;
    private static NormUtils INSTANCE_SAVE = null;

    public NormUtils() {
    }

    public NormUtils(boolean IS_INSTANCE) {
        this.IS_INSTANCE = IS_INSTANCE;
        if(!IS_INSTANCE){
            patternMap = new HashMap<>();
        }
    }

    public static NormUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NormUtils(true);
        }
        return INSTANCE;
    }

    public static NormUtils getInstanceSave() {
        if (INSTANCE_SAVE == null) {
            INSTANCE_SAVE = new NormUtils(false);
        }
        return INSTANCE_SAVE;
    }

    /**
     * 匹配
     * @param value
     * @param regex
     * @return
     */
    public boolean matches(String value, String regex) {
        if (StringUtils.isEmpty(regex)) return false;
        Pattern pattern = null;
        if (!IS_INSTANCE) {
            pattern = patternMap.get(regex);
            if (pattern == null) {
                pattern = Pattern.compile(regex);
                patternMap.put(regex, pattern);
            }
        }
        return pattern.matcher(value).matches();
    }

    public Pattern getPattern(String regex) {
        if (patternMap == null) return null;
        return patternMap.get(regex);
    }

}

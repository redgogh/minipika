package org.raniaia.poseidon.framework.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Create by tiansheng on 2020/2/7 1:07
 *
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
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

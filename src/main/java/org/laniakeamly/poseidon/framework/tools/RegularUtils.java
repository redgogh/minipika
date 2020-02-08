package org.laniakeamly.poseidon.framework.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Create by TianSheng on 2020/2/7 1:07
 *
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public class RegularUtils {

    private boolean IS_INSTANCE = false;

    private Map<String, Pattern> patternMap;

    private static RegularUtils INSTANCE = null;
    private static RegularUtils INSTANCE_SAVE = null;

    public RegularUtils() {
    }

    public RegularUtils(boolean IS_INSTANCE) {
        this.IS_INSTANCE = IS_INSTANCE;
        if(!IS_INSTANCE){
            patternMap = new HashMap<>();
        }
    }

    public static RegularUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RegularUtils(true);
        }
        return INSTANCE;
    }

    public static RegularUtils getInstanceSave() {
        if (INSTANCE_SAVE == null) {
            INSTANCE_SAVE = new RegularUtils(false);
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

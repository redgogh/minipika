package org.laniakeamly.poseidon.framework.tools;

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

    private static RegularUtils ru = null;

    public RegularUtils(){}

    public RegularUtils(boolean IS_INSTANCE){
        this.IS_INSTANCE = IS_INSTANCE;
    }

    public static RegularUtils getInstance(){
        if(ru == null){
            ru = new RegularUtils(true);
        }
        return ru;
    }

    /**
     * 匹配
     * @param value
     * @param regex
     * @return
     */
    public boolean matches(String value, String regex) {
        Pattern pattern = null;
        if (IS_INSTANCE) {
            pattern = patternMap.get(regex);
            if (pattern == null) {
                pattern = Pattern.compile(regex);
                patternMap.put(regex, pattern);
            }
        }
        return pattern.matcher(value).matches();
    }

}

package com.tengu.tools;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/12 0:10
 * @since 1.8
 */
public class TenguTools {

    public static String humpToUnderline(String string) {
        StringBuilder builder = new StringBuilder(string);
        int temp = 0; // 定位
        for (int i = 0, len = string.length(); i < len; i++) {
            if (Character.isUpperCase(string.charAt(i))) {
                builder.insert(i + temp, "_");
                temp++;
            }
        }
        return builder.toString().toLowerCase();
    }

    public static String UnderlineToHump(String string) {
        StringBuilder builder = new StringBuilder();
        String[] strs = string.split("_");
        builder.append(strs[0]);
        for(int i=1; i<strs.length; i++){
            StringBuilder v = new StringBuilder(strs[i]);
            v.replace(0,1,String.valueOf(v.charAt(0)).toUpperCase());
            builder.append(v);
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(UnderlineToHump("user_name_and_password"));
    }

}

package com.poseidon.framework.sql.pte;

import com.poseidon.framework.tools.PoseidonUtils;
import com.poseidon.framework.tools.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对pte文件预处理
 * Create by 2BKeyboard on 2019/12/11 14:23
 */
public class PreProcess {

    private final List<File> files;
    private final Map<String, StringBuilder> codes;

    private Pattern lineComment = Pattern.compile("[//](.*)"); // 单行删除
    private Pattern multiLineComment = Pattern.compile("[#\\-\\-](.*?)[\\-\\-#]"); // 单行删除

    public PreProcess() {
        this.files = PoseidonUtils.getPteFiles();
        this.codes = new HashMap<>(26);
    }

    /**
     * 读取代码
     * @return
     */
    public Map<String, StringBuilder> readCode() {
        BufferedReader reader = null;
        try {
            for (File file : files) {
                // 存放File文件的内容
                StringBuilder builder = new StringBuilder(500);
                String filename = StringUtils.removeSuffix(file.getName());
                String line = null;
                reader = new BufferedReader(new FileReader(file));
                while ((line = reader.readLine()) != null) {
                    builder.append(clearLineComment(line));
                }
                clearComment(builder);
                System.out.println(builder.toString());
                codes.put(filename, builder);
                close(reader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(reader);
        }
        return codes;
    }

    /**
     * 多行注释擦除
     *
     * @param builder   整个文件
     */
    public void clearComment(StringBuilder builder) {
        Matcher matcher = multiLineComment.matcher(builder.toString());
        String temp = matcher.replaceAll("");
        builder.delete(0,builder.length());
        builder.append(temp);
    }

    /**
     * 单行擦除
     * @param str 单行数据
     */
    private String clearLineComment(String str) {
        Matcher matcher = lineComment.matcher(str);
        return matcher.replaceAll("");
    }

    //
    // 关闭流
    //
    public void close(Closeable in) {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PreProcess();
    }

}

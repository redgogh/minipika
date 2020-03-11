package org.raniaia.poseidon.framework.tools;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

/**
 * Create by TianSheng on 2020/2/7 1:06
 *
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public class PIOUtils {

    /**
     * 获取Resource文件夹下的文件
     * @param name
     * @return
     */
    public static InputStream getResourceAsStream(String name){
        return PIOUtils.class.getClassLoader().getResourceAsStream(name);
    }

    /**
     * 从输入流中读取String
     * @param input
     * @return
     */
    public static String getStringByInputStream(InputStream input){
        if(input == null) throw new NullPointerException();
        final int size = 1024;
        final char[] buffer = new char[size];
        final StringBuilder out = new StringBuilder();
        try {
            Reader reader = new InputStreamReader(input, "UTF-8");
            while(true){
                int rsz = reader.read(buffer,0,size);
                if(rsz < 0) break;
                out.append(buffer,0,rsz);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return out.toString();
    }

    /**
     * 获取resource目录下的json文件并转换成{@link JSONObject}
     * @param name
     * @return
     */
    public static JSONObject getResourceAsJson(String name){
        return JSONObject.parseObject(getStringByInputStream(getResourceAsStream(name)));
    }

    /**
     * 读取resource目录下的Properties文件
     * 根据{@link InputStream}读取
     *
     * @param input
     * @return
     */
    public static Properties getResourceAsProperties(InputStream input){
        Properties p = null;
        try {
            if(input != null){
                p = new Properties();
                p.load(input);
                return p;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取resource目录下的Properties文件
     * 根据文件名读取
     *
     * @param name
     * @return
     */
    public static Properties getResourceAsProperties(String name){
        return getResourceAsProperties(getResourceAsStream(name));
    }

    /**
     * 将resource下的文件读取成String
     * @param name
     * @return
     */
    public static String getResourceAsString(String name){
        return getStringByInputStream(getResourceAsStream(name));
    }
    
}

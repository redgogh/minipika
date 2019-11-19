package com.tengu.config;

import com.tengu.annotation.Model;
import com.tengu.db.JdbcFunction;
import com.tengu.db.NativeJdbc;
import com.tengu.exception.ParseException;
import com.tengu.model.ModelAttribute;
import com.tengu.model.ParseModel;
import com.tengu.tools.StringUtils;
import com.tengu.tools.TenguUtils;

import java.io.InputStream;
import java.util.*;

/**
 * 配置以及初始化操作
 *
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/4 14:10
 * @since 1.8
 */
public class Config {

    // properties对象
    private static Properties config;

    // 配置文件地址
    private static String configPath;

    // jdbc连接驱动
    private static String url = getValue("tengu.jdbc.url");
    private static String driver = getValue("tengu.jdbc.driver");

    // 数据库账号密码
    private static String username = getValue("tengu.jdbc.username");
    private static String password = getValue("tengu.jdbc.password");

    // 连接池配置
    private static String maxSize = getValue("tengu.connectionPool.maxSize");
    private static String minSize = getValue("tengu.connectionPool.minSize");

    // 数据库表名前缀
    private static String tablePrefix = getValue("tengu.model.prefix");

    // model包路径
    private static String modelPackage = getValue("tengu.model.package");

    // 储存引擎
    private static String modelEngine = getValue("tengu.model.engine");

    // 数据库名
    private static String dbname;

    // 添加字段
    private static final String ADD_COLUMN_SCRIPT = "ALTER TABLE `%s` ADD %s after `%s`;";

    static {
        try {
            String temp = url;
            for (int i = 0; i < 3; i++) {
                temp = temp.substring(temp.indexOf("/") + 1);
            }
            dbname = temp.substring(0, temp.indexOf("?"));
            // 解析model
            parseModel();
            // 对字段进行检查
            checkNewColumns();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析model
     */
    private static void parseModel() {
        ParseModel parseModel = new ParseModel();
        parseModel.parse(TenguUtils.getModels());
        Map<String, ModelAttribute> messages = ModelAttribute.getMessages();
        Iterator iter = messages.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, ModelAttribute> entry = (Map.Entry<String, ModelAttribute>) iter.next();
            ModelAttribute message = entry.getValue();
            JdbcFunction.getFunction().execute(message.getCreateTableSql());
        }
    }

    /**
     * 检测是否有新增的字段
     *
     * @throws ParseException
     */
    private static void checkNewColumns() throws ParseException {
        List<Class<?>> models = TenguUtils.getModels();
        for (Class<?> target : models) {
            if (target.isAnnotationPresent(Model.class)) {
                Model model = target.getDeclaredAnnotation(Model.class);
                String table = model.value();
                List<String> inDbColumns = JdbcFunction.getFunction().getColumns(table);
                ModelAttribute message = ModelAttribute.getMessages().get(table);
                Map<String, String> inMessageColumns = message.getColumns();
                Iterator iter = inMessageColumns.entrySet().iterator();
                String previousKey = null;
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
                    String key = entry.getKey();
                    if (!inDbColumns.contains(key)) {
                        String executeScript = String.format(ADD_COLUMN_SCRIPT, message.getTableName(), entry.getValue(),previousKey);
                        NativeJdbc.getJdbc().execute(executeScript);
                    }
                    previousKey = key;
                }
            } else {
                throw new ParseException("没有@Model注解");
            }
        }
    }

    private static String getValue(String v) {
        try {
            if (config == null) {
                // 如果configPath等于空
                if (StringUtils.isEmpty(configPath)) {
                    configPath = "tengu.properties";
                }
                InputStream in = Config.class.getClassLoader().getResourceAsStream(configPath);
                config = new Properties();
                config.load(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return config.getProperty(v);
    }

    public static String getDbname() {
        return dbname;
    }

    public static String getModelPackage() {
        return modelPackage;
    }

    public static String getUrl() {
        return url;
    }

    public static String getDriver() {
        return driver;
    }

    public static String getTablePrefix() {
        return tablePrefix;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static Integer getMaxSize() {
        return Integer.valueOf(StringUtils.isEmpty(maxSize) ? "6" : maxSize);
    }

    public static Integer getMinSize() {
        return Integer.valueOf(StringUtils.isEmpty(minSize) ? "2" : minSize);
    }

    public static String getEngine() {
        return StringUtils.isEmpty(modelEngine) ? "innodb" : modelEngine;
    }
}

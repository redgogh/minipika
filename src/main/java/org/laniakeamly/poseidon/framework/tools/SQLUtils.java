package org.laniakeamly.poseidon.framework.tools;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by 2BKeyboard on 2020/1/30 14:40
 */
public class SQLUtils {

    /**
     * 获取sql中的表名
     * @param sql
     * @return
     */
    public static List<String> getSQLTables(String sql) {
        TablesNamesFinder finder = new TablesNamesFinder();
        Statement statement = null;
        try {
            statement = CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            return new ArrayList<>();
        }
        List<String> tables = finder.getTableList(statement);
        for (int i = 0, len = tables.size(); i < len; i++) {
            tables.set(i, tables.get(i).replace("`", ""));
        }
        return tables;
    }

    /**
     * 构建带参数的sql
     * @param sql
     * @param args
     * @return
     */
    public static String buildPreSQL(String sql, Object[] args) {
        StringBuilder builder = new StringBuilder();
        char[] charArray = sql.toCharArray();
        int index = -1;
        for (int i = 0; i < charArray.length; i++) {
            char ichar = charArray[i];
            if (ichar == '?') {
                Object object = args[index=(index+1)];
                if(object instanceof String) {
                    builder.append("'");
                    builder.append(object);
                    builder.append("'");
                }else{
                    builder.append(object);
                }
            } else {
                builder.append(ichar);
            }
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        String sql = "select * from user where username = ? and userage = ?";
        Object[] objects = new Object[2];
        objects[0] = "keyboard";
        objects[1] = new BigDecimal("18.25");
        System.out.println(buildPreSQL(sql,objects));
    }

}

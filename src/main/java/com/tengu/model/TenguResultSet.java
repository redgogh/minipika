package com.tengu.model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/13 18:12
 * @since 1.8
 */
public class TenguResultSet {

    private Map<String, String> resultSet;

    public TenguResultSet(){}

    public TenguResultSet(ResultSet rset){
        buildResultSet(rset);
    }

    /**
     * 构建TenguResultSet
     *
     * @param rset
     * @return
     * @throws Exception
     */
    public TenguResultSet buildResultSet(ResultSet rset) {
        try {
            rset.next();
            ResultSetMetaData mdata = rset.getMetaData();
            int len = mdata.getColumnCount();
            resultSet = new LinkedHashMap<>(len); // 初始化Map
            for (int i = 0; i < len; i++) {
                String name = mdata.getColumnName(i + 1);
                String value = rset.getString(name);
                resultSet.put(name, value);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }

    public Map<String,String> getResultSet(){
        return this.resultSet;
    }

}

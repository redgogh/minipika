package org.laniakeamly.database;

import org.laniakeamly.poseidon.framework.beans.BeansManager;
import org.laniakeamly.poseidon.framework.db.JdbcSupport;
import org.laniakeamly.poseidon.framework.tools.StringUtils;

public class Insert {

    private static JdbcSupport jdbcSupport = BeansManager.getBean("jdbc");

    public static void main(String[] args) {

        String sql = "INSERT INTO `cloud`.`shiqi_level_integral`( `integral_key`, `integral_value`) VALUES ( {}, 0);";
        for(int i=0; i<45; i++) {
            jdbcSupport.updateByString(StringUtils.format(sql,i));
        }
    }

}

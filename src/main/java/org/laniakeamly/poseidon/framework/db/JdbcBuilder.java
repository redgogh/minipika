package org.laniakeamly.poseidon.framework.db;

import org.laniakeamly.poseidon.framework.sql.SqlMapper;

/**
 * Create by 2BKeyboard on 2019/12/19 14:12
 */
public interface JdbcBuilder {

    <T> T queryForObject(SqlMapper mapper);

}

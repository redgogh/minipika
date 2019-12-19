package org.laniakeamly.poseidon.framework.db;

import org.laniakeamly.poseidon.framework.annotation.Valid;
import org.laniakeamly.poseidon.framework.sql.SqlMapper;

import java.util.List;

/**
 * Create by 2BKeyboard on 2019/12/19 14:20
 */
public class JdbcBuilderImpl implements JdbcBuilder {

    @Valid
    private NativeJdbc nativeJdbc;

    @Valid
    private JdbcSupport jdbcSupport;

    @Override
    public <T> T queryForObject(SqlMapper sqlMapper) {
        return (T) jdbcSupport.queryForObject(sqlMapper.getSql(),sqlMapper.getResult(),sqlMapper.getArgs());
    }

    @Override
    public <T> List<T> queryForList(SqlMapper sqlMapper) {
        return (List<T>) jdbcSupport.queryForList(sqlMapper.getSql(),sqlMapper.getResult(),sqlMapper.getArgs());
    }

    @Override
    public String queryForJson(SqlMapper sqlMapper) {
        return jdbcSupport.queryForJson(sqlMapper.getSql(),sqlMapper.getArgs());
    }

    @Override
    public NativePageHelper queryForPage(SqlMapper sqlMapper, NativePageHelper pageVo) {
        return jdbcSupport.queryForPage(sqlMapper.getSql(),pageVo,sqlMapper.getArgs());
    }

    @Override
    public int update(SqlMapper sqlMapper) {
        return jdbcSupport.update(sqlMapper.getSql(),sqlMapper.getArgs());
    }

    @Override
    public int insert(SqlMapper sqlMapper) {
        return jdbcSupport.insert(sqlMapper.getSql(),sqlMapper.getArgs());
    }
}

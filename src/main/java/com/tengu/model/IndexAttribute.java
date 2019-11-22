package com.tengu.model;

import com.tengu.annotation.IndexType;
import com.tengu.tools.TenguUtils;
import lombok.Data;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/19 15:59
 * @since 1.8
 */
@Data
public class IndexAttribute {

    private String alias;           // 索引别名
    private String[] columns;       // 索引字段
    private IndexType type;         // 索引类型
    private String comment;         // 注释

    private String script;          // 添加索引的脚本

    public void buildScript(String table) {
        StringBuffer buffer = new StringBuffer("alter table `".concat(table).concat("` "));
        if (type == IndexType.NORMAL) {
            buffer.append("add index ").append(alias.concat(" ( ")).append(buildColumns()).append(" ) comment'").append(comment).append("';");
        } else if (type == IndexType.UNIQUE) {
            buffer.append("add unique ").append(alias.concat(" ( ")).append(buildColumns()).append(" ) comment'").append(comment).append("';");
        } else if (type == IndexType.SPATIAL) {
            buffer.append("add spatial index ( ").append(buildColumns()).append(" ) comment'").append(comment).append("';");
        } else if (type == IndexType.FULLTEXT) {
            buffer.append("add fulltext ").append(alias.concat(" ( ")).append(buildColumns()).append(" ) comment'").append(comment).append("';");
        }
        this.script = buffer.toString();
    }

    private String buildColumns() {
        @SuppressWarnings("unchecked")
        StringBuilder _columns = new StringBuilder();
        for (String column : columns) {
            _columns.append("`")
                    .append(TenguUtils.humpToUnderline(column))
                    .append("`,");
        }
        int builderLen = _columns.length();
        _columns.delete(builderLen - 1, builderLen);
        return _columns.toString();
    }

}

package com.poseidon.framework.sql.pte.generate;

import com.poseidon.framework.tools.PteString;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成器，负责生成Java代码
 * Create by 2BKeyboard on 2019/12/12 14:45
 */
public class MarkJavaCode implements GenerateCriteria {

    private PteString marked;

    /**
     * 标记传参的代码所在行以及所在位置和名称
     */
    private List<Position> positionList;

    /**
     * sql语句
     */
    private StringBuilder sql = new StringBuilder(100);

    private String notnull = ".*\\[@NotNull.*?].*";

    private PteNotNullProcess pnp = new PteNotNullProcess();

    public MarkJavaCode() {
        marked = new PteString(100);
    }

    public static void main(String[] args) {
        MarkJavaCode bjc = new MarkJavaCode();
        PteString ps = new PteString();
        ps.appendLine("---------------------");
        ps.appendLine("username = [#username]");
        ps.appendLine("---------------------");
        ps.appendLine("#####################");
        ps.appendLine("#####################");
        ps.appendLine("---------------------");
        ps.appendLine("[@NotNull process:{]");
        ps.appendLine("System.out.print();");
        ps.appendLine("}]");
        ps.appendLine("username = [#username]");
        ps.appendLine("username = [#username]");
        ps.appendLine("username = [#username]");
        ps.appendLine("[@end]");
        ps.appendLine("---------------------");

        bjc.markJavaCode(ps);
    }

    /**
     * 标记Java代码位置
     *
     * @param sourceCode 源代码
     * @return 被标记后的代码
     */
    public PteString markJavaCode(PteString sourceCode) {
        notNull(sourceCode);
        return null;
    }

    @Override
    public String notNull(PteString pteStr) {
        int start = "[@NotNull ".length();
        while (pteStr.hasNext()) {
            String str = pteStr.next();
            int line = pteStr.getCurrentLineNumber();

            // 获取处理属性
            if (str.matches(notnull)) {
                StringBuilder property = new StringBuilder();
                char[] charArray = str.toCharArray();
                for (int i = start; i < charArray.length; i++) {

                    char value = charArray[i];
                    //
                    // 如果是终结符
                    //
                    if (value == ':') {
                        pnp.analysis(pteStr.toPteString(line),property.toString());
                    }

                    property.append(value);

                }
            }
            marked.appendLine(str);
        }
        return null;
    }

    @Override
    public void include(String str, int line) {
        int start = 0;                                  // 记录开始位置
        boolean canMark = false;                        // 是否可以标记,满足条件为brackets & well 不为空
        StringBuilder name = new StringBuilder();       // 记录名称
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char value = charArray[i];
            if (canMark) {
                if (value != ']') {
                    name.append(value);
                } else {
                    canMark = false;
                    if (positionList == null) {
                        positionList = new ArrayList<>();
                    }
                    positionList.add(new Position(name.toString(), start, i + 1, line));
                    start = 0;
                    name.delete(0, name.length());
                }
            } else {
                if (value == '[' && charArray[i + 1] == '#') {
                    start = i;
                    canMark = true;
                    i++;
                }
            }
        }
    }

}

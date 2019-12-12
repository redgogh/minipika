package com.poseidon.framework.sql.pte;

import com.poseidon.framework.tools.PteString;

import java.util.ArrayList;
import java.util.List;

/**
 * 将pte文件内容解析成Token
 * Create by 2BKeyboard on 2019/12/11 16:54
 */
public class ParserToken {

    private PteBuilderToken currentBuilderToken;

    /**
     * 最后解析出来的结果集
     */
    private List<PteBuilderToken> builderTokenSet = new ArrayList<>();

    public StringBuilder builderValue = new StringBuilder();

    /**
     * 记录当前解析状态
     * <p>
     * {@code NULL}
     * {@code BUILDER}
     * {@code MAPPER}
     */
    private PteStatus status = PteStatus.NULL;

    /**
     * Get Builder Token
     * @param pteStrings    源文件内容
     * @return  解析到的Token集合
     */
    public List<PteBuilderToken> getBuilderToken(List<PteString> pteStrings) {
        for (PteString pteString : pteStrings) {
            parserLine(pteString);
        }
        return builderTokenSet;
    }

    /**
     * 解析一行数据
     */
    private void parserLine(PteString ps) {
        while (ps.hasNext()) {
            String str = ps.next();      // 当前行的数据
            int line = ps.getHasNext();  // 当前在第几行
            char[] charArray = str.toCharArray();

            for (int i = 0; i < charArray.length; i++) {
                //
                // 当前状态为初始状态
                //
                if (status == PteStatus.NULL) {
                    if(charArray[i] == '{' || charArray[i] == '}'){
                        continue;
                    }
                    builderValue.append(charArray[i]);
                    if ("builder".equals(builderValue.toString())) {
                        status = PteStatus.BUILDER;
                        builderValueClear();
                        continue;
                    }
                    if ("mapper".equals(builderValue.toString())) {
                        status = PteStatus.MAPPER;
                        builderValueClear();
                        continue;
                    }
                    continue;
                }

                //
                // 如果当前状态为builder
                //
                if (status == PteStatus.BUILDER) {
                    // 判断是否为终结符
                    if (charArray[i] == '{' || charArray[i] == '}') {
                        PteBuilderToken pbt = new PteBuilderToken(builderValue.toString());
                        status = PteStatus.NULL;
                        currentBuilderToken = pbt;
                        builderTokenSet.add(pbt);
                        builderValueClear();
                    } else {
                        builderValue.append(charArray[i]);
                    }
                    continue;
                }

                //
                // 如果当前状态为mapper
                //
                if (status == PteStatus.MAPPER) {
                    // 判断是否为终结符
                    if (charArray[i] == '{' || charArray[i] == '}') {
                        PteMapperToken pmt = new PteMapperToken();
                        if (pmt.getTokenKey() == null) {
                            pmt.setTokenKey(builderValue.toString().trim());
                        }
                        if (pmt.getTokenValue() == null && pmt.getTokenKey() != null) {
                            PteString pteString = new PteString();
                            while (ps.hasNext()) {
                                String strValue = ps.next().trim();
                                if (!"}".equals(strValue.substring(strValue.length() - 1))) {
                                    pteString.appendLine(strValue);
                                    continue;
                                }else{
                                    break;
                                }
                            }
                            pmt.setTokenValue(pteString);
                        }
                        currentBuilderToken.builderToken(pmt);
                        builderValueClear();
                        status = PteStatus.NULL;
                    } else {
                        builderValue.append(charArray[i]);
                        continue;
                    }
                }

            }
        }
    }

    private void builderValueClear() {
        builderValue.delete(0, builderValue.length());
    }

}

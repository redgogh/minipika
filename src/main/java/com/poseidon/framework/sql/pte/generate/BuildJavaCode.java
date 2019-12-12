package com.poseidon.framework.sql.pte.generate;

import com.poseidon.framework.tools.PteString;

/**
 * 生成器，负责生成Java代码
 * Create by 2BKeyboard on 2019/12/12 14:45
 */
public class BuildJavaCode implements GenerateCriteria{

    private PteString javaCode;

    public BuildJavaCode(){
        javaCode = new PteString(50);
    }

    public PteString toJavaCode(PteString sourceCode){
        return null;
    }

    @Override
    public String _NotNull(PteString pteStr) {
        while(pteStr.hasNext()){

        }
        return null;
    }

    @Override
    public String _Include(PteString pteStr) {
        return null;
    }
}

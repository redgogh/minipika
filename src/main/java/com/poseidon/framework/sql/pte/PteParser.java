package com.poseidon.framework.sql.pte;

import com.poseidon.framework.tools.PteString;

import java.util.List;

/**
 * 语法解析器
 * Create by 2BKeyboard on 2019/12/11 18:12
 */
public class PteParser {

    public static void main(String[] args){
        PteParser pte = new PteParser();
        pte.parser();
    }

    public void parser(){
        PreProcess pre = new PreProcess();
        List<PteString> pteStrings = pre.readCode();
        for(PteString pstr : pteStrings){
           while(pstr.hasNext()){
               System.out.println(pstr.next());
           }
        }
    }

    public void traversalToken(List<PteBuilderToken> tokens){

    }


}

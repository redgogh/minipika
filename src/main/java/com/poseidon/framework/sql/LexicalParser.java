package com.poseidon.framework.sql;

import java.util.List;
import java.util.Map;

/**
 * 词法解析器
 * Create by 2BKeyboard on 2019/12/5 0:13
 */
public class LexicalParser {

    public List<Token> parse(String sql) {


        return null;
    }

    private void parseMysql(String sql)
    {
        StringBuilder lexical = new StringBuilder();
        List<String> keywords = new KeyWord().getMysql();
        char[] chars = sql.toCharArray();
        for (char single : chars) {
            switch (single) {
                case 'f':{
                    addChar(lexical,single,keywords);
                }
                case 'r':{

                }
                case 'o':{

                }
                case 'm': {

                }

                default: {
                }
            }
        }
    }

    private void addChar(StringBuilder builder,char single,List<String> keywords){
        builder.append(single);
        if(keywords.contains(keywords.toString())){

        }
    }



    private void clear(StringBuilder builder){
        builder.delete(0,builder.length());
    }

}

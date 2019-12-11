package com.poseidon.framework.sql.pte;

import java.util.List;

/**
 * 自动机
 * Create by 2BKeyboard on 2019/12/11 16:54
 */
public class Automaton {

    private char[] values;
    private PteStatus status;
    private StringBuilder builderValue;
    private List<PteBuilderToken> tokens;
    // 当前builder
    private PteBuilderToken currentToken;

    public Automaton(String str) {
        this.status = PteStatus.NULL;
        this.values = str.toCharArray();
        this.builderValue = new StringBuilder();
    }

    /**
     * 开始
     */
    public void start() {
        for (char value : values) {
            switch (value) {
                case '{': {
                    dispatch(value,true);
                    break;
                }
                case '}': {
                }
                default: {
                    dispatch(value);
                }
            }
        }
    }

    void dispatch(char value) {
        dispatch(value,false);
    }

    /**
     * 状态调度
     *
     * @param value
     */
    void dispatch(char value,boolean end) {
        switch (status) {
            case NULL: {
                if (builderValue.length() != 0) {
                    //
                    // 切换到builder状态
                    //
                    if ("builder".equals(builderValue.toString())) {
                        status = PteStatus.BUILDER;
                        clear();
                    }
                }
                break;
            }
            case BUILDER: {
                builder(value,end);
            }
            case QUERY: {
            }
            case INSERT: {
            }
            case UPDATE: {
            }
            case DELETE: {
            }
        }
    }

    /**
     * {@code BUILDER} status
     *
     * @param value
     */
    void builder(char value,boolean end) {
        builderValue.append(value);
        if(end) {
            PteBuilderToken token = new PteBuilderToken(builderValue.toString());
            currentToken = token;
        }
        clear();
    }

    /**
     * 添加token
     * @param key
     * @param value
     */
    void addToken(String key,String value){
        currentToken.builderToken(key,value);
    }

    void clear(){
        builderValue.delete(0,builderValue.length());
    }

}

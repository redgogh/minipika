package com.poseidon.framework.sql.pte;

import com.poseidon.framework.tools.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动机
 * Create by 2BKeyboard on 2019/12/11 16:54
 */
public class ParserToken {

    private List<StringBuilder> values;
    private PteStatus status;
    private StringBuilder builderValue;
    private List<PteBuilderToken> tokens;
    // 当前builder
    private PteBuilderToken currentToken;

    private String name;
    private String block;

    public ParserToken(List<StringBuilder> value) {
        this.tokens = new ArrayList<>(16);
        this.status = PteStatus.NULL;
        this.values = value;
        this.builderValue = new StringBuilder();
    }

    /**
     * 开始
     */
    public List<PteBuilderToken> getBuilderToken() {
        for (StringBuilder builder : this.values) {
            char[] charsValue = builder.toString().toCharArray();
            for (char value : charsValue) {
                switch (value) {
                    case '{':
                    case '}':{
                        dispatch(' ', true);
                        break;
                    }

                    default: {
                        dispatch(value);
                    }
                }
            }
        }
        return tokens;
    }

    void dispatch(char value) {
        dispatch(value, false);
    }

    /**
     * 状态调度
     *
     * @param value
     */
    void dispatch(char value, boolean end) {
        if (value == ' ' && status == PteStatus.NULL) return;
        builderValue.append(value);
        switch (status) {
            case NULL: {
                if (builderValue.length() != 0) {
                    //
                    // 切换到builder状态
                    //
                    if ("builder".equals(builderValue.toString())) {
                        status = PteStatus.BUILDER;
                        clear();
                        break;
                    }
                    //
                    // 切换到query状态
                    //
                    if ("mapper".equals(builderValue.toString())) {
                        status = PteStatus.MAPPER;
                        clear();
                        break;
                    }
                }
                break;
            }
            case BUILDER: {
                builder(end);
            }
            case MAPPER: {
                mapper(end);
            }
        }
    }

    /**
     * {@code BUILDER} status
     */
    void builder(boolean end) {
        if (end) {
            PteBuilderToken token = new PteBuilderToken(builderValue.toString().trim());
            tokens.add(token);
            currentToken = token;
            status = PteStatus.NULL;
            clear();
        }
    }

    /**
     * {@code MAPPER} status
     */
    void mapper(boolean end) {
        if (end) {
            if (StringUtils.isEmpty(name)) {
                name = builderValue.toString().trim();
                clear();
                return;
            }
            if (StringUtils.isEmpty(block)) {
                block = builderValue.toString().trim();
            }
            if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(block)) {
                addToken(name, block);
                name = "";
                block = "";
                clear();
                status = PteStatus.NULL;
            }
        }
    }

    /**
     * 添加token
     *
     * @param key
     * @param value
     */
    void addToken(String key, String value) {
        currentToken.builderToken(key, value);
    }

    void clear() {
        builderValue.delete(0, builderValue.length());
    }

}

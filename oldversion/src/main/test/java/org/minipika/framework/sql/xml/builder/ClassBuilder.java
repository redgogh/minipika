//package framework.sql.xml.builder;
//
///*
// * Copyright (C) 2020 Tiansheng All rights reserved.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//
//
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Copyright: Create by tiansheng on 2019/12/13 15:14
// */
//public class ClassBuilder extends framework.sql.xml.builder.StringNewline implements framework.sql.xml.builder.CodeBuilderFactory {
//
//    @Setter
//    @Getter
//    private String fullClassName;
//    @Setter
//    @Getter
//    private String name;
//
//    private int next = 0;
//
//    private List<framework.sql.xml.builder.MethodBuilder> methods;
//
//    public ClassBuilder(String name,String fullClassName){
//        this.name = name;
//        this.fullClassName = fullClassName.concat(name);
//    }
//
//    @Override
//    public ClassBuilder methodToClassBody() {
//        StringBuilder builder = new StringBuilder();
//        for (framework.sql.xml.builder.MethodBuilder method : methods) {
//            builder.append(method.toString(method.getArgs()));
//        }
//        insertLine(next,builder.toString());
//        return this;
//    }
//
//    @Override
//    public ClassBuilder createClassStatement() {
//        appendLine("public class ".concat(name).concat(" {\n"));
//        appendLine("}");
//        return null;
//    }
//
//    @Override
//    public ClassBuilder putMethod(framework.sql.xml.builder.MethodBuilder methodBuilder) {
//        if(methods == null){
//            methods = new ArrayList<>();
//        }
//        methods.add(methodBuilder);
//        return this;
//    }
//}
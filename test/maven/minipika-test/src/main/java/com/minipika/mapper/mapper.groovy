package com.minipika.mapper

import org.codehaus.groovy.transform.GroovyASTTransformationClass

import java.lang.annotation.Documented
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target([ElementType.FIELD, ElementType.TYPE])
@GroovyASTTransformationClass("com.minipika.mapper.GroovyAstTest")
@interface mapper {

}
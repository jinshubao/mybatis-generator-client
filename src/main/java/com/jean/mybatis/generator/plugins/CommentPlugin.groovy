package com.jean.mybatis.generator.plugins

import org.mybatis.generator.api.IntrospectedColumn
import org.mybatis.generator.api.IntrospectedTable
import org.mybatis.generator.api.Plugin
import org.mybatis.generator.api.PluginAdapter
import org.mybatis.generator.api.dom.java.Field
import org.mybatis.generator.api.dom.java.TopLevelClass

/**
 * Created by jinshubao on 2017/4/14.
 */
class CommentPlugin extends PluginAdapter {
    @Override
    boolean validate(List<String> list) {
        return true
    }

    @Override
    boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, Plugin.ModelClassType modelClassType) {
        field.getJavaDocLines().clear()
        field.addJavaDocLine("/**")
        field.addJavaDocLine(" * ${introspectedColumn.remarks}")
        field.addJavaDocLine(" */")
        println introspectedColumn.remarks
        return true
    }
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        println introspectedTable.remarks
        topLevelClass.addJavaDocLine("/**")
        topLevelClass.addJavaDocLine(" * ${introspectedTable.remarks}")
        topLevelClass.addJavaDocLine(" */")
        return true;
    }
}

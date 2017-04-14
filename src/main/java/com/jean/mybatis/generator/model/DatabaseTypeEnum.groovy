package com.jean.mybatis.generator.model

/**
 *
 * Created by jinshubao on 2017/4/9.
 */
enum DatabaseTypeEnum {
    MySQL("MySQL", "com.mysql.jdbc.Driver"),
    Oracle("Oracle", "oracle.jdbc.driver.OracleDriver")

    String name
    String value

    DatabaseTypeEnum(String name, String value) {
        this.value = value
        this.name = name
    }

    String getValue(String name) {
        for (def item : values()) {
            if (item.name == name) {
                return item.value
            }
        }
        return null
    }

    static DatabaseTypeEnum getDataBaseType(String value) {
        for (def item : values()) {
            if (item.value == value) {
                return item
            }
        }
        return null
    }

    @Override
    String toString() {
        return name
    }
}
package com.jean.mybatis.generator.model

/**
 *
 * Created by jinshubao on 2017/4/9.
 */
enum DatabaseType {
    MySql("MySql", "com.mysql.jdbc.Driver"),
    Oracle("Oracle", "oracle.jdbc.driver.OracleDriver")

    String name
    String driverClass

    DatabaseType(String name, String driverClass) {
        this.driverClass = driverClass
        this.name = name
    }

    /*String getDriverClass(String name) {
        for (def item : values()) {
            if (item.name == name) {
                return item.driverClass
            }
        }
        return null
    }

    static DatabaseTypeEnum getDataBaseType(String value) {
        for (def item : values()) {
            if (item.driverClass == value) {
                return item
            }
        }
        return null
    }
*/
    @Override
    String toString() {
        return name
    }
}

package com.jean.mybatis.generator.model

/**
 *
 * Created by jinshubao on 2017/4/9.
 */
class DatabaseItem extends DatabaseConfig {

    DatabaseItemTypeEnum type

    DatabaseItem() {}

    DatabaseItem(DatabaseConfig config, DatabaseItemTypeEnum type) {
        this.dataBaseType = config.dataBaseType
        this.encoding = config.encoding
        this.host = config.host
        this.port = config.port
        this.username = config.username
        this.password = config.password
        this.properties = config.properties
        this.savePassword = config.savePassword
        this.databaseName = config.databaseName
        this.type = type
    }

    @Override
    String toString() {
        switch (type) {
            case DatabaseItemTypeEnum.CONNECTION:
                return host + ":" + port
            case DatabaseItemTypeEnum.DATABASE:
                return databaseName
            case DatabaseItemTypeEnum.TABLE:
                return tableName
            default:
                return super.toString()
        }
    }
}

package com.jean.mybatis.generator.database

import com.jean.mybatis.generator.model.DatabaseConfig
import groovy.sql.Sql
import org.springframework.stereotype.Service

/**
 *
 * Created by jinshubao on 2017/4/9.
 */
@Service
class MySQLDatabaseMetadata implements DatabaseMetadataInterface {


    protected Sql getSql(DatabaseConfig config) {
        def url = getConnectionUrl(config)
        return Sql.newInstance(url, config.username, config.password, config.dataBaseType.value)
    }

    @Override
    List getDatabases(DatabaseConfig config) {
        def sql = getSql(config)
        def list = []
        sql.eachRow("SELECT SCHEMA_NAME FROM `information_schema`.`SCHEMATA`") {
            list << it.SCHEMA_NAME
        }
        return list
    }

    @Override
    boolean testConnection(DatabaseConfig config) {
        def sql = getSql(config)
        return sql != null
    }

    @Override
    List getTables(DatabaseConfig config) {
        def sql = getSql(config)
        def list = []
        sql.eachRow("SELECT TABLE_NAME FROM `INFORMATION_SCHEMA`.`TABLES` WHERE TABLE_SCHEMA=?", [config.databaseName]) {
            list << it.TABLE_NAME
        }
        return list
    }

    String getConnectionUrl(DatabaseConfig config) {
        def url = "jdbc:mysql://${config.host}:${config.port}"
        if (config.databaseName) {
            url += "/${config.databaseName}"
        }
        def properties = []
        if (config.encoding) {
            properties << "useUnicode=true"
            properties << "characterEncoding=${config.encoding}"
        }
        if (config.properties) {
            properties << config.properties
        }
        if (!properties.isEmpty()) {
            url += "?"
            url += properties.join("&")
        }
        return url
    }
}

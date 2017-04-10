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


    protected Sql getSql(DatabaseConfig config, String databaseName) {
        def url = getConnectionUrl(config, databaseName)
        return Sql.newInstance(url, config.username, config.password, config.databaseType.value)
    }

    @Override
    List getDatabases(DatabaseConfig config) {
        def sql = getSql(config, null)
        def list = []
        sql.eachRow("SELECT SCHEMA_NAME FROM `information_schema`.`SCHEMATA`") {
            list << it.SCHEMA_NAME
        }
        return list
    }

    @Override
    boolean testConnection(DatabaseConfig config) {
        def sql = getSql(config, null)
        return sql != null
    }

    @Override
    List getTables(DatabaseConfig config, String databaseName) {
        def sql = getSql(config, databaseName)
        def list = []
        sql.eachRow("SELECT TABLE_NAME FROM `INFORMATION_SCHEMA`.`TABLES` WHERE TABLE_SCHEMA=?", [databaseName]) {
            list << it.TABLE_NAME
        }
        return list
    }

    String getConnectionUrl(DatabaseConfig config, String databaseName) {
        def url = "jdbc:mysql://${config.host}:${config.port}"
        if (databaseName) {
            url += "/${databaseName}"
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

    @Override
    List getColumns(DatabaseConfig config, String databaseName, String tableName) {
        def sql = getSql(config, databaseName)
        def list = []
        sql.eachRow("SELECT COLUMN_NAME,DATA_TYPE,COLUMN_COMMENT FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?", [tableName]) {
            list << ["name": it.COLUMN_NAME, "type": it.DATA_TYPE, "comment": it.COLUMN_COMMENT]
        }
        return list
    }
}

package com.jean.mybatis.generator.support.database

import com.jean.mybatis.generator.model.DatabaseConfig
import com.jean.mybatis.generator.model.DatabaseType
import com.jean.mybatis.generator.model.TableInfo
import groovy.sql.Sql
import org.springframework.stereotype.Service

/**
 *
 * Created by jinshubao on 2017/4/9.
 */
@Service
class MySQLMetadataProvider implements IDataBaseMetadataProvider {

    protected DatabaseConfig config

    DatabaseConfig getConfig() {
        return config
    }

    void setConfig(DatabaseConfig config) {
        this.config = config
    }

    @Override
    DatabaseConfig getDatabaseConfig() {
        return config
    }

    protected Sql getSql(String databaseName) {
        def url = getConnectionUrl(databaseName)
        return Sql.newInstance(url, config.username, config.password, config.type.driverClass)
    }


    protected Sql getSql() {
        def url = getConnectionUrl()
        return Sql.newInstance(url, config.username, config.password, config.type.driverClass)
    }

    @Override
    List getDatabases() {
        def sql = getSql()
        def list = []
        sql.eachRow("SELECT SCHEMA_NAME FROM `information_schema`.`SCHEMATA`") {
            list << it.SCHEMA_NAME
        }
        return list
    }

    @Override
    boolean testConnection() {
        def sql = getSql()
        return sql != null
    }

    @Override
    List getTables(String databaseName) {
        def sql = getSql(databaseName)
        def list = []
        sql.eachRow("SELECT TABLE_NAME FROM `INFORMATION_SCHEMA`.`TABLES` WHERE TABLE_SCHEMA=?", [databaseName]) {
            list << it.TABLE_NAME
        }
        return list
    }

    String getConnectionUrl(String databaseName) {
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

    String getConnectionUrl() {
        getConnectionUrl(null)
    }

    @Override
    List<TableInfo> getColumns(String databaseName, String tableName) {
        def sql = getSql(databaseName)
        def list = []
        sql.eachRow("SELECT COLUMN_NAME,DATA_TYPE,COLUMN_COMMENT FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?", [tableName]) {
            list << new TableInfo(it.COLUMN_NAME, it.DATA_TYPE, it.COLUMN_COMMENT)
        }
        return list
    }

    @Override
    DatabaseType getType() {
        return DatabaseType.MySql
    }
}

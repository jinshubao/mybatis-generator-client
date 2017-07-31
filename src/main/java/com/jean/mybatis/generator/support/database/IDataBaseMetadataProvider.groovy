package com.jean.mybatis.generator.support.database

import com.jean.mybatis.generator.model.DatabaseConfig
import com.jean.mybatis.generator.model.DatabaseType
import com.jean.mybatis.generator.model.TableInfo

/**
 *
 * Created by jinshubao on 2017/4/9.
 */
interface IDataBaseMetadataProvider {

    void setConfig(DatabaseConfig config)

    DatabaseConfig getDatabaseConfig()

    boolean testConnection()

    List getDatabases()

    List getTables(String databaseName)

    String getConnectionUrl()

    String getConnectionUrl(String databaseName)

    List<TableInfo> getColumns(String databaseName, String tableName)

    DatabaseType getType()
}

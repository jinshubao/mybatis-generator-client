package com.jean.mybatis.generator.database

import com.jean.mybatis.generator.model.DatabaseConfig

/**
 *
 * Created by jinshubao on 2017/4/9.
 */
interface DatabaseMetadataInterface {

    List getDatabases(DatabaseConfig config)

    boolean testConnection(DatabaseConfig config)

    List getTables(DatabaseConfig config)

    String getConnectionUrl(DatabaseConfig config)
}

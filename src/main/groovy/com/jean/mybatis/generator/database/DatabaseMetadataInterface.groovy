package com.jean.mybatis.generator.database

import com.jean.mybatis.generator.model.AbstractTreeCellItem
import com.jean.mybatis.generator.model.DatabaseConfig
import com.jean.mybatis.generator.model.TableItem
import javafx.scene.control.TreeItem

/**
 *
 * Created by jinshubao on 2017/4/9.
 */
interface DatabaseMetadataInterface {

    List getDatabases(DatabaseConfig config)

    boolean testConnection(DatabaseConfig config)

    List getTables(DatabaseConfig config, String databaseName)

    String getConnectionUrl(DatabaseConfig config, String databaseName)

    List getColumns(DatabaseConfig config, String databaseName,String tableName)
}

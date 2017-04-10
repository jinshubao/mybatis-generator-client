package com.jean.mybatis.generator.factory

import com.jean.mybatis.generator.database.MySQLDatabaseMetadata
import com.jean.mybatis.generator.model.AbstractTreeCellItem
import com.jean.mybatis.generator.model.DatabaseItem
import javafx.scene.control.TreeCell
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 * Created by jinshubao on 2017/4/10.
 */
abstract class AbstractTreeCell<T> extends TreeCell<T> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass())

    protected MySQLDatabaseMetadata mySQLDatabaseMetadata

    AbstractTreeCell(MySQLDatabaseMetadata mySQLDatabaseMetadata) {
        this.mySQLDatabaseMetadata = mySQLDatabaseMetadata
    }
}

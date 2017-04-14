package com.jean.mybatis.generator.model
/**
 *
 * Created by jinshubao on 2017/4/9.
 */
class DatabaseTableItem extends AbstractTreeCellItem {

    String databaseName
    String tableName

    DatabaseTableItem(DatabaseConfig config) {
        super(config)
    }

    @Override
    String toString() {
        return tableName
    }
}

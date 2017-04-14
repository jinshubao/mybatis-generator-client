package com.jean.mybatis.generator.model
/**
 *
 * Created by jinshubao on 2017/4/9.
 */
class DatabaseItem extends AbstractTreeCellItem {

    String databaseName

    DatabaseItem(DatabaseConfig config) {
        super(config)
    }

    @Override
    String toString() {
        return databaseName
    }
}

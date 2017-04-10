package com.jean.mybatis.generator.model
/**
 *
 * Created by jinshubao on 2017/4/9.
 */
class TableItem extends DatabaseItem {

    String tableName

    TableItem(DatabaseConfig config) {
        super(config)
    }

    @Override
    String toString() {
        return tableName
    }
}

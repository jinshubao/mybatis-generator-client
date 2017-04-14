package com.jean.mybatis.generator.model
/**
 *
 * Created by jinshubao on 2017/4/9.
 */
class ConnectionItem extends AbstractTreeCellItem {

    ConnectionItem(DatabaseConfig config) {
        super(config)
    }

    @Override
    String toString() {
        return host + ":" + port
    }
}

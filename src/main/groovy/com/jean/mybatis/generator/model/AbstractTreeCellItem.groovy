package com.jean.mybatis.generator.model

import javafx.beans.property.SimpleBooleanProperty

/**
 * Created by jinshubao on 2017/4/10.
 */
abstract class AbstractTreeCellItem extends DatabaseConfig {

    def isOpen = new SimpleBooleanProperty()

    AbstractTreeCellItem(DatabaseConfig config) {
        this.databaseType = config.databaseType
        this.encoding = config.encoding
        this.host = config.host
        this.port = config.port
        this.username = config.username
        this.password = config.password
        this.properties = config.properties
        this.savePassword = config.savePassword
    }
}

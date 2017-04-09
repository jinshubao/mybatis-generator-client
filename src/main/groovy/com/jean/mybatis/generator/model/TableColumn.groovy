package com.jean.mybatis.generator.model

import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty

/**
 *
 * Created by jinshubao on 2017/4/9.
 */
class TableColumn {

    StringProperty columnName = new SimpleStringProperty()

    StringProperty jdbcType = new SimpleStringProperty()

    String getColumnName() {
        return columnName.get()
    }

    void setColumnName(String columnName) {
        this.columnName.set(columnName)
    }

    StringProperty columnNameProperty() {
        return columnName
    }

    String getJdbcType() {
        return jdbcType.get()
    }

    void setJdbcType(String jdbcType) {
        this.jdbcType.set(jdbcType)
    }

    StringProperty jdbcTypeProperty() {
        return jdbcType
    }


}

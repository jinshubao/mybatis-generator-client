package com.jean.mybatis.generator.model

import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty

/**
 *
 * Created by jinshubao on 2017/4/9.
 */
class TableInfo {

    TableInfo(String name, String type, String comment) {
        this.name.set(name)
        this.type.set(type)
        this.comment.set(comment)
    }
    StringProperty name = new SimpleStringProperty()

    StringProperty type = new SimpleStringProperty()

    StringProperty comment = new SimpleStringProperty()

    StringProperty nameProperty() {
        return name
    }

    String getName() {
        return name.get()
    }

    void setName(String name) {
        this.name.set(name)
    }

    StringProperty typeProperty() {
        return type
    }

    String getType() {
        return type.get()
    }

    void setType(String type) {
        this.type.set(type)
    }

    StringProperty commentProperty() {
        return comment
    }

    String getComment() {
        return comment.get()
    }

    void setComment(String comment) {
        this.comment.set(comment)
    }
}

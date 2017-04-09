package com.jean.mybatis.generator.dialog

import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane

/**
 *
 * Created by jinshubao on 2017/4/8.
 */
class DatabaseConnectionDialog<R> extends Dialog {

    DatabaseConnectionDialog(String title, String headerText, String style, Pane pane) {
        final DialogPane dialogPane = getDialogPane()
        setTitle(title)
        dialogPane.setHeaderText(headerText)
        if (style) {
            dialogPane.getStyleClass().add(style)
        }
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL)

        setResultConverter { type ->
            def values = [:]
            if (type) {
                if (type.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    pane.getChildren().each { node ->
                        if (node instanceof TextField) {
                            values.put(node.getId(), node.getText())
                        } else if (node instanceof ComboBox) {
                            values.put(node.id, node.getSelectionModel().getSelectedItem())
                        }
                    }
                }
            }
            values
        }
    }
}

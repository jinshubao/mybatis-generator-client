package com.jean.mybatis.generator.utils

import com.jean.mybatis.generator.model.DatabaseConfig
import com.jean.mybatis.generator.model.DatabaseTypeEnum
import com.jean.mybatis.generator.model.EncodingEnum
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.stage.Stage

/**
 * 弹框工具类
 * Created by jinshubao on 2017/4/8.
 */
class DialogUtil {

    static Optional<ButtonType> warning(String title, String headerText, String contentText) {
        alert(Alert.AlertType.WARNING, title, headerText, contentText, [ButtonType.OK] as ButtonType[])
    }

    static Optional<ButtonType> confirmation(String title, String headerText, String contentText) {
        alert(Alert.AlertType.CONFIRMATION, title, headerText, contentText, [ButtonType.CANCEL, ButtonType.OK] as ButtonType[])
    }

    static Optional<ButtonType> error(String title, String headerText, String contentText) {
        alert(Alert.AlertType.ERROR, title, headerText, contentText, [ButtonType.OK] as ButtonType[])
    }

    static Optional<ButtonType> information(String title, String headerText, String text) {
        alert(Alert.AlertType.INFORMATION, title, headerText, text, [ButtonType.CLOSE] as ButtonType[])
    }


    static Optional<ButtonType> alert(Alert.AlertType alertType, String title, String headerText, String contentText, ButtonType[] buttonTypes) {
        def alert = new Alert(alertType, contentText, buttonTypes)
        alert.setTitle(title)
        alert.setHeaderText(headerText)
        def stage = alert.getDialogPane().getScene().getWindow() as Stage
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/image/mybatis-logo.png")))
        return alert.showAndWait()
    }

    /**
     * 通用对话框
     * @param title
     * @param headerText
     * @param contentText
     * @param buttonTypes
     * @param okEventHandler
     * @param cancelEvenHandler
     */
    static Optional<ButtonType> dialog(String title, String headerText, String contentText, ButtonType[] buttonTypes) {
        def dialog = new Dialog<>()
        dialog.setTitle(title)
        dialog.setHeaderText(headerText)
        dialog.setContentText(contentText)
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypes)
        def stage = dialog.getDialogPane().getScene().getWindow() as Stage
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/image/mybatis-logo.png")))
        return dialog.showAndWait()
    }

    /**
     * 选择对话框
     * @param title
     * @param headerText
     * @param contentText
     * @param defValue
     * @param items
     * @param eventHandler
     * @return
     */
    static <T> Optional<T> choiceDialog(String title, String headerText, String contentText, T defValue, T[] items) {
        def dialog = new ChoiceDialog(defValue, items)
        dialog.setTitle(title)
        dialog.setHeaderText(headerText)
        dialog.setContentText(contentText)
        def stage = dialog.getDialogPane().getScene().getWindow() as Stage
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/image/mybatis-logo.png")))
        return dialog.showAndWait()
    }

    /**
     * 文本输入对话框
     * @param title
     * @param headerText
     * @param contentText
     * @param defValue
     * @param eventHandler
     * @return
     */
    static Optional<String> textInputDialog(String title, String headerText, String contentText, String defValue) {
        def dialog = new TextInputDialog(defValue)
        dialog.setTitle(title)
        dialog.setHeaderText(headerText)
        dialog.setContentText(contentText)
        def stage = dialog.getDialogPane().getScene().getWindow() as Stage
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/image/mybatis-logo.png")))
        return dialog.showAndWait()
    }

    /**
     * 数据库连接对话框
     * @param title
     * @param headerText
     * @param contentText
     * @param pane
     * @param eventHandler
     * @return
     */
    static Optional<DatabaseConfig> databaseConnectionDialog(String title, String headerText, Pane pane) {
        def dialog = new Dialog<>()
        dialog.setTitle(title)
        dialog.setHeaderText(headerText)
        dialog.dialogPane.setContent(pane)
        dialog.dialogPane.buttonTypes.addAll(ButtonType.OK, ButtonType.CANCEL)
        def stage = dialog.getDialogPane().getScene().getWindow() as Stage
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/image/mybatis-logo.png")))
        dialog.setResultConverter { type ->
            if (type.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                def values = [:]
                pane.getChildren().each { node ->
                    if (node instanceof TextField) {
                        values.put(node.getId(), node.getText())
                    } else if (node instanceof ComboBox) {
                        values.put(node.id, node.getSelectionModel().getSelectedItem())
                    } else if (node instanceof CheckBox) {
                        values.put(node.id, node.isSelected())
                    }
                }
                def config = new DatabaseConfig()
                config.databaseType = values?.dataBaseType as DatabaseTypeEnum
                config.host = values?.host as String
                config.port = values?.port as String
                config.username = values?.username as String
                config.password = values?.password as String
                config.encoding = values?.encoding as EncodingEnum
                config.properties = values?.properties as String
                config.savePassword = values?.savePassword as Boolean
                return config
            }
            return null
        }
        return dialog.showAndWait()
    }

    static void exception(String title, String headerText, Throwable ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR)
        alert.setTitle(title)
        alert.setHeaderText(headerText)
        alert.setContentText(ex.message)
        def stage = alert.getDialogPane().getScene().getWindow() as Stage
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/image/mybatis-logo.png")))
        StringWriter sw = new StringWriter()
        ex.printStackTrace(new PrintWriter(sw))
        String exceptionText = sw.toString()
        TextArea textArea = new TextArea(exceptionText)
        textArea.setEditable(false)
        textArea.setWrapText(true)
        textArea.setMaxWidth(Double.MAX_VALUE)
        textArea.setMaxHeight(Double.MAX_VALUE)
        alert.getDialogPane().setExpandableContent(textArea)
        alert.showAndWait()
    }
}
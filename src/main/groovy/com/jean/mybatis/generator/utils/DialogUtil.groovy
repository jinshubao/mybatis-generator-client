package com.jean.mybatis.generator.utils

import javafx.scene.control.*
import javafx.stage.WindowEvent

import java.util.function.Consumer

/**
 * 弹框工具类
 * Created by jinshubao on 2017/4/8.
 */
class DialogUtil {

    static void warning(String title, String headerText, String contentText) {
        warning(title, headerText, contentText) {}
    }

    static void warning(String title, String headerText, String contentText, Closure closure) {
        alert(Alert.AlertType.WARNING, title, headerText, contentText, [ButtonType.CANCEL, ButtonType.OK] as ButtonType[], closure)
    }

    static void confirmation(String title, String headerText, String contentText) {
        confirmation(title, headerText, contentText) {}
    }

    static void confirmation(String title, String headerText, String contentText, Closure closure) {
        alert(Alert.AlertType.CONFIRMATION, title, headerText, contentText, [ButtonType.CANCEL, ButtonType.OK] as ButtonType[], closure)
    }

    static void error(String title, String headerText, String contentText) {
        error(title, headerText, contentText) {}
    }

    static void error(String title, String headerText, String contentText, Closure closure) {
        alert(Alert.AlertType.ERROR, title, headerText, contentText, [ButtonType.OK] as ButtonType[], closure)
    }

    static void information(String title, String headerText, String contentText) {
        information(title, headerText, contentText) {}
    }

    static void information(String title, String headerText, String text, Closure closure) {
        alert(Alert.AlertType.INFORMATION, title, headerText, text, [ButtonType.CLOSE] as ButtonType[], closure)
    }

    static void alert(Alert.AlertType alertType, String title, String headerText, String contentText, ButtonType[] buttonTypes, Closure eventHandler) {
        def alert = new Alert(alertType, contentText, buttonTypes)
        alert.setTitle(title)
        alert.setHeaderText(headerText)
        def option = alert.showAndWait()
        if (eventHandler) {
            option.ifPresent(eventHandler as Consumer)
        }
    }

    /**
     * 通用对话框
     * @param title
     * @param headerText
     * @param contentText
     * @param buttonTypes
     * @param eventHandler
     */
    static void dialog(String title, String headerText, String contentText, ButtonType[] buttonTypes, Closure eventHandler) {
        def dialog = new Dialog<>()
        dialog.setTitle(title)
        dialog.setHeaderText(headerText)
        dialog.setContentText(contentText)
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypes)
        def option = dialog.showAndWait()
        if (eventHandler) {
            option.ifPresent(eventHandler as Consumer)
        }
    }

    /**
     * 选择对话框
     * @param title
     * @param headerText
     * @param contentText
     * @param defValue
     * @param items
     * @return
     */
    static <T> T choiceDialog(String title, String headerText, String contentText, T defValue, T... items) {
        choiceDialog(title, headerText, contentText, defValue, items) {}
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
    static <T> T choiceDialog(String title, String headerText, String contentText, T defValue, T[] items, Closure eventHandler) {
        def dialog = new ChoiceDialog(defValue, items)
        dialog.setTitle(title)
        dialog.setHeaderText(headerText)
        dialog.setContentText(contentText)
        def option = dialog.showAndWait()
        if (eventHandler) {
            option.ifPresent(eventHandler as Consumer)
        }
        dialog.getSelectedItem() as T
    }

    /**
     * 文本输入对话框
     * @param title
     * @param headerText
     * @param contentText
     * @param defValue
     * @return
     */
    static String textInputDialog(String title, String headerText, String contentText, String defValue) {
        textInputDialog(title, headerText, contentText, defValue){}
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
    static String textInputDialog(String title, String headerText, String contentText, String defValue, Closure eventHandler) {
        def dialog = new TextInputDialog(defValue)
        dialog.setTitle(title)
        dialog.setHeaderText(headerText)
        dialog.setContentText(contentText)
        def option = dialog.showAndWait()
        if (eventHandler) {
            option.ifPresent(eventHandler as Consumer)
        }
        dialog.getEditor().getText()
    }

    /**
     * 退出弹框
     * @param event
     */
    static void exit(WindowEvent event) {
        dialog("退出提示", "确认退出？", "", [ButtonType.OK, ButtonType.CANCEL] as ButtonType[]) {
            if (it != ButtonType.OK) {
                event.consume()
            }
        }
    }
}

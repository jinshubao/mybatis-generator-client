package com.jean.mybatis.generator.controller

import com.jean.mybatis.generator.MainApplication
import com.jean.mybatis.generator.utils.DialogUtil
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Toggle
import javafx.scene.control.ToggleButton
import javafx.scene.control.ToggleGroup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

/**
 *
 * Created by jinshubao on 2017/4/8.
 */
@Controller
class MainController implements Initializable {

    @FXML
    ToggleButton user
    @FXML
    ToggleGroup topbuttons
    @FXML
    ToggleButton table
    @FXML
    ToggleButton view
    @FXML
    ToggleButton function
    @FXML
    ToggleButton event
    @FXML
    ToggleButton select
    @FXML
    ToggleButton report
    @FXML
    ToggleButton backup
    @FXML
    ToggleButton plan
    @FXML
    ToggleButton model

    @Autowired
    MainApplication application

    @Override
    void initialize(URL location, ResourceBundle resources) {
        topbuttons.selectedToggleProperty().addListener({ ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue ->
            def headerText = """这是一段很长很长的信息"""
            def contentText = """这是一段很长很长的信息这是一段很长很长的信息这是一段很长很长的信息这是一段很长很长的信息这是一段很长很长的信息这是一段很长很长的信息这是一段很长很长的信息这是一段很长很长的信息这是一段很长很长的信息这是一段很长很长的信息这是一段很长很长的信息这是一段很长很长的信息这是一段很长很长的信息这是一段很长很长的信息这是一段很长很长的信息这是一段很长很长的信息这是一段很长很长的信息这是一段很长很长的信息这是一段很长很长的信息这是一段很长很长的信息这是一段很长很长的信息这是一段很长很长的信息这是一段很长很长的信息这是一段很长很长的信息"""
//            DialogUtil.warning(headerText, headerText, contentText)
            def value = DialogUtil.choiceDialog("请选择", "请选择一门语言", "选不选不由你", "java", "java", "C", "groovy")
            println value
            def value1 = DialogUtil.textInputDialog("请选择", "请选择一门语言", "选不选不由你", "C")
            println value1
        } as ChangeListener)
    }
}

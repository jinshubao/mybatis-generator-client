package com.jean.mybatis.generator.controller

import com.jean.mybatis.generator.model.DatabaseConfig
import com.jean.mybatis.generator.model.DatabaseType
import com.jean.mybatis.generator.model.EncodingEnum
import com.jean.mybatis.generator.utils.DialogUtil
import javafx.fxml.FXML
import javafx.scene.control.*
import org.springframework.stereotype.Controller

/**
 *
 * Created by jinshubao on 2017/4/8.
 */
@Controller
class ConnectionController extends BaseController {

    @FXML
    private ComboBox<DatabaseType> dataBaseType
    @FXML
    private TextField host
    @FXML
    private TextField port
    @FXML
    private TextField username
    @FXML
    private PasswordField password
    @FXML
    private ComboBox<EncodingEnum> encoding
    @FXML
    private TextField properties
    @FXML
    private Button testConnection
    @FXML
    private CheckBox savePassword

    @Override
    void initialize(URL location, ResourceBundle resources) {
        dataBaseType.items.addAll(DatabaseType.values())
        dataBaseType.selectionModel.selectFirst()
        encoding.items.addAll(EncodingEnum.values())
        encoding.selectionModel.selectFirst()
        properties.setText("serverTimezone=UTC&useUnicode=true&useSSL=false")
        testConnection.setOnAction {
            try {
                def config = new DatabaseConfig()
                config.type = dataBaseType.value
                config.host = host.text
                config.port = port.text
                config.username = username.text
                config.password = password.text
                config.encoding = encoding.value
                config.properties = properties.text
                def service = chooseMetadataService(config.type)
                service.setConfig(config)
                service.testConnection()
                DialogUtil.information("连接成功", null, "连接成功")
            } catch (Exception e) {
                DialogUtil.exceptionDialog("连接失败", "连接失败", e)
            }
        }
    }
}

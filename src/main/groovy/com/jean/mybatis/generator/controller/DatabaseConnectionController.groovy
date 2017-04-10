package com.jean.mybatis.generator.controller

import com.jean.mybatis.generator.database.MySQLDatabaseMetadata
import com.jean.mybatis.generator.model.DatabaseConfig
import com.jean.mybatis.generator.model.DatabaseTypeEnum
import com.jean.mybatis.generator.model.EncodingEnum
import com.jean.mybatis.generator.utils.DialogUtil
import javafx.fxml.FXML
import javafx.scene.control.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

/**
 *
 * Created by jinshubao on 2017/4/8.
 */
@Controller
class DatabaseConnectionController extends BaseController {

    @FXML
    ComboBox<DatabaseTypeEnum> dataBaseType
    @FXML
    TextField host
    @FXML
    TextField port
    @FXML
    TextField username
    @FXML
    PasswordField password
    @FXML
    ComboBox<EncodingEnum> encoding
    @FXML
    TextField properties
    @FXML
    Button testConnection
    @FXML
    CheckBox savePassword

    @Autowired
    MySQLDatabaseMetadata mySQLDatabaseMetadata

    @Override
    void initialize(URL location, ResourceBundle resources) {
        dataBaseType.items.addAll(DatabaseTypeEnum.values())
        dataBaseType.selectionModel.selectFirst()
        encoding.items.addAll(EncodingEnum.values())
        encoding.selectionModel.selectFirst()
        properties.setText("serverTimezone=UTC&useUnicode=true&useSSL=false")
        testConnection.setOnAction {
            try {
                def config = new DatabaseConfig()
                config.databaseType = dataBaseType.value
                config.host = host.getText()
                config.port = port.getText()
                config.username = username.getText()
                config.password = password.getText()
                config.encoding = encoding.value
                config.properties = properties.getText()
                if (mySQLDatabaseMetadata.testConnection(config)) {
                    DialogUtil.information("连接成功", "连接成功", "")
                } else {
                    DialogUtil.error("连接失败", "连接失败", "")
                }
            } catch (Exception e) {
                logger.error(e.message, e)
                DialogUtil.error("连接失败", "连接失败", e.message)
            }
        }
    }
}

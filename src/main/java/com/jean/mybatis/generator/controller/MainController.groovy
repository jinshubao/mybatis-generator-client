package com.jean.mybatis.generator.controller

import com.jean.mybatis.generator.constant.CommonConstant
import com.jean.mybatis.generator.database.IMetadataService
import com.jean.mybatis.generator.factory.TreeCellFactory
import com.jean.mybatis.generator.model.AbstractTreeCellItem
import com.jean.mybatis.generator.model.ConnectionItem
import com.jean.mybatis.generator.model.DatabaseConfig
import com.jean.mybatis.generator.model.StageTypeEnum
import com.jean.mybatis.generator.utils.DialogUtil
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.layout.Pane
import org.mybatis.generator.api.MyBatisGenerator
import org.mybatis.generator.config.Configuration
import org.mybatis.generator.internal.DefaultShellCallback
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

/**
 *
 * Created by jinshubao on 2017/4/8.
 */
@Controller
class MainController extends BaseController {
    @FXML
    ToggleGroup topbuttons
    @FXML
    ToggleButton user
    @FXML
    ToggleButton table
    @FXML
    ToggleButton view
    @FXML
    ToggleButton function
    @FXML
    ToggleButton model
    @FXML
    MenuItem newConnectionItem
    @FXML
    TreeView<AbstractTreeCellItem> databaseSchemaView
    @FXML
    ProgressIndicator progressIndicator
    @FXML
    Label message

    @Autowired
    IMetadataService metadataService

    @Autowired
    TreeCellFactory treeCellFactory

    @Override
    void initialize(URL location, ResourceBundle resources) {
        databaseSchemaView.setRoot(new TreeItem())
        databaseSchemaView.setShowRoot(false)
        message.setText(null)
        progressIndicator.setVisible(false)
        def newConnectionEventHandler = {
            DialogUtil.databaseConnectionDialog("新建数据库连接", null,
                    CommonConstant.SCENES.get(StageTypeEnum.CONNECTION.toString()) as Pane).ifPresent { DatabaseConfig config ->
                def connection = new TreeItem(new ConnectionItem(config))
                databaseSchemaView.getRoot().getChildren().add(connection)
            }
        }
        newConnectionItem.setOnAction(newConnectionEventHandler)
        databaseSchemaView.setCellFactory(treeCellFactory)

        user.setOnAction({
            List<String> warnings = new ArrayList<String>()
            boolean overwrite = true;
            Configuration config = new Configuration()

            //   ... fill out the config object as appropriate...

            DefaultShellCallback callback = new DefaultShellCallback(overwrite)
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings)
            myBatisGenerator.generate(null)
        })
    }

}

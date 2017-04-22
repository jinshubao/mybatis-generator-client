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
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.layout.Pane
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

/**
 *
 * Created by jinshubao on 2017/4/8.
 */
@Controller
class MainController extends BaseController {

    @FXML
    TreeView<AbstractTreeCellItem> databaseView

    @FXML
    MenuBar menuBar
    @FXML
    Menu fileMenu
    @FXML
    MenuItem newConnectionMenuItem
    @FXML
    MenuItem exitMenuItem

    @FXML
    Menu editMenu
    @FXML
    MenuItem newConfigurationMenuItem
    @FXML
    MenuItem manageConfigurationMenuItem
    @FXML
    Menu configurationListMenu
    @FXML
    ToggleGroup configurationGroup

    @FXML
    Menu helpMenu
    @FXML
    MenuItem aboutMenuItem

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
        databaseView.setRoot(new TreeItem())
        databaseView.setShowRoot(false)
        message.setText(null)
        progressIndicator.setVisible(false)
        newConnectionMenuItem.setOnAction {
            DialogUtil.newConnectionDialog("新建数据库连接", null,
                    CommonConstant.SCENES.get(StageTypeEnum.CONNECTION.toString()) as Pane).ifPresent { DatabaseConfig config ->
                def connection = new TreeItem(new ConnectionItem(config))
                databaseView.getRoot().getChildren().add(connection)
            }
        }
        newConfigurationMenuItem.setOnAction {
            DialogUtil.configurationDialog("新增配置文件", null,
                    CommonConstant.SCENES.get(StageTypeEnum.CONFIGURATION.toString()) as Node).ifPresent {
                logger.info(it.toString())
            }
        }

        databaseView.setCellFactory(treeCellFactory)

        /*List<String> warnings = new ArrayList<String>()
        boolean overwrite = true;
        Configuration config = new Configuration()

        //   ... fill out the config object as appropriate...

        DefaultShellCallback callback = new DefaultShellCallback(overwrite)
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings)
        myBatisGenerator.generate(null)*/
    }
}

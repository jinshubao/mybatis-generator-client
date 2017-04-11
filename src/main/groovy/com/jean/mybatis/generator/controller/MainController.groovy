package com.jean.mybatis.generator.controller

import com.jean.mybatis.generator.constant.CommonConstant
import com.jean.mybatis.generator.database.IMetadataService
import com.jean.mybatis.generator.factory.TreeCellFactory
import com.jean.mybatis.generator.model.*
import com.jean.mybatis.generator.utils.DialogUtil
import javafx.beans.value.ChangeListener
import javafx.fxml.FXML
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
    @FXML
    MenuItem newConnectionItem
    @FXML
    TreeView<AbstractTreeCellItem> databaseSchemaView
    @FXML
    TableView<TableInfo> databaseTableView
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
        databaseSchemaView.selectionModel.selectedItemProperty().addListener({ observableValue, oldValue, newValue ->
            if (newValue) {
                def value = newValue.value
                if (value instanceof DatabaseTableItem) {
                    databaseTableView.items.clear()
                    databaseTableView.items.addAll(metadataService.getColumns(value, value.databaseName, value.tableName))
                }
            }
        } as ChangeListener)
        databaseTableView.getColumns().get(0).setCellValueFactory {
            it.value.nameProperty()
        }
        databaseTableView.getColumns().get(1).setCellValueFactory {
            it.value.typeProperty()
        }
        databaseTableView.getColumns().get(2).setCellValueFactory {
            it.value.commentProperty()
        }
    }
}

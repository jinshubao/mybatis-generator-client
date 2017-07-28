package com.jean.mybatis.generator.controller

import com.jean.mybatis.generator.constant.CommonConstant
import com.jean.mybatis.generator.database.IMetadataService
import com.jean.mybatis.generator.factory.TreeCellFactory
import com.jean.mybatis.generator.model.AbstractTreeCellItem
import com.jean.mybatis.generator.model.ConnectionItem
import com.jean.mybatis.generator.model.DatabaseConfig
import com.jean.mybatis.generator.model.StageTypeEnum
import com.jean.mybatis.generator.utils.DialogUtil
import com.jean.mybatis.generator.utils.StringUtil
import javafx.beans.value.ChangeListener
import javafx.fxml.FXML
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.stage.DirectoryChooser
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

    @FXML
    public TextField projectPath
    @FXML
    public Button explorerProject
    @FXML
    public CheckBox camelCase
    @FXML
    public Button addModelMap
    @FXML
    public Button generate
    @FXML
    public TextField modelPackage
    @FXML
    public TextField mapperPath
    @FXML
    public TextField daoPackage
    @FXML
    public GridPane modelMap

    @Autowired
    Collection<IMetadataService> metadataService

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

        databaseView.setCellFactory(new TreeCellFactory(this, this.metadataService))
        explorerProject.setOnAction {
            def chooser = new DirectoryChooser()
            def window = (it.getSource() as Node).scene.window
            def file = chooser.showDialog(window)
            if (file) {
                projectPath.setText(file.getAbsolutePath())
            }
        }

        addModelMap.setOnAction {
            addModelMap(null, null)
        }
        camelCase.selectedProperty().addListener({ observable, oldValue, newValue ->
            if (newValue != null) {
                modelMap.getChildren().each {
                    if (it instanceof TextField && it.getId() == MODEL_NAME) {
                        def textField = it as TextField
                        def text = textField.text
                        if (text) {
                            text = StringUtil.toCamelCase(text, newValue)
                            textField.setText(text)
                        }
                    }
                }
            }
        } as ChangeListener)

        camelCase.setSelected(true)

        generate.disableProperty().bind(projectPath.textProperty().isEmpty()
                .or(modelPackage.textProperty().isEmpty())
                .or(daoPackage.textProperty().isEmpty())
                .or(mapperPath.textProperty().isEmpty()))

        generate.setOnAction({
            //todo 生成代码

        })
        /*List<String> warnings = new ArrayList<String>()
        boolean overwrite = true;
        Configuration config = new Configuration()

        //   ... fill out the config object as appropriate...

        DefaultShellCallback callback = new DefaultShellCallback(overwrite)
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings)
        myBatisGenerator.generate(null)*/
    }

    public static final String MODEL_NAME = "model_name"
    public static final String TABLE_NAME = "table_name"

    public void addModelMap(String tableName, String modelName) {
        def row = GridPane.getRowIndex(addModelMap)
        def table = new TextField(tableName)
        GridPane.setConstraints(table, 1, row)

        if (modelName && camelCase.selected) {
            modelName = StringUtil.toCamelCase(modelName, true)
        }

        def model = new TextField(modelName)
        model.setId(MODEL_NAME)
        table.setId(TABLE_NAME)
        GridPane.setConstraints(model, 2, row)
        def btn = new Button("定制")
        def del = new Button("删除")
        del.setOnAction({
            def thisBtn = it.getSource() as Button
            def parent = thisBtn.getParent()
            def rowIndex = GridPane.getRowIndex(parent)
            def grid = parent.getParent() as GridPane
            //删除所有当前行的Node
            def iterator = grid.getChildren().iterator()
            while (iterator.hasNext()) {
                def node = iterator.next()
                def nodeRowIndex = GridPane.getRowIndex(node)
                if (nodeRowIndex == rowIndex) {
                    iterator.remove()
                }
                if (nodeRowIndex > rowIndex) {
                    GridPane.setRowIndex(node, nodeRowIndex - 1)
                }
            }
            grid.getChildren().removeAll()
            grid.getRowConstraints().remove(rowIndex)
        })
        def hbox = new HBox(5D, btn, del)
        hbox.setAlignment(Pos.CENTER_LEFT)
        GridPane.setConstraints(hbox, 3, row)
        def constraints = new RowConstraints(10D, 30D, Region.USE_COMPUTED_SIZE)
        constraints.setVgrow(Priority.SOMETIMES)
        constraints.setPercentHeight(-1)
        modelMap.rowConstraints.add(1, constraints)
        //让按钮到下一行
        GridPane.setRowIndex(addModelMap, ++row)
        modelMap.getChildren().addAll(table, model, hbox)
    }
}

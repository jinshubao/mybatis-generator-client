package com.jean.mybatis.generator.controller

import com.jean.mybatis.generator.constant.CommonConstant
import com.jean.mybatis.generator.core.GeneratorService
import com.jean.mybatis.generator.factory.TreeCellFactory
import com.jean.mybatis.generator.model.AbstractTreeCellItem
import com.jean.mybatis.generator.model.ConnectionItem
import com.jean.mybatis.generator.model.DatabaseConfig
import com.jean.mybatis.generator.model.StageTypeEnum
import com.jean.mybatis.generator.utils.DialogUtil
import com.jean.mybatis.generator.utils.StringUtil
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.fxml.FXML
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.stage.DirectoryChooser
import org.mybatis.generator.config.*
import org.springframework.stereotype.Controller

/**
 *
 * Created by jinshubao on 2017/4/8.
 */
@Controller
class MainController extends BaseController {

    @FXML
    private TreeView<AbstractTreeCellItem> databaseView

    @FXML
    private MenuBar menuBar
    @FXML
    private Menu fileMenu
    @FXML
    private MenuItem newConnectionMenuItem
    @FXML
    private MenuItem exitMenuItem

    @FXML
    private Menu editMenu
    @FXML
    private MenuItem newConfigurationMenuItem
    @FXML
    private MenuItem manageConfigurationMenuItem
    @FXML
    private Menu configurationListMenu
    @FXML
    private ToggleGroup configurationGroup

    @FXML
    private Menu helpMenu
    @FXML
    private MenuItem aboutMenuItem

    @FXML
    private ProgressIndicator progressIndicator
    @FXML
    private Label message

    @FXML
    private TextField projectPath
    @FXML
    private Button explorerProject
    @FXML
    private CheckBox camelCase
    @FXML
    private Button addModelMap
    @FXML
    private Button generate
    @FXML
    private TextField modelPackage
    @FXML
    private TextField mapperPath
    @FXML
    private TextField daoPackage
    @FXML
    private GridPane modelMap

    @Override
    void initialize(URL location, ResourceBundle resources) {
        this.databaseView.setRoot(new TreeItem())
        this.databaseView.setShowRoot(false)
        this.message.setText(null)
        this.progressIndicator.setVisible(false)
        DatabaseConfig databaseConfig = null
        this.newConnectionMenuItem.setOnAction {
            DialogUtil.newConnectionDialog("新建数据库连接", null,
                    CommonConstant.SCENES.get(StageTypeEnum.CONNECTION.toString()) as Pane).ifPresent { DatabaseConfig config ->
                def service = chooseMetadataService(config.type)
                service.setConfig(config)
                databaseConfig = config
                def connection = new TreeItem(new ConnectionItem(config))
                this.databaseView.getRoot().getChildren().add(connection)
            }
        }
        this.newConfigurationMenuItem.setOnAction {
            DialogUtil.configurationDialog("新增配置文件", null,
                    CommonConstant.SCENES.get(StageTypeEnum.CONFIGURATION.toString()) as Node).ifPresent {
                logger.info(it.toString())
            }
        }

        this.databaseView.setCellFactory(new TreeCellFactory(this, this.metadataServices))
        this.explorerProject.setOnAction {
            def chooser = new DirectoryChooser()
            def window = (it.getSource() as Node).scene.window
            def file = chooser.showDialog(window)
            if (file) {
                this.projectPath.setText(file.getAbsolutePath())
            }
        }

        this.addModelMap.setOnAction {
            addModelMap(null, null)
        }
        this.camelCase.selectedProperty().addListener({ observable, oldValue, newValue ->
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

        this.camelCase.setSelected(true)


        def configuration = new Configuration()
        def context = new Context(ModelType.HIERARCHICAL)
        context.setId("context1")
        context.setTargetRuntime("MyBatis3Simple")
        context.addProperty("beginningDelimiter", "`")
        context.addProperty("endingDelimiter", "`")

        context.addPluginConfiguration(new PluginConfiguration(configurationType: "org.mybatis.generator.plugins.SerializablePlugin"))
        context.addPluginConfiguration(new PluginConfiguration(configurationType: "com.jean.mybatis.generator.plugins.CommentPlugin"))

        def commentGenerator = new CommentGeneratorConfiguration()
        commentGenerator.addProperty("suppressAllComments", "true")
        commentGenerator.addProperty("addRemarkComments", "true")
        commentGenerator.addProperty("suppressDate", "true")
        context.setCommentGeneratorConfiguration(commentGenerator)


        def javaTypeResolver = new JavaTypeResolverConfiguration()
        javaTypeResolver.addProperty("forceBigDecimals", "true")
        context.setJavaTypeResolverConfiguration(javaTypeResolver)

        def javaModelGenerator = new JavaModelGeneratorConfiguration()
        javaModelGenerator.setTargetPackage(this.modelPackage.text)
        this.modelPackage.textProperty().addListener({ ObservableValue<? extends String> observable, String oldValue, String newValue ->
            javaModelGenerator.setTargetPackage(newValue)
        } as ChangeListener)
        context.setJavaModelGeneratorConfiguration(javaModelGenerator)

        def javaClientGenerator = new JavaClientGeneratorConfiguration()
        javaClientGenerator.setConfigurationType("XMLMAPPER")//
        javaClientGenerator.addProperty("enableSubPackages", "true")//
        javaClientGenerator.setTargetPackage(this.daoPackage.text)
        this.daoPackage.textProperty().addListener({ ObservableValue<? extends String> observable, String oldValue, String newValue ->
            javaClientGenerator.setTargetPackage(newValue)
        } as ChangeListener)

        def sqlMapGenerator = new SqlMapGeneratorConfiguration()
        if (this.projectPath.text) {
            def text = this.projectPath.text
            if (!text.endsWith(File.separator)) {
                text = text + File.separator
            }
            def javaPath = text + "src" + File.separator + "main" + File.separator + "java" + File.separator
            def resourcePath = text + "src" + File.separator + "main" + File.separator + "resources" + File.separator
            javaModelGenerator.setTargetProject(javaPath)
            javaClientGenerator.setTargetProject(javaPath)
            sqlMapGenerator.setTargetProject(resourcePath)
        }

        this.projectPath.textProperty().addListener({ ObservableValue<? extends String> observable, String oldValue, String newValue ->
            def javaPath = null
            def resourcePath = null
            if (newValue) {
                if (!newValue.endsWith(File.separator)) {
                    newValue = newValue + File.separator
                }
                javaPath = newValue + "src" + File.separator + "main" + File.separator + "java" + File.separator
                resourcePath = newValue + "src" + File.separator + "main" + File.separator + "resources" + File.separator
            }
            javaModelGenerator.setTargetProject(javaPath)
            javaClientGenerator.setTargetProject(javaPath)
            sqlMapGenerator.setTargetProject(resourcePath)
        } as ChangeListener)
        context.setJavaClientGeneratorConfiguration(javaClientGenerator)

        sqlMapGenerator.setTargetPackage(this.mapperPath.text)
        this.mapperPath.textProperty().addListener({ ObservableValue<? extends String> observable, String oldValue, String newValue ->
            sqlMapGenerator.setTargetPackage(newValue)
        } as ChangeListener)
        context.setSqlMapGeneratorConfiguration(sqlMapGenerator)
        configuration.addContext(context)

        def generatorService = new GeneratorService(configuration, true)

        this.generate.disableProperty().bind(generatorService.runningProperty()
                .or(projectPath.textProperty().isEmpty())
                .or(modelPackage.textProperty().isEmpty())
                .or(daoPackage.textProperty().isEmpty())
                .or(mapperPath.textProperty().isEmpty()))
        this.message.textProperty().bind(generatorService.messageProperty())
        this.progressIndicator.visibleProperty().bind(generatorService.runningProperty())
        this.generate.setOnAction({
            //点击
            def jdbcConnection = new JDBCConnectionConfiguration()
            jdbcConnection.setDriverClass(databaseConfig.type.driverClass)
            def service = chooseMetadataService(databaseConfig.type)
            jdbcConnection.setConnectionURL(service.getConnectionUrl("message"))
            jdbcConnection.setUserId(databaseConfig.username)
            jdbcConnection.setPassword(databaseConfig.password)
            context.setJdbcConnectionConfiguration(jdbcConnection)

            context.tableConfigurations.clear()
            this.modelMap.getChildren().each {
                if (it.getId() == TABLE_NAME) {
                    def textFiled = it as TextField
                    def table = new TableConfiguration(context)
                    table.setTableName(textFiled.text)
                    table.setSelectByExampleStatementEnabled(false)
                    table.setDeleteByExampleStatementEnabled(false)
                    table.setUpdateByExampleStatementEnabled(false)
                    if (!this.camelCase.selected) {
                        //使用表字段名
                        table.addProperty("useActualColumnNames", "true")
                    }
                    table.setGeneratedKey(new GeneratedKey("id", "Mysql", false, "pre"))
                    context.addTableConfiguration(table)
                }
            }
            generatorService.restart()
        })

    }

    public static final String MODEL_NAME = "model_name"
    public static final String TABLE_NAME = "table_name"

    void addModelMap(String tableName, String modelName) {
        def row = GridPane.getRowIndex(this.addModelMap)
        def table = new TextField(tableName)
        GridPane.setConstraints(table, 1, row)

        if (modelName && this.camelCase.selected) {
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
        this.modelMap.rowConstraints.add(1, constraints)
        //让按钮到下一行
        GridPane.setRowIndex(addModelMap, ++row)
        this.modelMap.getChildren().addAll(table, model, hbox)
    }
}

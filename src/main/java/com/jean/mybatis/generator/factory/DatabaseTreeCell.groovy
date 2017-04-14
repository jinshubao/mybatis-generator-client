package com.jean.mybatis.generator.factory

import com.jean.mybatis.generator.database.IMetadataService
import com.jean.mybatis.generator.model.*
import com.jean.mybatis.generator.utils.DialogUtil
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import org.slf4j.Logger

/**
 *
 * Created by jinshubao on 2017/4/10.
 */
class DatabaseTreeCell extends TreeCell<AbstractTreeCellItem> {

    protected final Logger logger

    protected IMetadataService metadataService

    DatabaseTreeCell(IMetadataService metadataService, Logger logger) {
        this.metadataService = metadataService
        this.logger = logger
    }

    @Override
    protected void updateItem(AbstractTreeCellItem item, boolean empty) {
        super.updateItem(item, empty)
        if (empty) {
            setText(null)
            setGraphic(null)
        } else {
            def treeItem = getTreeItem()
            if (item.class == DatabaseTableItem.class) {
                updateTableItem(treeItem)
            } else if (item instanceof DatabaseItem) {
                updateDatabaseItem(treeItem)
            } else if (item instanceof ConnectionItem) {
                updateConnectionItem(treeItem)
            }
            setOnMouseClicked({
                if (it.button == MouseButton.PRIMARY && it.clickCount == 2 && !item.isOpen.value) {
                    if (item instanceof DatabaseItem) {
                        openDatabase(treeItem)
                    } else if (item instanceof ConnectionItem) {
                        openConnection(treeItem)
                    }
                }
            })
        }
    }

    private void updateConnectionItem(TreeItem treeItem) {
        def item = treeItem.value as ConnectionItem
        ContextMenu contextMenu = new ContextMenu()
        MenuItem open = new MenuItem("打开连接")
        MenuItem close = new MenuItem("关闭连接")
        MenuItem refresh = new MenuItem("刷新")
        MenuItem del = new MenuItem("删除")
        open.disableProperty().bind(item.isOpen)
        close.disableProperty().bind(item.isOpen.not())
        refresh.disableProperty().bind(item.isOpen.not())
        open.setOnAction {
            openConnection(treeItem)
        }
        close.setOnAction {
            treeItem.children.clear()
            item.isOpen.set(false)
        }
        refresh.setOnAction {
            openConnection(treeItem)
        }
        del.setOnAction {
            DialogUtil.confirmation("删除确认", null, "确认要删除连接${item}吗？").ifPresent {
                if (it == ButtonType.OK) {
                    treeItem.parent.children.remove(treeItem)
                    item.isOpen.set(false)
                }
            }
        }
        contextMenu.getItems().addAll(open, close, refresh, del)
        setContextMenu(contextMenu)
        setGraphic(new ImageView(new Image("/image/database_connect.png")))
        setText(item?.toString())
    }

    private void updateDatabaseItem(TreeItem treeItem) {
        def item = treeItem.value as DatabaseItem
        ContextMenu contextMenu = new ContextMenu()
        contextMenu.setPrefWidth(100)
        MenuItem open = new MenuItem("打开数据库")
        MenuItem close = new MenuItem("关闭数据库")
        MenuItem gen = new MenuItem("生成SQL映射文件")
        MenuItem refresh = new MenuItem("刷新")
        open.disableProperty().bind(item.isOpen)
        close.disableProperty().bind(item.isOpen.not())
        gen.disableProperty().bind(item.isOpen.not())
        refresh.disableProperty().bind(item.isOpen.not())
        open.setOnAction {
            openDatabase(treeItem)
        }
        close.setOnAction {
            treeItem.children.clear()
            item.isOpen.set(false)
        }
        refresh.setOnAction {
            treeItem.children.clear()
            openDatabase(treeItem)
        }
        contextMenu.getItems().addAll(open, close, gen, refresh)
        setContextMenu(contextMenu)
        setGraphic(new ImageView(new Image("/image/database.png")))
        setText(item?.toString())
    }

    private void updateTableItem(TreeItem treeItem) {
        def item = treeItem.value as DatabaseTableItem
        ContextMenu contextMenu = new ContextMenu()
        MenuItem gen = new MenuItem("生成SQL映射文件")
        gen.setOnAction { generate() }
        contextMenu.getItems().addAll(gen)
        setContextMenu(contextMenu)
        setGraphic(new ImageView(new Image("/image/database_table.png")))
        setText(item?.toString())
    }

    void openConnection(TreeItem treeItem) {
        try {
            def databases = metadataService.getDatabases(treeItem.value as DatabaseConfig)
            databases.each {
                def databaseItem = new DatabaseItem(item)
                databaseItem.databaseName = it as String
                treeItem.getChildren().add(new TreeItem(databaseItem))
            }
            treeItem.setExpanded(true)
            item.isOpen.set(true)
        } catch (Exception e) {
            DialogUtil.exception("打开失败", "打开连接失败", e)
        }
    }


    void openDatabase(TreeItem treeItem) {
        try {
            def temp = treeItem.value
            logger.info(temp.toString())
            def item = treeItem.value as DatabaseItem
            if (treeItem.children.isEmpty()) {
                metadataService.getTables(item, item.databaseName).each { table ->
                    def cfg = new DatabaseTableItem(item)
                    cfg.tableName = table as String
                    treeItem.children.add(new TreeItem(cfg))
                }
                treeItem.setExpanded(true)
                item.isOpen.set(true)
            }
        } catch (Exception e) {
            logger.error(e.message, e)
            DialogUtil.exception("打开失败", "打开数据库失败", e)
        }
    }

    void generate() {

    }
}

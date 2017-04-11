package com.jean.mybatis.generator.factory

import com.jean.mybatis.generator.database.IMetadataService
import com.jean.mybatis.generator.model.AbstractTreeCellItem
import com.jean.mybatis.generator.model.ConnectionItem
import com.jean.mybatis.generator.model.DatabaseItem
import com.jean.mybatis.generator.model.DatabaseTableItem
import com.jean.mybatis.generator.utils.DialogUtil
import javafx.scene.control.ButtonType
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.control.TreeItem
import javafx.scene.image.Image
import javafx.scene.image.ImageView

/**
 *
 * Created by jinshubao on 2017/4/10.
 */
class DatabaseTreeCell extends AbstractTreeCell<AbstractTreeCellItem> {

    DatabaseTreeCell(IMetadataService metadataService) {
        super(metadataService)
    }

    @Override
    protected void updateItem(AbstractTreeCellItem item, boolean empty) {
        super.updateItem(item, empty)
        if (empty) {
            setText(null)
            setGraphic(null)
        } else {
            if (item.class == DatabaseTableItem.class) {
                update(item as DatabaseTableItem)
            } else if (item instanceof DatabaseItem) {
                updateDatabaseItem(item as DatabaseItem)
            } else if (item instanceof ConnectionItem) {
                updateConnectionItem(item as ConnectionItem)
            }
        }
    }

    private void updateConnectionItem(ConnectionItem item) {
        logger.info(item.toString())
        ContextMenu contextMenu = new ContextMenu()
        MenuItem open = new MenuItem("打开连接")
        MenuItem close = new MenuItem("关闭连接")
        MenuItem refresh = new MenuItem("刷新")
        MenuItem del = new MenuItem("删除")
        open.disableProperty().bind(item.isOpen)
        close.disableProperty().bind(item.isOpen.not())
        refresh.disableProperty().bind(item.isOpen.not())
        open.setOnAction { openConnection() }
        close.setOnAction { closeConnection() }
        refresh.setOnAction { openConnection() }
        del.setOnAction { deleteConnection() }
        contextMenu.getItems().addAll(open, close, refresh, del)
        setContextMenu(contextMenu)
        setGraphic(new ImageView(new Image("/image/database_connect.png")))
        setText(item?.toString())
    }

    private void updateDatabaseItem(DatabaseItem item) {
        logger.info(item.toString())
        ContextMenu contextMenu = new ContextMenu()
        MenuItem open = new MenuItem("打开数据库")
        MenuItem close = new MenuItem("关闭数据库")
        MenuItem refresh = new MenuItem("刷新")
        open.disableProperty().bind(item.isOpen)
        close.disableProperty().bind(item.isOpen.not())
        refresh.disableProperty().bind(item.isOpen.not())
        open.setOnAction { openDatabase() }
        close.setOnAction { closeDatabase() }
        refresh.setOnAction {
            treeItem.children.clear()
            openDatabase()
        }
        contextMenu.getItems().addAll(open, close, refresh)
        setContextMenu(contextMenu)
        setGraphic(new ImageView(new Image("/image/database.png")))
        setText(item?.toString())
    }

    private void update(DatabaseTableItem item) {
        logger.info(item.toString())
        ContextMenu contextMenu = new ContextMenu()
        MenuItem gen = new MenuItem("生成java对象")
        gen.setOnAction { generate() }
        contextMenu.getItems().addAll(gen)
        setContextMenu(contextMenu)
        setGraphic(new ImageView(new Image("/image/database_table.png")))
        setText(item?.toString())
    }

    private void openConnection() {
        try {
            if (treeItem.children.isEmpty()) {
                def databases = metadataService.getDatabases(item)
                databases.each {
                    def databaseItem = new DatabaseItem(item)
                    databaseItem.databaseName = it as String
                    treeItem.getChildren().add(new TreeItem(databaseItem))
                }
                treeItem.setExpanded(true)
                item.isOpen.set(true)
            }
        } catch (Exception e) {
            DialogUtil.exception("打开失败", "打开连接失败", e)
        }

    }

    private void closeConnection() {
        def treeItem = getTreeItem()
        treeItem.children.clear()
        item.isOpen.set(false)
    }

    private void deleteConnection() {
        DialogUtil.confirmation("删除确认", null, "确认要删除连接${item}吗？").ifPresent {
            if (it == ButtonType.OK) {
                treeItem.parent.children.remove(treeItem)
                item.isOpen.set(false)
            }
        }
    }

    private void openDatabase() {
        try {
            def item = getItem() as DatabaseItem
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
            DialogUtil.exception("打开失败", "打开数据库失败", e)
        }
    }

    private void closeDatabase() {
        treeItem.children.clear()
        item.isOpen.set(false)
    }

    private void generate() {

    }
}

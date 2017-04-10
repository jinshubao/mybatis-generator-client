package com.jean.mybatis.generator.factory

import com.jean.mybatis.generator.database.MySQLDatabaseMetadata
import com.jean.mybatis.generator.model.AbstractTreeCellItem
import com.jean.mybatis.generator.model.ConnectionItem
import com.jean.mybatis.generator.model.DatabaseItem
import com.jean.mybatis.generator.model.TableItem
import com.jean.mybatis.generator.utils.DialogUtil
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

    DatabaseTreeCell(MySQLDatabaseMetadata mySQLDatabaseMetadata) {
        super(mySQLDatabaseMetadata)
    }

    @Override
    protected void updateItem(AbstractTreeCellItem item, boolean empty) {
        super.updateItem(item, empty)
        if (empty) {
            setText(null)
            setGraphic(null)
        } else {
            if (item instanceof TableItem) update(item as TableItem)
            else if (item instanceof DatabaseItem) update(item as DatabaseItem)
            else if (item instanceof ConnectionItem) update(item as ConnectionItem)
        }
    }

    private void update(ConnectionItem item) {
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
        this.setOnMouseClicked {
            it.clickCount == 2 && openConnection()
        }
    }

    private void update(DatabaseItem item) {
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
        this.setOnMouseClicked {
            it.clickCount == 2 && openDatabase()
        }
    }

    private void update(TableItem item) {
        ContextMenu contextMenu = new ContextMenu()
        MenuItem gen = new MenuItem("生成java对象")
        gen.setOnAction { generate() }
        contextMenu.getItems().addAll(gen)
        setContextMenu(contextMenu)
        setGraphic(new ImageView(new Image("/image/database_table.png")))
        setText(item?.toString())
    }

    private void openConnection() {
        if (treeItem.children.isEmpty()) {
            def databases = mySQLDatabaseMetadata.getDatabases(item)
            databases.each {
                def databaseItem = new DatabaseItem(item)
                databaseItem.databaseName = it as String
                treeItem.getChildren().add(new TreeItem(databaseItem))
            }
            treeItem.setExpanded(true)
            item.isOpen.set(true)
        }
    }

    private void closeConnection() {
        def treeItem = getTreeItem()
        treeItem.children.clear()
        item.isOpen.set(false)
    }

    private void deleteConnection() {
        DialogUtil.confirmation("删除确认", "确认要删除连接${item}吗？", null) {
            treeItem.parent.children.remove(treeItem)
            item.isOpen.set(false)
        }
    }

    private void openDatabase() {
        def item = getItem() as DatabaseItem
        if (treeItem.children.isEmpty()) {
            mySQLDatabaseMetadata.getTables(item, item.databaseName).each { table ->
                def cfg = new TableItem(item)
                cfg.tableName = table as String
                treeItem.children.add(new TreeItem(cfg))
            }
            treeItem.setExpanded(true)
            item.isOpen.set(true)
        }
    }

    private void closeDatabase() {
        treeItem.children.clear()
        item.isOpen.set(false)
    }

    private void generate() {

    }
}

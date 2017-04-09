package com.jean.mybatis.generator.factory

import com.jean.mybatis.generator.database.MySQLDatabaseMetadata
import com.jean.mybatis.generator.model.DatabaseItem
import com.jean.mybatis.generator.model.DatabaseItemTypeEnum
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.util.Callback
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by jinshubao on 2017/4/9.
 */

@Component
class TreeCellFactory implements Callback<TreeView<DatabaseItem>, TreeCell<DatabaseItem>> {

    @Autowired
    MySQLDatabaseMetadata mySQLDatabaseMetadata

    @Override
    TreeCell<DatabaseItem> call(TreeView<DatabaseItem> param) {

        return new TreeCell<DatabaseItem>() {
            @Override
            protected void updateItem(DatabaseItem item, boolean empty) {
                super.updateItem(item, empty)
                if (empty) {
                    setText(null)
                    setGraphic(null)
                } else {
                    ContextMenu contextMenu = new ContextMenu()
                    if (item.type == DatabaseItemTypeEnum.CONNECTION) {
                        MenuItem refresh = new MenuItem("刷新")
                        MenuItem del = new MenuItem("删除")
                        refresh.setOnAction {

                        }
                        contextMenu.getItems().addAll(refresh, del)
                        setGraphic(new ImageView(new Image("/image/database_connect.png")))
                    } else if (item.type == DatabaseItemTypeEnum.DATABASE) {
                        MenuItem open = new MenuItem("打开数据库")
                        MenuItem close = new MenuItem("关闭数据库")
                        close.setDisable(true)
                        open.setDisable(true)
                        close.setOnAction {
                            getTreeItem().children.clear()
                            open.setDisable(false)
                            close.setDisable(true)
                        }
                        open.setOnAction {
                            if (getTreeItem().children.isEmpty()) {
                                def config = getItem()
                                mySQLDatabaseMetadata.getTables(getItem()).each {
                                    def cfg = new DatabaseItem(config, DatabaseItemTypeEnum.TABLE)
                                    cfg.tableName = it as String
                                    getTreeItem().children.add(new TreeItem(cfg))
                                }
                                getTreeItem().setExpanded(true)
                                open.setDisable(true)
                                close.setDisable(false)
                            }
                        }
                        MenuItem refresh = new MenuItem("刷新")
                        refresh.setOnAction {
                            def config = getItem()
                            getTreeItem().children.clear()
                            mySQLDatabaseMetadata.getTables(getItem()).each {
                                def cfg = new DatabaseItem(config, DatabaseItemTypeEnum.TABLE)
                                cfg.tableName = it as String
                                getTreeItem().children.add(new TreeItem(cfg))
                            }
                            getTreeItem().setExpanded(true)
                        }
                        contextMenu.getItems().addAll(open, close, refresh)
                        setGraphic(new ImageView(new Image("/image/database.png")))
                    } else if (item.type == DatabaseItemTypeEnum.TABLE) {
                        MenuItem del = new MenuItem("生成java对象")
                        contextMenu.getItems().addAll(del)
                        setGraphic(new ImageView(new Image("/image/database_table.png")))
                    }
                    setContextMenu(contextMenu)
                    setText(item?.toString())
                }
            }
        }
    }
}

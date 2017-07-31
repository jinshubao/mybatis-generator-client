package com.jean.mybatis.generator.factory

import com.jean.mybatis.generator.controller.MainController
import com.jean.mybatis.generator.support.database.IDataBaseMetadataProvider
import com.jean.mybatis.generator.model.AbstractTreeCellItem
import javafx.scene.control.TreeCell
import javafx.scene.control.TreeView
import javafx.scene.input.MouseButton
import javafx.util.Callback

/**
 *
 * Created by jinshubao on 2017/4/9.
 */

class TreeCellFactory implements Callback<TreeView<AbstractTreeCellItem>, TreeCell<AbstractTreeCellItem>> {

    protected Collection<IDataBaseMetadataProvider> metadataServices
    protected MainController mainController

    TreeCellFactory(MainController mainController, Collection<IDataBaseMetadataProvider> metadataServices) {
        this.mainController = mainController
        this.metadataServices = metadataServices
    }

    @Override
    TreeCell<AbstractTreeCellItem> call(TreeView<AbstractTreeCellItem> param) {
        return new DatabaseTreeCell(mainController, metadataServices)
    }
}

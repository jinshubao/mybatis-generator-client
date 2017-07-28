package com.jean.mybatis.generator.factory

import com.jean.mybatis.generator.controller.MainController
import com.jean.mybatis.generator.database.IMetadataService
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


    protected Collection<IMetadataService> metadataServices
    protected MainController mainController

    TreeCellFactory(MainController mainController, Collection<IMetadataService> metadataServices) {
        this.mainController = mainController
        this.metadataServices = metadataServices
    }

    @Override
    TreeCell<AbstractTreeCellItem> call(TreeView<AbstractTreeCellItem> param) {
        def cell = new DatabaseTreeCell(mainController, metadataServices)
        cell.setOnMouseClicked() {
            if (it.button == MouseButton.PRIMARY && it.clickCount == 2) {
                //
            }
        }
        return cell
    }
}

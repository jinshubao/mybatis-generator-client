package com.jean.mybatis.generator.factory

import com.jean.mybatis.generator.database.IMetadataService
import com.jean.mybatis.generator.model.AbstractTreeCellItem
import javafx.scene.control.TreeCell
import javafx.scene.control.TreeView
import javafx.util.Callback
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 *
 * Created by jinshubao on 2017/4/9.
 */

@Component
class TreeCellFactory implements Callback<TreeView<AbstractTreeCellItem>, TreeCell<AbstractTreeCellItem>> {

    @Autowired
    IMetadataService metadataService

    @Override
    TreeCell<AbstractTreeCellItem> call(TreeView<AbstractTreeCellItem> param) {
        return new DatabaseTreeCell(metadataService)
    }
}

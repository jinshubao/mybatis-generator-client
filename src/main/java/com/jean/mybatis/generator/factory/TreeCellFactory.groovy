package com.jean.mybatis.generator.factory

import com.jean.mybatis.generator.database.IMetadataService
import com.jean.mybatis.generator.model.AbstractTreeCellItem
import javafx.scene.control.TreeCell
import javafx.scene.control.TreeView
import javafx.scene.input.MouseButton
import javafx.util.Callback
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 *
 * Created by jinshubao on 2017/4/9.
 */

@Component
class TreeCellFactory implements Callback<TreeView<AbstractTreeCellItem>, TreeCell<AbstractTreeCellItem>> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass())
    @Autowired
    IMetadataService metadataService

    @Override
    TreeCell<AbstractTreeCellItem> call(TreeView<AbstractTreeCellItem> param) {
        def cell = new DatabaseTreeCell(metadataService,logger)
        cell.setOnMouseClicked() {
            if (it.button == MouseButton.PRIMARY && it.clickCount == 2) {
                logger.info(it.source.toString())
            }
        }
        return cell
    }
}

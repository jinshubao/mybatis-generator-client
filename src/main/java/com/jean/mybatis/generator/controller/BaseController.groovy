package com.jean.mybatis.generator.controller

import com.jean.mybatis.generator.model.DatabaseType
import com.jean.mybatis.generator.support.database.IDataBaseMetadataProvider
import javafx.fxml.Initializable
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

/**
 *
 * Created by jinshubao on 2017/4/8.
 */
abstract class BaseController implements Initializable {

    protected Logger logger = LoggerFactory.getLogger(this.class)


    @Autowired
    protected Collection<IDataBaseMetadataProvider> metadataServices

    IDataBaseMetadataProvider chooseMetadataService(DatabaseType databaseType) {
        if (metadataServices) {
            for (IDataBaseMetadataProvider service : metadataServices) {
                if (service.getType() == databaseType) {
                    return service
                }
            }
        }
        throw new Exception("不支持的数据库类型")
    }

}

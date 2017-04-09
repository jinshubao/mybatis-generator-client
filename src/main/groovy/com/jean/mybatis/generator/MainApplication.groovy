package com.jean.mybatis.generator

import com.jean.mybatis.generator.constant.CommonConstant
import com.jean.mybatis.generator.scene.StageType
import com.jean.mybatis.generator.utils.DialogUtil
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.core.env.Environment

/**
 *
 * Created by jinshubao on 2017/4/8.
 */
@SpringBootApplication
class MainApplication extends AbstractJavaFxApplicationSupport {

    @Autowired
    private Environment environment

    @Override
    void start(Stage stage) throws Exception {
        super.start(stage)
        Parent root = loadFxml("/fxml/Scene.fxml")
        Parent databaseConnection = loadFxml("/fxml/DatabaseConnection.fxml")
        CommonConstant.SCENES.put(StageType.MAIN.toString(), root)
        CommonConstant.SCENES.put(StageType.DATABASE_CONNECTION.toString(), databaseConnection)
        Scene scene = new Scene(root)
        scene.getStylesheets().add("/styles/Styles.css")
        String name = environment.getProperty("spring.application.name")
        String version = environment.getProperty("spring.application.version")
        stage.setTitle("${name} ${version}")
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/mybatis-logo.png")))
        stage.setScene(scene)
        stage.show()
        stage.setOnCloseRequest {
            DialogUtil.exit(it)
        }
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    static void main(String[] args) {
        launchApp(MainApplication.class, args)
    }
}

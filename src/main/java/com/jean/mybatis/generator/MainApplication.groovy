package com.jean.mybatis.generator

import com.jean.mybatis.generator.constant.CommonConstant
import com.jean.mybatis.generator.model.StageTypeEnum
import com.jean.mybatis.generator.utils.DialogUtil
import javafx.animation.PathTransition
import javafx.animation.Timeline
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.ButtonType
import javafx.scene.image.Image
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.scene.shape.CubicCurveTo
import javafx.scene.shape.MoveTo
import javafx.scene.shape.Path
import javafx.scene.shape.Rectangle
import javafx.stage.Stage
import javafx.util.Duration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.core.env.Environment

/**
 *
 * Created by jinshubao on 2017/4/8.
 */
@SpringBootApplication
class MainApplication extends ApplicationSupport {

    @Autowired
    private Environment environment

    @Override
    void start(Stage stage) throws Exception {
        super.start(stage)
//        main(stage)
        pathTransition(stage)
    }

    void main(Stage stage){
        Parent root = loadFxml("/fxml/Scene.fxml")
        CommonConstant.SCENES.put(StageTypeEnum.MAIN.toString(), root)
        Parent databaseConnection = loadFxml("/fxml/Connection.fxml")
        CommonConstant.SCENES.put(StageTypeEnum.CONNECTION.toString(), databaseConnection)
        Parent configuration = loadFxml("/fxml/Configuration.fxml")
        CommonConstant.SCENES.put(StageTypeEnum.CONFIGURATION.toString(), configuration)
        Scene scene = new Scene(root)
        scene.getStylesheets().add("/styles/Styles.css")
        String name = environment.getProperty("spring.application.name")
        String version = environment.getProperty("spring.application.version")
        stage.setTitle("${name} ${version}")
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/mybatis-logo.png")))
        stage.setScene(scene)
        stage.show()
        stage.setOnCloseRequest { event ->
            DialogUtil.confirmation("退出提示", null, "确认退出？").ifPresent() {
                if (it != ButtonType.OK) {
                    event.consume()
                }
            }
        }

    }

    void pathTransition(Stage arg0) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 400, 400);

        //创建一个矩形
        final Rectangle rect = new Rectangle(0, 0, 40, 40);
        rect.setArcHeight(10);
        rect.setArcWidth(10);
        rect.setFill(Color.RED);
        //将矩形作为一个Node方到Parent中
        root.getChildren().add(rect);

        //创建路径
        Path path = new Path();
        path.getElements().add(new MoveTo(20, 20));
//        path.getElements().add(new CubicCurveTo(380, 0, 380, 120, 200, 120));
        path.getElements().add(new CubicCurveTo(30, 30, 0, 500, 500, 500));
        //创建路径转变
        PathTransition pt = new PathTransition();
        pt.setDuration(Duration.millis(4000));//设置持续时间4秒
        pt.setPath(path);//设置路径
        pt.setNode(rect);//设置物体
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        //设置周期性，无线循环
        pt.setCycleCount(Timeline.INDEFINITE);
        pt.setAutoReverse(true);//自动往复
        pt.play();//启动动画

        arg0.setScene(scene);
        arg0.show();
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

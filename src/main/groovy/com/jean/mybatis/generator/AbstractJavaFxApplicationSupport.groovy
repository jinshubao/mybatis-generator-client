package com.jean.mybatis.generator

import javafx.application.Application
import javafx.application.Preloader
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.stage.Stage
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext

/**
 * javafx-springboot
 * Created by jinshubao on 2017/4/8.
 */
abstract class AbstractJavaFxApplicationSupport extends Application {

    protected static String[] args
    protected ConfigurableApplicationContext applicationContext

    @Override
    void init() throws Exception {
        notifyPreloader(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_INIT, this))
        applicationContext = SpringApplication.run(getClass(), args)
        applicationContext.getAutowireCapableBeanFactory().autowireBean(this)
    }

    @Override
    void start(Stage primaryStage) throws Exception {
        notifyPreloader(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_START, this))
    }

    @Override
    void stop() throws Exception {
        applicationContext.close()
    }

    protected static void launchApp(Class<? extends AbstractJavaFxApplicationSupport> applicationClass, String[] args) {
        AbstractJavaFxApplicationSupport.args = args
        launch(applicationClass, args)
    }

    Parent loadFxml(String name) {
        FXMLLoader loader = new FXMLLoader()
        loader.setControllerFactory {
            applicationContext.getBean(it)
        }
        loader.load(getClass().getResourceAsStream(name))
    }
}

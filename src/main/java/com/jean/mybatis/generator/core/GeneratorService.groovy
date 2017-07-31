package com.jean.mybatis.generator.core

import javafx.concurrent.Service
import javafx.concurrent.Task
import org.mybatis.generator.api.MyBatisGenerator
import org.mybatis.generator.api.ProgressCallback
import org.mybatis.generator.config.Configuration
import org.mybatis.generator.internal.DefaultShellCallback

class GeneratorService extends Service<List<String>> {
    Configuration configuration
    boolean overwrite

    GeneratorService(Configuration configuration, boolean overwrite) {
        this.configuration = configuration
        this.overwrite = overwrite
    }

    @Override
    protected Task<List<String>> createTask() {
        return new GeneratorTask<List<String>>() {
            @Override
            protected List<String> call() throws Exception {
                List<String> warnings = new ArrayList<String>()
                def myBatisGenerator = new MyBatisGenerator(configuration, new DefaultShellCallback(overwrite), warnings)
                myBatisGenerator.generate(this)
                return warnings
            }
        }

    }

    @Override
    void restart() {
        if (!isRunning()) {
            super.restart()
        }
    }

    @Override
    protected void succeeded() {
        super.succeeded()
    }

    static abstract class GeneratorTask<T> extends Task<T> implements ProgressCallback {

        private int saveTaskIndex = 1
        private int introspectionTaskIndex = 1
        private int generationTaskIndex = 1

        @Override
        void introspectionStarted(int totalTasks) {
            updateMessage("正在保存文件${introspectionTaskIndex++}/${totalTasks}")
        }

        @Override
        void generationStarted(int totalTasks) {
            updateMessage("正在保存文件${generationTaskIndex++}/${totalTasks}")
        }

        @Override
        void saveStarted(int totalTasks) {
            updateMessage("正在保存文件${saveTaskIndex++}/${totalTasks}")
        }

        @Override
        void startTask(String taskName) {
            updateMessage("正在执行${taskName}...")
        }

        @Override
        void checkCancel() throws InterruptedException {
            updateMessage("取消生成")
        }

        @Override
        protected void succeeded() {
            super.succeeded()
            updateMessage("生成成功")
        }

        @Override
        void done() {
            super.done()
        }

        @Override
        protected void failed() {
            super.failed()
            def throwable = getException()
            updateMessage("生成失败")
            throwable.printStackTrace()
        }
    }
}

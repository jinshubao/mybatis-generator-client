package com.jean.mybatis.generator.core

import javafx.concurrent.Service
import javafx.concurrent.Task
import org.mybatis.generator.api.MyBatisGenerator
import org.mybatis.generator.api.ProgressCallback
import org.mybatis.generator.api.ShellCallback
import org.mybatis.generator.config.Configuration
import org.mybatis.generator.internal.DefaultShellCallback

class GeneratorService extends Service<List<String>> {
    Configuration configuration
    ShellCallback callback

    GeneratorService(Configuration configuration) {
        this(configuration, new DefaultShellCallback(true))
    }

    GeneratorService(Configuration configuration, ShellCallback callback) {
        this.configuration = configuration
        this.callback = callback
    }

    @Override
    protected Task<List<String>> createTask() {
        return new GeneratorTask<List<String>>(configuration, callback)

    }

    @Override
    synchronized void restart() {
        if (!isRunning()) {
            super.restart()
        }
    }

    @Override
    protected void succeeded() {
        super.succeeded()
    }

    private static class GeneratorTask<T> extends Task<T> implements ProgressCallback {

        private Configuration configuration
        private ShellCallback shellCallback
        private int saveTaskIndex = 1
        private int introspectionTaskIndex = 1
        private int generationTaskIndex = 1

        GeneratorTask(Configuration configuration, ShellCallback shellCallback) {
            this.configuration = configuration
            this.shellCallback = shellCallback
        }

        @Override
        protected List<String> call() throws Exception {
            List<String> warnings = new ArrayList<String>()
            def myBatisGenerator = new MyBatisGenerator(configuration, shellCallback, warnings)
            myBatisGenerator.generate(this)
            return warnings
        }

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

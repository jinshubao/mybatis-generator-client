package com.jean.mybatis.generator.model

/**
 *
 * Created by jinshubao on 2017/4/9.
 */
enum EncodingEnum {

    MySQL("UTF-8", "UTF-8"),
    Oracle("GBK", "GBK")

    String name
    String value

    EncodingEnum(String name, String value) {
        this.value = value
        this.name = name
    }

    @Override
    String toString() {
        return name
    }
}

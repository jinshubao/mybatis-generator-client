package com.jean.mybatis.generator.model

/**
 *
 * Created by jinshubao on 2017/4/9.
 */
enum EncodingEnum {

    UTF8("UTF-8", "utf8"),
    GBK("GBK", "GBK")

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

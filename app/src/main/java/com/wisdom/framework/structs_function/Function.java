package com.wisdom.framework.structs_function;

/**
 * Created by chejiangwei on 2017/12/13.
 * Describe: 对接口的抽象
 * 接口一定有以下属性：名字（name）,返回值（result）,参数（param）,方法体（function）
 */

public abstract class Function {

    public String name;

    public Function(String name) {
        this.name = name;
    }

}

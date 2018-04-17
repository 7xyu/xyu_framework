package com.wisdom.framework.structs_function;

/**
 * Created by chejiangwei on 2017/12/13.
 * Describe:仅有参数的接口
 */

public abstract class FunctionOnlyParam<Param> extends Function {
    public FunctionOnlyParam(String name) {
        super(name);
    }

    public abstract void function(Param param);

}

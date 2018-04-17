package com.wisdom.framework.structs_function;

/**
 * Created by chejiangwei on 2017/12/13
 * Describe:既有参数亦有返回的接口
 */

public abstract class FunctionWithParamAndResult<Result, Param> extends Function {
    public FunctionWithParamAndResult(String name) {
        super(name);
    }

    public abstract Result function(Param param);

}

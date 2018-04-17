package com.wisdom.framework.structs_function;

/**
 * Created by chejiangwei on 2017/12/13.
 * Describe:仅有返回的接口
 */

public abstract class FunctionOnlyResult<Result> extends Function {

    public FunctionOnlyResult(String name) {
        super(name);
    }

    public abstract Result function();

}

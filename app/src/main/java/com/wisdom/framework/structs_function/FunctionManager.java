package com.wisdom.framework.structs_function;

import android.support.annotation.NonNull;

import java.util.HashMap;

/**
 * Created by chejiangwei on 2017/12/13.
 * Describe:接口管理类
 */

public class FunctionManager {

    private HashMap<String, FunctionNoParamNoResult> functionNoParamNoResultHashMap;
    private HashMap<String, FunctionOnlyParam> functionOnlyParamHashMap;
    private HashMap<String, FunctionOnlyResult> functionOnlyResultHashMap;
    private HashMap<String, FunctionWithParamAndResult> functionWithParamAndResultHashMap;

    private FunctionManager() {
        functionNoParamNoResultHashMap = new HashMap<>();
        functionOnlyParamHashMap = new HashMap<>();
        functionOnlyResultHashMap = new HashMap<>();
        functionWithParamAndResultHashMap = new HashMap<>();
    }

    private static class SingleHolder {
        private static FunctionManager INSTANCE = new FunctionManager();
    }

    public static FunctionManager getInstance() {
        return SingleHolder.INSTANCE;
    }

  /*  *//**
     *//*
    public void clear() {
        functionNoParamNoResultHashMap.clear();
        functionOnlyParamHashMap.clear();
        functionOnlyResultHashMap.clear();
        functionWithParamAndResultHashMap.clear();
    }*/

    /**
     * 添加接口
     * 接口名要保证唯一性，可以参考以类名+_自定义方法名构成
     *
     * @param function
     */
    public FunctionManager addFunction(FunctionNoParamNoResult function) {
        functionNoParamNoResultHashMap.put(function.name, function);
        return this;
    }

    /**
     * 添加接口
     * 接口名要保证唯一性，可以参考以类名+_自定义方法名构成
     *
     * @param function
     */
    public FunctionManager addFunction(FunctionOnlyParam function) {
        functionOnlyParamHashMap.put(function.name, function);
        return this;
    }

    /**
     * 添加接口
     * 接口名要保证唯一性，可以参考以类名+_自定义方法名构成
     *
     * @param function
     */
    public FunctionManager addFunction(FunctionOnlyResult function) {
        functionOnlyResultHashMap.put(function.name, function);
        return this;
    }

    /**
     * 添加接口
     * 接口名要保证唯一性，可以参考以类名+_自定义方法名构成
     *
     * @param function
     */
    public FunctionManager addFunction(FunctionWithParamAndResult function) {
        functionWithParamAndResultHashMap.put(function.name, function);
        return this;
    }


    /**
     * 实现无参无返回接口
     *
     * @param name
     */
    public void invoke(@NonNull String name) throws FunctionException {
        FunctionNoParamNoResult function = functionNoParamNoResultHashMap.get(name);
        if (function == null) throw new FunctionException("糟糕，找不到该无参无返回的接口——" + name);
        function.function();
    }


    /**
     * 实现仅有参数的接口
     *
     * @param name
     */
    public <Param> void invoke(@NonNull String name, Param param) throws FunctionException {
        FunctionOnlyParam<Param> function = functionOnlyParamHashMap.get(name);
        if (function == null) throw new FunctionException("糟糕，找不到该仅有参数的的接口——" + name);
        function.function(param);
    }

    /**
     * 实现仅有结果的接口
     *
     * @param name
     */
    public <Result> Result invoke(@NonNull String name, Class<Result> resultType) throws FunctionException {
        FunctionOnlyResult function = functionOnlyResultHashMap.get(name);
        if (function == null) throw new FunctionException("糟糕，找不到该仅有结果的接口——" + name);
        return resultType.cast(function.function());
    }


    /**
     * 实现既有参数亦有结果的接口
     *
     * @param name
     */
    public <Result, Param> Result invoke(@NonNull String name, Param param, Class<Result> resultType) throws FunctionException {
        FunctionWithParamAndResult<Result, Param> function = functionWithParamAndResultHashMap.get(name);
        if (function == null) throw new FunctionException("糟糕，找不到该既有参数亦有结果的接口——" + name);
        return resultType.cast(function.function(param));
    }


}

package com.lsc.freemarker.utils;


import java.util.function.Function;

/**
 * @author: yuchao
 * @description:三目运算
 * @date: 2022-07-18 11:26:01
 */
public final class OptionalUtil {
    /**
     * 三目
     *
     * @param bol
     * @param trueValue
     * @param falseValue
     * @param <T>
     * @return
     */
    public static <T> T ofBoolean(Boolean bol, T trueValue, T falseValue) {
        return bol != null && bol ? trueValue : falseValue;
    }

    /**
     * 三目,传递
     *
     * @param bol
     * @param t
     * @param trueApply
     * @param falseApply
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> R ofBoolean(Boolean bol, T t, Function<T, R> trueApply, Function<T, R> falseApply) {
        return bol != null && bol ? trueApply.apply(t) : falseApply.apply(t);
    }

}

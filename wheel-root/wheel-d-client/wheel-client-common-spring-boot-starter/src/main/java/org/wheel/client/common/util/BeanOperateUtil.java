package org.wheel.client.common.util;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import lombok.Data;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/8/19 15:42
 */
public class BeanOperateUtil {

    public static <T, F> F invoke(T bean, SFunction<T, ?> func, Object... params) {
        SerializedLambda serializedLambda = getSerializedLambda(func);
        return ReflectUtil.invoke(bean, serializedLambda.getImplMethodName(), params);
    }

    public static <T> String getMethodName(SFunction<T, ?> func) {
        SerializedLambda serializedLambda = getSerializedLambda(func);
        return serializedLambda.getImplMethodName();
    }

    public static <T> SerializedLambda getSerializedLambda(SFunction<T, ?> fn) {
        // 从function取出序列化方法
        Method writeReplaceMethod;
        try {
            writeReplaceMethod = fn.getClass().getDeclaredMethod("writeReplace");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        // 从序列化方法取出序列化的lambda信息
        boolean isAccessible = writeReplaceMethod.isAccessible();
        writeReplaceMethod.setAccessible(true);
        SerializedLambda serializedLambda;
        try {
            serializedLambda = (SerializedLambda) writeReplaceMethod.invoke(fn);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        writeReplaceMethod.setAccessible(isAccessible);
        return serializedLambda;
    }


    @Data
    static class Aaaaa {
        private String aaa;

        private String ce;
    }

    public static void main(String[] args) {
        Aaaaa aa1 = new Aaaaa();
        aa1.setAaa("aa");
        aa1.setAaa("222");
        Object beanValue = invoke(aa1, Aaaaa::getAaa);
        System.out.println(beanValue);
    }
}

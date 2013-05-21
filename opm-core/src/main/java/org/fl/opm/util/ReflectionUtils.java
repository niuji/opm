package org.fl.opm.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-4
 * Time: 上午11:02
 * To change this template use File | Settings | File Templates.
 */
public class ReflectionUtils {
    /**
     * 查找拥有指定注解的field
     *
     * @param clz
     * @param annotations
     * @return
     */
    public static List<Field> findAllFields(Class<?> clz, Class<? extends Annotation>... annotations) {
        if (clz == null || ArrayUtils.isEmpty(annotations)) {
            return null;
        }
        List<Field> list = new ArrayList();
        do {
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                for (Class annotation : annotations) {
                    if (field.getAnnotation(annotation) != null) {
                        list.add(field);
                        break;
                    }
                }
            }
        } while ((clz = clz.getSuperclass()) != Object.class);
        return list;
    }

    /**
     *
     * @param clz
     * @param annotationClass
     * @return
     */
    public static Class<?> findAnnotatedClass(Class<?> clz, Class<? extends Annotation> annotationClass){
        if(annotationClass == null){
            return null;
        }
        do {
            Annotation annotation = clz.getAnnotation(annotationClass);
            if(annotation != null){
                return clz;
            }
        } while ((clz = clz.getSuperclass()) != Object.class);
        return null;
    }

    public static Object getFieldValue(Object obj, Field field) throws Exception {
        try {
            field.setAccessible(true);
            return field.get(obj);
        } catch (IllegalAccessException ex) {
            handleReflectionException(ex);
            throw new IllegalStateException(
                    "Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static void setField(Field field, Object target, Object value) {
        try {
            field.setAccessible(true);
            field.set(target, value);
        }
        catch (IllegalAccessException ex) {
            handleReflectionException(ex);
            throw new IllegalStateException(
                    "Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    private static void handleReflectionException(Exception ex) {
        if (ex instanceof NoSuchMethodException) {
            throw new IllegalStateException("Method not found: " + ex.getMessage());
        }
        if (ex instanceof IllegalAccessException) {
            throw new IllegalStateException("Could not access method: " + ex.getMessage());
        }
        if (ex instanceof InvocationTargetException) {
            handleInvocationTargetException((InvocationTargetException) ex);
        }
        if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        }
        throw new UndeclaredThrowableException(ex);
    }

    private static void handleInvocationTargetException(InvocationTargetException ex) {
        rethrowRuntimeException(ex.getTargetException());
    }

    private static void rethrowRuntimeException(Throwable ex) {
        if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        }
        if (ex instanceof Error) {
            throw (Error) ex;
        }
        throw new UndeclaredThrowableException(ex);
    }
}

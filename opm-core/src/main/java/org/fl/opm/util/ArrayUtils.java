package org.fl.opm.util;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-4
 * Time: 上午11:46
 * To change this template use File | Settings | File Templates.
 */
public class ArrayUtils {
    /**
     * 数组是否不为空
     *
     * @param array
     * @return
     */
    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    /**
     * 数组是否为空
     *
     * @param array
     * @return
     */
    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static Object[] union(Object[] objs1, Object[] objs2) {
        if (objs1 == null) {
            return objs2;
        }
        if (objs2 == null) {
            return objs1;
        }
        Object[] result = new Object[objs1.length + objs2.length];
        System.arraycopy(objs1, 0, result, 0, objs1.length);
        System.arraycopy(objs2, 0, result, objs1.length, objs2.length);
        return result;
    }

    public static boolean contains(Object[] array, Object val) {
        if (isEmpty(array)) {
            return false;
        }
        for (Object obj : array) {
            if (obj == null) {
                if (val == null) {
                    return true;
                }
            } else {
                if (obj.equals(val)) {
                    return true;
                }
            }
        }
        return false;
    }
}

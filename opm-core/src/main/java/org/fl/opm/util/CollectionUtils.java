package org.fl.opm.util;

import java.lang.reflect.Array;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-4
 * Time: 上午11:40
 * To change this template use File | Settings | File Templates.
 */
public class CollectionUtils {
    /**
     * 集合是否不为空
     *
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 集合是否为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> T[] toArray(Class<T> clz, Collection<T> collection) {
        return collection.toArray((T[]) Array.newInstance(clz, collection.size()));
    }

}

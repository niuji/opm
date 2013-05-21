package org.fl.opm.jdbc;

import java.util.HashMap;
import java.util.Map;

public class MetaInfoHolder {
    private static Map<Class, ModelJdbcMetaInfo> metaInfoMap = new HashMap<Class, ModelJdbcMetaInfo>();

    public static <T> ModelJdbcMetaInfo getMetaInfo(Class<T> modelClass) throws Exception {
        ModelJdbcMetaInfo meta = metaInfoMap.get(modelClass);
        if (meta == null) {
            synchronized (metaInfoMap) {
                meta = metaInfoMap.get(modelClass);
                if (meta == null) {
                    meta = new ModelJdbcMetaInfo(modelClass);
                    metaInfoMap.put(modelClass, meta);
                }
            }
        }
        return meta;
    }
}
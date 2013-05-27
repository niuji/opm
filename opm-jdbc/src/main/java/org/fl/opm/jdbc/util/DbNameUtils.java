package org.fl.opm.jdbc.util;

import org.fl.opm.util.StringUtils;

import javax.persistence.Column;
import java.lang.reflect.Field;

/**
 *
 * User: jiangyixin.stephen
 * Date: 2013-05-27 15:23
 */
public class DbNameUtils {
    public static String getColName(Field col) {
        Column column = col.getAnnotation(Column.class);
        if (column != null && StringUtils.isNotBlank(column.name())) {
            return column.name();
        } else {
            return DbNameUtils.toDbName(col.getName());
        }

    }

    public static String toDbName(String name) {
        if (StringUtils.isBlank(name)) {
            return "";
        }
        int len = name.length();
        StringBuilder sb = new StringBuilder(len + 10);
        char c;
        for (int i = 0; i < len; i++) {
            c = name.charAt(i);
            if (Character.isUpperCase(c) && i != 0) {
                sb.append('_').append(c);
            } else {
                sb.append(Character.toUpperCase(c));
            }
        }
        return sb.toString();
    }
}

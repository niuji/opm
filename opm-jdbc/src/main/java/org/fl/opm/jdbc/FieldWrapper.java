package org.fl.opm.jdbc;

import org.fl.opm.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-21 18:39
 */
public class FieldWrapper {
    private final Field field;
    private final Object object;

    public <T> FieldWrapper(Field field, Object object) {
        this.field = field;
        this.object = object;
    }

    public Class<?> getFieldType(){
        return field.getType();
    }

    public Object getFieldValue() throws Exception {
        return ReflectionUtils.getFieldValue(object, field);
    }
}

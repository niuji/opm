package org.fl.opm.cast.rule;

import org.fl.opm.cast.CastRule;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-6
 * Time: 下午5:16
 * To change this template use File | Settings | File Templates.
 */
public class NullRule implements CastRule {
    @Override
    public <T> T cast(Object src, Class<T> toType) throws Exception {
        // 原生数据的默认值
        if (toType.isPrimitive()) {
            if (toType == int.class)
                return (T) Integer.valueOf(0);
            else if (toType == long.class)
                return (T) Long.valueOf(0L);
            else if (toType == byte.class)
                return (T) Byte.valueOf((byte) 0);
            else if (toType == short.class)
                return (T) Short.valueOf((short) 0);
            else if (toType == float.class)
                return (T) Float.valueOf(.0f);
            else if (toType == double.class)
                return (T) Double.valueOf(.0);
            else if (toType == boolean.class)
                return (T) Boolean.FALSE;
            else if (toType == char.class)
                return (T) Character.valueOf(' ');
            throw new Exception("Not supported primitive type:" + toType);
        }
        // 是对象，直接返回 null
        return null;
    }
}

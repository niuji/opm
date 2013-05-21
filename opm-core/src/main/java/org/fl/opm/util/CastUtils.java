package org.fl.opm.util;

import org.fl.opm.cast.CastRule;
import org.fl.opm.cast.CastRules;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-6
 * Time: 下午4:27
 * To change this template use File | Settings | File Templates.
 */
public class CastUtils {
    public static <T> T cast(Object src, Class<T> toType) throws Exception {
        CastRule rule = CastRules.findRule(src, toType);
        if (rule == null) {
            throw new Exception(String.format("No cast rule for %s[%s] -> %s ", src, src == null ? "null" : src.getClass(), toType));
        }else{
            return rule.cast(src, toType);
        }


    }
}

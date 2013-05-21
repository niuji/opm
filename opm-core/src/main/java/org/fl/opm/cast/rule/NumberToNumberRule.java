package org.fl.opm.cast.rule;

import org.fl.opm.cast.CastRuleApplicable;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-6
 * Time: 下午5:30
 * To change this template use File | Settings | File Templates.
 */
public class NumberToNumberRule implements CastRuleApplicable {
    @Override
    public <T> T cast(Object src, Class<T> toType) throws Exception {
        return toType.getConstructor(String.class).newInstance(src.toString());
    }

    @Override
    public <T> boolean canApply(Class<?> fromType, Class<T> toType) {
        return Number.class.isAssignableFrom(fromType) && Number.class.isAssignableFrom(toType);
    }
}

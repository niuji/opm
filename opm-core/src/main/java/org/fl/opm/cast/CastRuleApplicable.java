package org.fl.opm.cast;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-6
 * Time: 下午5:36
 * To change this template use File | Settings | File Templates.
 */
public interface CastRuleApplicable extends CastRule {

    <T> boolean canApply(Class<?> fromType, Class<T> toType);
}

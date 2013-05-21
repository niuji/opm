package org.fl.opm.cast.rule;

import org.fl.opm.cast.CastRule;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-6
 * Time: 下午5:19
 * To change this template use File | Settings | File Templates.
 */
public class DirectCastRule implements CastRule {
    @Override
    public <T> T cast(Object src, Class<T> toType) throws Exception {
        return (T) src;
    }
}

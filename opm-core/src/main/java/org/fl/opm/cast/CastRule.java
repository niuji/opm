package org.fl.opm.cast;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-6
 * Time: 下午5:11
 * To change this template use File | Settings | File Templates.
 */
public interface CastRule {
    <T> T cast(Object src, Class<T> toType) throws Exception;
}

package org.fl.opm.cast;

import org.fl.opm.cast.rule.DirectCastRule;
import org.fl.opm.cast.rule.NullRule;
import org.fl.opm.cast.rule.NumberToNumberRule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-6
 * Time: 下午5:02
 * To change this template use File | Settings | File Templates.
 */
public class CastRules {
    private static final CastRule NULL_RULE = new NullRule();
    private static final CastRule DIRECT_CAST_RULE = new DirectCastRule();
    private static final List<CastRuleApplicable> rules = new ArrayList<CastRuleApplicable>();

    public static <T> CastRule findRule(Object src, Class<T> toType) {
        if (null == src) {
             return  NULL_RULE;
        }
        Class<?> fromType = src.getClass();
        if (fromType == toType || toType == null || fromType == null || fromType.getName().equals(toType.getName()) ||toType.isAssignableFrom(fromType)) {
            return DIRECT_CAST_RULE;
        }
        return findRegistedRules(fromType, toType);
    }

    private static <T> CastRule findRegistedRules(Class<?> fromType, Class<T> toType) {
        for(CastRuleApplicable rule : rules){
            if(rule.canApply(fromType, toType)){
                return rule;
            }
        }
        return null;
    }

    static {
         rules.add(new NumberToNumberRule());
    }
}

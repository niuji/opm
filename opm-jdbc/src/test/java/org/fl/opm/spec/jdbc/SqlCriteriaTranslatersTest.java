package org.fl.opm.spec.jdbc;

import org.fl.opm.spec.criteria.Criteria;
import org.fl.opm.spec.enums.Symbol;
import org.junit.Test;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-23 17:45
 */
public class SqlCriteriaTranslatersTest {
    @Test
    public void toWhereSqlTest() throws Exception {
        Criteria c = Criteria.emptyCriteria().and("aaa", Symbol.EQUAL, "ff", 1).and("bbb", Symbol.ISNULL, null, null);
        System.out.printf("%s", SqlCriteriaTranslaters.toWhereSql(c, new JdbcParamterHolder()));
    }
}

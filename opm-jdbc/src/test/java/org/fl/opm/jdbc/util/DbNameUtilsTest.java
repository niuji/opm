package org.fl.opm.jdbc.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-4
 * Time: 下午1:39
 * To change this template use File | Settings | File Templates.
 */
public class DbNameUtilsTest {
    @Test
    public void toDbNameTest(){
        assertThat(DbNameUtils.toDbName("DaBb"), is("DA_BB"));
        assertThat(DbNameUtils.toDbName("  "), is(""));
    }
}

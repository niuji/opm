package org.fl.opm.jdbc.util;

import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 * User: jiangyixin.stephen
 * Date: 2013-05-24 17:56
 */
public class DbNameUtilsTest {
    @Test
    public void toDbNameTest(){
        assertThat(DbNameUtils.toDbName("DaBb"), is("DA_BB"));
        assertThat(DbNameUtils.toDbName("  "), is(""));
    }

}

package org.fl.opm.util;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-14
 * Time: 下午5:46
 * To change this template use File | Settings | File Templates.
 */
public class ArrayUtilsTest {
    @Test
    public void testUnion() throws Exception {
        Object[] a = {"1", null};
        Object[] b = {new BigDecimal(3)};
        System.out.println(Arrays.toString(ArrayUtils.union(a, b)));
    }
}

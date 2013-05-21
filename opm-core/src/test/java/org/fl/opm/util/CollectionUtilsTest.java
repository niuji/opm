package org.fl.opm.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-6
 * Time: 上午10:19
 * To change this template use File | Settings | File Templates.
 */
public class CollectionUtilsTest {
    @Test
    public void toArrayTest(){
        List<String> list = new ArrayList<String>();
        list.add("dd");
        list.add(null);
        assertThat(CollectionUtils.toArray(String.class,list)[0], is("dd"));
        assertThat(CollectionUtils.toArray(String.class,list)[1], nullValue());
    }
}

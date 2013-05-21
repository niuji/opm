package org.fl.opm.util;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-4
 * Time: 下午12:00
 * To change this template use File | Settings | File Templates.
 */
public class ReflectionUtilsTest {
    @Test
    public void findFiedsTest(){
        assertThat(ReflectionUtils.findAllFields(ModelA.class, Deprecated.class).size(), is(4));
        assertThat(ReflectionUtils.findAllFields(ModelA.class, Documented.class).size(), is(0));
    }

    class ModelA extends Model{
        @Deprecated
        private String str1;
        @Deprecated
        private String str2;
    }
    class Model{
        @Deprecated
        private String str1;
        @Deprecated
        private String str2;
    }
}

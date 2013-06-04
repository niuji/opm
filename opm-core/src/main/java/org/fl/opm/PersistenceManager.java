package org.fl.opm;

import org.fl.opm.spec.Spec;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-3
 * Time: 下午1:15
 * To change this template use File | Settings | File Templates.
 */
public interface PersistenceManager {
    /**
     * 保存/更新实体
     *
     * @param model
     * @param <T>
     * @return 主键ID
     */
    public <T> Object save(T model) throws Exception;

    /**
     * 根据主键ID获得对象
     *
     * @param modelClass
     * @param id
     * @param <T>
     * @return
     */
    public <T> T findById(Class<T> modelClass, Object id) throws Exception;

    /**
     * 根据主键ID删除对象
     *
     * @param modelClass
     * @param <T>
     * @return
     */
    public <T> int deleteById(Class<T> modelClass, Object id) throws Exception;

    /**
     * 根据指定条件查询
     * @param spec
     * @return
     * @throws Exception
     */
    public List findBySpec(Spec spec) throws Exception;

    /**
     * 根据指定条件更新
     * @param modelClass
     * @param spec
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> int updateBySpec(Class<T> modelClass, Spec spec) throws Exception;

    /**
     * 根据指定条件删除
     * @param modelClass
     * @param spec
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> int deleteBySpec(Class<T> modelClass, Spec spec) throws Exception;
}

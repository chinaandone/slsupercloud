/**
 *
 */
package com.clever.common.service.base;

import com.clever.common.bean.page.Pager;
import com.clever.common.bean.page.WebPage;
import com.clever.common.repository.base.IBaseMapperDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Yate
 * @version 1.0
 * @date 2013年9月23日
 * @description 基础的
 */
public abstract class BaseService<E, PK> implements IBaseService<E, PK> {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    protected abstract IBaseMapperDao<E, PK> getMapperDao();

    /*
     * (non-Javadoc)
     *
     * @see org.yate.basic.service.IBaseService#add(java.lang.Object)
     */
    public void addEntity(E e) {
        this.getMapperDao().addEntity(e);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.yate.basic.service.IBaseService#remove(java.lang.Object)
     */
    public void removeEntity(PK id) {
        this.getMapperDao().removeEntity(id);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.yate.basic.service.IBaseService#update(java.lang.Object)
     */
    public void updateEntity(E e) {
        this.getMapperDao().updateEntity(e);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.yate.basic.service.IBaseService#getEntities(int, int,
     * java.lang.String, java.lang.String)
     */

    @Override
    public WebPage<E> getEntities(Pager pager, E e) {
        WebPage<E> result = new WebPage<E>(pager);
        result.setResult(this.getMapperDao().getEntities(result, e));
        return result;
    }

    @Override
    public List<E> listEntities(E e) {
        return this.getMapperDao().getEntities(null, e);
    }

    /*
         * (non-Javadoc)
         *
         * @see org.yate.basic.service.IBaseService#getEntity(java.lang.Object)
         */
    public E getEntity(PK id) {
        return this.getMapperDao().getEntity(id);
    }

}

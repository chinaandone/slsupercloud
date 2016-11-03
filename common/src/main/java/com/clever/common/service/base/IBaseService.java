// IBaseService
package com.clever.common.service.base;


import com.clever.common.bean.page.Pager;
import com.clever.common.bean.page.WebPage;

import java.util.List;

/**
 * @author Yate
 * @version 1.0
 * @date 2013年9月23日
 * @description TODO
 */
public interface IBaseService<E, PK> {
    /**
     * @param e
     * @description 详细说明
     */
    void addEntity(final E e);

    /**
     * @param id
     */
    void removeEntity(final PK id);


    /**
     * @param e
     * @description 详细说明
     */
    void updateEntity(final E e);


    /**
     * 此分页废弃，只能根据id来分
     * @param pager
     * @param e
     * @return
     */
    WebPage<E> getEntities(final Pager pager, final E e);


    /**
     * @param e
     * @return
     */
    List<E> listEntities(final E e);

    /**
     * @param id
     * @return
     * @throws Exception
     * @description 详细说明
     */
    E getEntity(final PK id);

}

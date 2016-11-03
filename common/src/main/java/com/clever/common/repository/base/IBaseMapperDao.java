// BaseDao
package com.clever.common.repository.base;

import com.clever.common.bean.page.WebPage;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author Yate
 * @version 1.0
 * @date 2013年9月23日
 * @description TODO
 */
public interface IBaseMapperDao<E, PK> extends IBaseMapper {
    /**
     * @param e
     * @description 详细说明
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addEntity(final E e);


    /**
     * @param e
     * @description 详细说明
     */
    void removeEntity(@Param(value = "id") final PK e);

    /**
     * @param e
     * @description 详细说明
     */
    void updateEntity(final E e);

    /**
     * @param id
     * @return
     * @throws Exception
     * @description 详细说明
     */
    E getEntity(@Param(value = "id") final PK id);

    /**
     * @param page
     * @param e
     * @return
     * @description 详细说明
     */
    List<E> getEntities(@Param(value = "page") WebPage<E> page, @Param(value = "e") final E e);
}

/**
 * 
 */
package com.clever.common.mybatis.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Yate
 * @date 2013年9月24日
 * @description 分页注解，给于PaginationInterceptor用
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Page {
	/**
	 * @return page index
	 */
	String page() default "pageIndex";

	/**
	 * @return page size
	 */
	String rows() default "pageSize";

	/**
	 * @return
	 */
	String total() default "total";

}

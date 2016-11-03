package com.clever.common.bean.page;

import com.clever.common.eum.WebPageLimitTypeEnum;

import java.util.Collections;
import java.util.List;

/**
 * INFO: ${todo}
 * User: zhaokai@mail.qianwang365.com
 * Date: 14-2-2
 * Time: 上午10:06
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public class ApiWebPage<T> {
    // 客户端查询 起始ID
    private String pageCondition;

    // 每页要求的记录条数.
    private Integer pageSize = 10;

    private Long totalCount = 0L;

    // 返回的结果.
    private List<T> result = Collections.emptyList();

    //0：数据没有限制 1：限制了（通常是数据被锁了不能全部返回)
    private WebPageLimitTypeEnum limitType = WebPageLimitTypeEnum.NO_LIMIT;

    public WebPageLimitTypeEnum getLimitType() {
        return limitType;
    }

    public void setLimitType(WebPageLimitTypeEnum limitType) {
        this.limitType = limitType;
    }

    public ApiWebPage() {
    }


    public ApiWebPage(String pageCondition) {
        this.pageCondition = pageCondition;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }


    public int getResultSize() {
        if (result == null)
            return 0;
        return result.size();
    }

    public String getPageCondition() {
        return pageCondition;
    }

    public void setPageCondition(String pageCondition) {
        this.pageCondition = pageCondition;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}

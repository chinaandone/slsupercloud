package com.clever.common.bean.page;

import com.clever.common.mybatis.annotations.Page;

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
@Page(page = "pageIndex", rows = "pageSize", total = "totalCount")
public class WebPage<T> extends Pager {

    protected long totalCount;  //总共多少条记录
    protected int firstIndex = 0;  // 该分页的第一条记录索引.(从0开始计数)
    protected int pageCount;  //总共多少页


    // 返回的结果.
    private List<T> result = Collections.emptyList();


    public WebPage() {
    }

    /**
     * @param pageSize
     */
    public WebPage(int pageSize) {
        super(1, pageSize);
    }

    public WebPage(Pager page) {
        super(page.getPageIndex(), page.getPageSize());
        //genPagingParams(entityAmount);
    }


    public WebPage(Pager page, int entityAmount) {
        super(page.getPageIndex(), page.getPageSize());
        genPagingParams(entityAmount);
    }


    // ---- getters and setters ----


    public int getPageCount() {
        return pageCount;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        genPagingParams(totalCount);
    }

    public void setFirstIndex(int firstIndex) {
        this.firstIndex = firstIndex;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * 根据 记录总数 刷新分页相关属性数据
     *
     * @param entityAmount
     */
    protected void genPagingParams(long entityAmount) {
        this.totalCount = entityAmount;
        //多少页
        pageCount = (int) ((entityAmount % pageSize == 0) ? entityAmount / pageSize : entityAmount / pageSize + 1);

        if (pageIndex < 1)
            pageIndex = 1;
        /*if (pageIndex > pageCount)
            pageIndex = pageCount;*/

        firstIndex = pageSize * (pageIndex - 1);
        if (firstIndex < 1)
            firstIndex = 0; //MYSQL 从0开始

        /*if (this.pageCount < SHOWPAGES) {
            *//** if中是总页数小于SHOWPAGES的情况 *//*
            this.startPage = 1;
            this.endPage = pageCount > 0 ? pageCount : 1;
        } else {
            *//** else中是总页数大于SHOWPAGES的情况 *//*
            if (this.pageIndex <= SHOWPAGES / 2 + 1) {
                this.startPage = 1;
                this.endPage = SHOWPAGES;
            } else {
                this.startPage = this.pageIndex - (SHOWPAGES / 2);
                this.endPage = this.pageIndex + (SHOWPAGES / 2 - 1);
                if (this.endPage >= this.pageCount) {
                    this.endPage = this.pageCount;
                    this.startPage = this.pageCount - SHOWPAGES + 1;
                }
            }
        }

        toWebPage();*/
    }


    public int getResultSize() {
        if (result == null)
            return 0;
        return result.size();
    }
}

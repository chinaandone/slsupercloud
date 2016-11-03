package com.clever.web.controller;

import com.clever.common.bean.page.Pager;
import com.clever.common.util.CommConfig;
import com.clever.common.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {

    @Autowired
    protected RedisTemplate<String, String> redisTemplate;

    @Autowired
    private CommConfig commConfig;

    Logger log = LoggerFactory.getLogger(this.getClass());

    private HttpServletRequest request;

    private HttpServletResponse response;

    private HttpSession session;

    protected BaseController(){
    }

    public BaseController(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    /**Spring会根据注解注入request和response*/
    @ModelAttribute
    public void setRequestAndResponse(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    protected HttpServletRequest getRequest(){
        return request;
    }

    protected HttpServletResponse getResponse(){
        return response;
    }

    protected CommConfig getCommConfig(){
        return commConfig;
    }
    /**
     * DATATABLE 组件传递的是 当前记录的位置
     * 转成成分页通用参数
     *
     * @param iDisplayStart
     * @param iDisplayLength
     * @return
     */
    public Pager getDataTableIndex(int iDisplayStart, int iDisplayLength) {
        Pager pager = new Pager();
        pager.setPageIndex((iDisplayStart + iDisplayLength) / iDisplayLength);
        pager.setPageSize(iDisplayLength);
        return pager;
    }

    /**
     * 得到32位的uuid
     * @return
     */
    public String get32UUID(){
        return UuidUtil.get32UUID();
    }
}

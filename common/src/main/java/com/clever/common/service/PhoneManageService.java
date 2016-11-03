package com.clever.common.service;

import com.clever.common.domain.ComputerInfo;
import com.clever.common.domain.InfoPhone;
import com.clever.common.service.base.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * Info: 手环（大学士）
 * User: Ddido.deng@clever-m.com
 * Date: 2016-04-20
 * Time: 10:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public interface PhoneManageService extends IBaseService {


    /**
     * 根据shopid获取所有的大学士
     * @param c
     * @return
     */
    public List<InfoPhone> getEntities(InfoPhone c);
}

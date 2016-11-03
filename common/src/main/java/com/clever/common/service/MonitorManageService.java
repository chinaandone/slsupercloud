package com.clever.common.service;

import com.clever.common.domain.ComputerInfo;
import com.clever.common.domain.Org;
import com.clever.common.service.base.IBaseService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Info: 实时状态监控（大学士）
 * User: Ddido.deng@clever-m.com
 * Date: 2016-04-20
 * Time: 10:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public interface MonitorManageService extends IBaseService {
    /**
     * 获取开/关机状态实时状态监控信息
     * @param deviceId
     * @return
     */
    public Map<String,Object> querySwitchStatus(String deviceId);

    /**
     * 获取器件自检状态信息
     * @param deviceId
     * @return
     */
    public Map<String,Object> querySelfCheck(String deviceId);

    /**
     * 网络信号质量查询
     * @param deviceId
     * @return
     */
    public Map<String,Object> queryNetworkSigna(String deviceId);

    /**
     * 433无线网络状态查询
     * @param deviceId
     * @return
     */
    public Map<String,Object> queryWirelessNetwork(String deviceId);

    /**
     * 翻台器/手环配置查询
     * @param deviceId
     * @return
     */
    public Map<String,Object> queryBraceletCfg(String deviceId);

    /**
     * RAM使用情况查询
     * @param deviceId
     * @return
     */
    public Map<String,Object> queryRAMObj(String deviceId);

    /**
     * ROM使用情况查询
     * @param deviceId
     * @return
     */
    public Map<String,Object> queryROMObj(String deviceId);

    /**
     * 当前音量设置查询
     * @param deviceId
     * @return
     */
    public Map<String,Object> queryVolume(String deviceId);

    /**
     * Camera图像/视频查看现场
     * @param deviceId
     * @return
     */
    public Map<String,Object> queryCameraUrl(String deviceId);

    /**
     * 避障设置查询
     * @param deviceId
     * @return
     */
    public Map<String,Object> queryObstacleCfg(String deviceId);

    /**
     * 接近感应设置查询
     * @param deviceId
     * @return
     */
    public Map<String,Object> queryInductionCfg(String deviceId);

    /**
     * 电池状态分析报告查询
     * @param deviceId
     * @return
     */
    public Map<String,Object> queryBatteryCfg(String deviceId);

    /**
     * 生成餐厅地图查询
     * @param deviceId
     * @return
     */
    public Map<String,Object> queryMapUrl(String deviceId);

    /**
     * 根据shopid获取所有的大学士
     * @param c
     * @return
     */
    public List<ComputerInfo> getEntities(ComputerInfo c);
}

package com.clever.common.service.impl;

import com.clever.common.domain.ComputerInfo;
import com.clever.common.domain.bean.*;
import com.clever.common.repository.MonitorManageDao;
import com.clever.common.repository.OrderInfoDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.MonitorManageService;
import com.clever.common.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
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
@Service("MonitorManageService")
@Transactional
public class MonitorManageServiceImpl extends BaseService implements MonitorManageService {

    @Autowired
    private MonitorManageDao monitorManageDao;
    @Override
    protected IBaseMapperDao<ComputerInfo,Long> getMapperDao() {
        return monitorManageDao;
    }

    /**
     * 获取开/关机状态实时状态监控信息
     * @param deviceId
     * @return
     */
    @Override
    public Map<String, Object> querySwitchStatus(String deviceId) {
        Map<String,Object> map = null;
        map = new HashMap<String,Object>();
        map.put("name", "开/关机状态");
        map.put("code", "switchStatus");
        map.put("data", true);
        map.put("type", "1001");
        return map;
    }

    /**
     * 获取器件自检状态信息
     * @param deviceId
     * @return
     */
    @Override
    public Map<String, Object> querySelfCheck(String deviceId) {
        Map<String,Object> map = null;
        map = new HashMap<String,Object>();
        SelfCheckBean selfCheckBean = new SelfCheckBean();
        selfCheckBean.setStatus(true);
        selfCheckBean.setCheckName("器件名称");
        SelfCheckBean selfCheckBean1 = new SelfCheckBean();
        selfCheckBean1.setStatus(false);
        selfCheckBean1.setCheckName("器件名称1");
        List<SelfCheckBean> selfCheckBeanList = new ArrayList<SelfCheckBean>();
        selfCheckBeanList.add(selfCheckBean);
        selfCheckBeanList.add(selfCheckBean1);
        map.put("name", "器件自检状态");
        map.put("code", "selfCheck");
        map.put("data", selfCheckBeanList);
        map.put("type", "1002");
        return map;
    }

    /**
     * 网络信号质量查询
     * @param deviceId
     * @return
     */
    @Override
    public Map<String, Object> queryNetworkSigna(String deviceId) {
        Map<String,Object> map = null;
        map = new HashMap<String,Object>();
        map.put("name", "网络信号质量");
        map.put("code", "networkSigna");
        map.put("data", 95);
        map.put("type", "1003");
        return map;
    }

    /**
     * 433无线网络状态查询
     * @param deviceId
     * @return
     */
    @Override
    public Map<String, Object> queryWirelessNetwork(String deviceId) {
        Map<String,Object> map = null;
        map = new HashMap<String,Object>();
        WirelessNetwork wn = new WirelessNetwork();
        wn.setMode("调幅AM");//通讯方式
        wn.setWorking("315MHZ/433MHZ ");//工作频率
        wn.setStability("±75KHZ");//频率稳定度
        wn.setPower("≤500MW");//发射功率
        wn.setElectric("≤0.1UA");//静态电流

        map.put("name", "433无线网络状态");
        map.put("code", "wirelessNetwork");
        map.put("data", wn);
        map.put("type", "1004");
        return map;
    }

    /**
     * 翻台器/手环配置查询
     * @param deviceId
     * @return
     */
    @Override
    public Map<String, Object> queryBraceletCfg(String deviceId) {
        Map<String,Object> map = null;
        map = new HashMap<String,Object>();
        BraceletCfg braceletCfg = new BraceletCfg();
        braceletCfg.setId("shxxxxxx");
        braceletCfg.setPower("85");
        braceletCfg.setSmsCurrent("5");
        braceletCfg.setSmsTotal("8");
        braceletCfg.setStatus(true);
        map.put("name", "获取翻台器/手环配置");
        map.put("code", "braceletCfg");
        map.put("data", braceletCfg);
        map.put("type", "1005");
        return map;
    }

    /**
     * RAM使用情况查询
     * @param deviceId
     * @return
     */
    @Override
    public Map<String, Object> queryRAMObj(String deviceId) {
        Map<String,Object> map = null;
        map = new HashMap<String,Object>();
        RAMObj rAMObj = new RAMObj();
        rAMObj.setCurrentRAM("3574");
        rAMObj.setTotalRAM("4568");
        map.put("name", "RAM使用情况");
        map.put("code", "rAMObj");
        map.put("data", rAMObj);
        map.put("type", "1006");
        return map;
    }

    /**
     * ROM使用情况查询
     * @param deviceId
     * @return
     */
    @Override
    public Map<String, Object> queryROMObj(String deviceId) {
        Map<String,Object> map = null;
        map = new HashMap<String,Object>();
        ROMObj rOMObj = new ROMObj();
        rOMObj.setCurrentROM("3574");
        rOMObj.setTotalROM("4568");
        map.put("name", "ROM使用情况");
        map.put("code", "rOMObj");
        map.put("data", rOMObj);
        map.put("type", "1007");
        return map;
    }

    /**
     * 当前音量设置查询
     * @param deviceId
     * @return
     */
    @Override
    public Map<String, Object> queryVolume(String deviceId) {
        Map<String,Object> map = null;
        map = new HashMap<String,Object>();
        map.put("name", "当前音量设置");
        map.put("code", "volume");
        map.put("data", 95);
        map.put("type", "1008");
        return map;
    }

    /**
     * Camera图像/视频查看现场
     * @param deviceId
     * @return
     */
    @Override
    public Map<String, Object> queryCameraUrl(String deviceId) {
        Map<String,Object> map = null;
        map = new HashMap<String,Object>();
        map.put("name", "Camera查看现场");
        map.put("code", "cameraUrl");
        map.put("data", "http://f.hiphotos.baidu.com/image/pic/item/00e93901213fb80e0ee553d034d12f2eb9389484.jpg");
        map.put("type", "1009");
        return map;
    }

    /**
     * 避障设置查询
     * @param deviceId
     * @return
     */
    @Override
    public Map<String, Object> queryObstacleCfg(String deviceId) {
        Map<String,Object> map = null;
        map = new HashMap<String,Object>();
        ObstacleCfg obstacleCfg = new ObstacleCfg();
        obstacleCfg.setStatus(false);
        obstacleCfg.setStatusValue(0);
        map.put("name", "避障设置");
        map.put("code", "obstacleCfg");
        map.put("data", obstacleCfg);
        map.put("type", "1010");
        return map;
    }

    /**
     * 接近感应设置查询
     * @param deviceId
     * @return
     */
    @Override
    public Map<String, Object> queryInductionCfg(String deviceId) {
        Map<String,Object> map = null;
        map = new HashMap<String,Object>();
        InductionCfg inductionCfg = new InductionCfg();
        inductionCfg.setStatus(true);
        inductionCfg.setStatusValue(9);
        map.put("name", "接近感应设置");
        map.put("code", "inductionCfg");
        map.put("data", inductionCfg);
        map.put("type", "1011");
        return map;
    }

    /**
     * 电池状态分析报告查询
     * @param deviceId
     * @return
     */
    @Override
    public Map<String, Object> queryBatteryCfg(String deviceId) {
        Map<String,Object> map = null;
        map = new HashMap<String,Object>();
        BatteryCfg batteryCfg = new BatteryCfg();
        batteryCfg.setCurrentRate("4.9");
        batteryCfg.setSurplusTime("66400000");
        map.put("name", "电池状态分析报告");
        map.put("code", "batteryCfg");
        map.put("data", batteryCfg);
        map.put("type", "1012");
        return map;
    }

    /**
     * 生成餐厅地图查询
     * @param deviceId
     * @return
     */
    @Override
    public Map<String, Object> queryMapUrl(String deviceId) {
        Map<String,Object> map = null;
        map = new HashMap<String,Object>();
        map.put("name", "生成餐厅地图");
        map.put("code", "mapUrl");
        map.put("data", "http://f.hiphotos.baidu.com/image/pic/item/00e93901213fb80e0ee553d034d12f2eb9389484.jpg");
        map.put("type", "1013");
        return map;
    }

    /**
     * 根据shopid获取所有的大学士
     * @param c
     * @return
     */
    @Override
    public List<ComputerInfo> getEntities(ComputerInfo c) {
        return monitorManageDao.getEntities(c);
    }
}

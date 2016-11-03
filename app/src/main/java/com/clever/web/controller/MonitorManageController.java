package com.clever.web.controller;


import com.alibaba.fastjson.JSON;
import com.clever.common.domain.ComputerInfo;
import com.clever.common.domain.bean.*;
import com.clever.common.domain.bean.request.Group;
import com.clever.common.domain.bean.request.Member;
import com.clever.common.service.MonitorManageService;
import com.clever.common.view.AjaxResultObj;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Info: 实时状态监控（大学士）
 * User: dido.deng@clever-m.com
 * Date: 2016-04-18
 * Time: 15:11
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Controller
@RequestMapping(value = "/monitor")
public class MonitorManageController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(MonitorManageController.class);

    @Autowired
    private MonitorManageService monitorManageService;

    /**
     * 开/关机状态
     * @param deviceId
     * @return
     */
    @RequestMapping(value = "/querySwitchStatus")
    @ResponseBody
    public AjaxResultObj querySwitchStatus(@RequestParam("deviceId") String deviceId) {
        logger.info("获取开/关机状态实时状态监控信息");
        try {
            if(StringUtils.isEmpty(deviceId)){
                return AjaxResultObj.failed("请输入设备ID");
            }
            Map<String,Object> map = null;
            map = new HashMap<String,Object>();
            map.put("name", "开/关机状态");
            map.put("code", "switchStatus");
            map.put("data", true);
            map.put("type", "boolean");
            return AjaxResultObj.success(map,"获取开/关机状态实时状态监控信息成功！","xxxx","Cooky");
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return AjaxResultObj.failed("糟了...系统出错了...");
        }
    }

    /**
     * 器件自检状态
     * @param deviceId
     * @return
     */
    @RequestMapping(value = "/querySelfCheck")
    @ResponseBody
    public AjaxResultObj querySelfCheck(@RequestParam("deviceId") String deviceId) {
        logger.info("获取器件自检状态信息");
        try {
            if(StringUtils.isEmpty(deviceId)){
                return AjaxResultObj.failed("请输入设备ID");
            }
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
            map.put("type", "obj");
            return AjaxResultObj.success(map,"获取器件自检状态信息成功！","xxxx","Cooky");
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return AjaxResultObj.failed("糟了...系统出错了...");
        }
    }

    /**
     * 网络信号质量查询
     * @param deviceId
     * @return
     */
    @RequestMapping(value = "/queryNetworkSigna")
    @ResponseBody
    public AjaxResultObj queryNetworkSigna(@RequestParam("deviceId") String deviceId) {
        logger.info("获取网络信号质量查询信息");
        try {
            if(StringUtils.isEmpty(deviceId)){
                return AjaxResultObj.failed("请输入设备ID");
            }
            Map<String,Object> map = null;
            map = new HashMap<String,Object>();
            map.put("name", "网络信号质量查询");
            map.put("code", "networkSigna");
            map.put("data", 95);
            map.put("type", "int");
            return AjaxResultObj.success(map,"获取网络信号质量查询信息成功！","xxxx","Cooky");
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return AjaxResultObj.failed("糟了...系统出错了...");
        }
    }

    /**
     * 433无线网络状态查询
     * @param deviceId
     * @return
     */
    @RequestMapping(value = "/queryWirelessNetwork")
    @ResponseBody
    public AjaxResultObj queryWirelessNetwork(@RequestParam("deviceId") String deviceId) {
        logger.info("获取433无线网络状态查询信息");
        try {
            if(StringUtils.isEmpty(deviceId)){
                return AjaxResultObj.failed("请输入设备ID");
            }

            Map<String,Object> map = null;
            map = new HashMap<String,Object>();
            WirelessNetwork wn = new WirelessNetwork();
            wn.setMode("调幅AM");//通讯方式
            wn.setWorking("315MHZ/433MHZ ");//工作频率
            wn.setStability("±75KHZ");//频率稳定度
            wn.setPower("≤500MW");//发射功率
            wn.setElectric("≤0.1UA");//静态电流

            map.put("name", "433无线网络状态查询");
            map.put("code", "wirelessNetwork");
            map.put("data", wn);
            map.put("type", "obj");
            return AjaxResultObj.success(map,"获取433无线网络状态查询信息成功！","xxxx","Cooky");

        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return AjaxResultObj.failed("糟了...系统出错了...");
        }
    }

    /**
     * 翻台器/手环配置查询
     * @param deviceId
     * @return
     */
    @RequestMapping(value = "/queryBraceletCfg")
    @ResponseBody
    public AjaxResultObj queryBraceletCfg(@RequestParam("deviceId") String deviceId) {
        logger.info("获取翻台器/手环配置查询信息");
        try {
            if(StringUtils.isEmpty(deviceId)){
                return AjaxResultObj.failed("请输入设备ID");
            }
            Map<String,Object> map = null;
            map = new HashMap<String,Object>();
            BraceletCfg braceletCfg = new BraceletCfg();
            braceletCfg.setId("shxxxxxx");
            braceletCfg.setPower("85");
            braceletCfg.setSmsCurrent("5");
            braceletCfg.setSmsTotal("8");
            braceletCfg.setStatus(true);
            map.put("name", "获取翻台器/手环配置查询");
            map.put("code", "braceletCfg");
            map.put("data", braceletCfg);
            map.put("type", "obj");
            return AjaxResultObj.success(map,"获取433无线网络状态查询信息成功！","xxxx","Cooky");
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return AjaxResultObj.failed("糟了...系统出错了...");
        }
    }

    /**
     * RAM使用情况查询
     * @param deviceId
     * @return
     */
    @RequestMapping(value = "/queryRAMObj")
    @ResponseBody
    public AjaxResultObj queryRAMObj(@RequestParam("deviceId") String deviceId) {
        logger.info("获取RAM使用情况查询信息");
        try {
            if(StringUtils.isEmpty(deviceId)){
                return AjaxResultObj.failed("请输入设备ID");
            }
            Map<String,Object> map = null;
            map = new HashMap<String,Object>();
            RAMObj rAMObj = new RAMObj();
            rAMObj.setCurrentRAM("3574");
            rAMObj.setTotalRAM("4568");
            map.put("name", "RAM使用情况查询");
            map.put("code", "rAMObj");
            map.put("data", rAMObj);
            map.put("type", "obj");
            return AjaxResultObj.success(map,"获取RAM使用情况查询成功！","xxxx","Cooky");
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return AjaxResultObj.failed("糟了...系统出错了...");
        }
    }

    /**
     * ROM使用情况查询
     * @param deviceId
     * @return
     */
    @RequestMapping(value = "/queryROMObj")
    @ResponseBody
    public AjaxResultObj queryROMObj(@RequestParam("deviceId") String deviceId) {
        logger.info("获取ROM使用情况查询信息");
        try {
            if(StringUtils.isEmpty(deviceId)){
                return AjaxResultObj.failed("请输入设备ID");
            }
            Map<String,Object> map = null;
            map = new HashMap<String,Object>();
            ROMObj rOMObj = new ROMObj();
            rOMObj.setCurrentROM("3574");
            rOMObj.setTotalROM("4568");
            map.put("name", "ROM使用情况查询");
            map.put("code", "rOMObj");
            map.put("data", rOMObj);
            map.put("type", "obj");
            return AjaxResultObj.success(map,"获取ROM使用情况查询成功！","xxxx","Cooky");
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return AjaxResultObj.failed("糟了...系统出错了...");
        }
    }

    /**
     * 当前音量设置查询
     * @param deviceId
     * @return
     */
    @RequestMapping(value = "/queryVolume")
    @ResponseBody
    public AjaxResultObj queryVolume(@RequestParam("deviceId") String deviceId) {
        logger.info("获取当前音量设置查询信息");
        try {
            if(StringUtils.isEmpty(deviceId)){
                return AjaxResultObj.failed("请输入设备ID");
            }
            Map<String,Object> map = null;
            map = new HashMap<String,Object>();
            map.put("name", "当前音量设置查询");
            map.put("code", "volume");
            map.put("data", 95);
            map.put("type", "int");
            return AjaxResultObj.success(map,"获取当前音量设置查询成功！","xxxx","Cooky");
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return AjaxResultObj.failed("糟了...系统出错了...");
        }
    }

   /**
     * Camera图像/视频查看现场
     * @param deviceId
     * @return
     */
    @RequestMapping(value = "/queryCameraUrl")
    @ResponseBody
    public AjaxResultObj queryCameraUrl(@RequestParam("deviceId") String deviceId) {
        logger.info("获取Camera图像/视频查看现场信息");
        try {
            if(StringUtils.isEmpty(deviceId)){
                return AjaxResultObj.failed("请输入设备ID");
            }
            Map<String,Object> map = null;
            map = new HashMap<String,Object>();
            map.put("name", "Camera图像/视频查看现场");
            map.put("code", "cameraUrl");
            map.put("data", "xx/xxx.jpg");
            map.put("type", "img");
            return AjaxResultObj.success(map,"获取Camera图像/视频查看现场成功！","xxxx","Cooky");
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return AjaxResultObj.failed("糟了...系统出错了...");
        }
    }

    /**
     * 避障设置查询
     * @param deviceId
     * @return
     */
    @RequestMapping(value = "/queryObstacleCfg")
    @ResponseBody
    public AjaxResultObj queryObstacleCfg(@RequestParam("deviceId") String deviceId) {
        logger.info("获取避障设置查询信息");
        try {
            if(StringUtils.isEmpty(deviceId)){
                return AjaxResultObj.failed("请输入设备ID");
            }
            Map<String,Object> map = null;
            map = new HashMap<String,Object>();
            ObstacleCfg obstacleCfg = new ObstacleCfg();
            obstacleCfg.setStatus(false);
            obstacleCfg.setStatusValue(0);
            map.put("name", "避障设置查询");
            map.put("code", "obstacleCfg");
            map.put("data", obstacleCfg);
            map.put("type", "obj");
            return AjaxResultObj.success(map,"获取避障设置查询成功！","xxxx","Cooky");
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return AjaxResultObj.failed("糟了...系统出错了...");
        }
    }

    /**
     * 接近感应设置查询
     * @param deviceId
     * @return
     */
    @RequestMapping(value = "/queryInductionCfg")
    @ResponseBody
    public AjaxResultObj queryInductionCfg(@RequestParam("deviceId") String deviceId) {
        logger.info("获取接近感应设置查询信息");
        try {
            if(StringUtils.isEmpty(deviceId)){
                return AjaxResultObj.failed("请输入设备ID");
            }
            Map<String,Object> map = null;
            map = new HashMap<String,Object>();
            InductionCfg inductionCfg = new InductionCfg();
            inductionCfg.setStatus(true);
            inductionCfg.setStatusValue(9);
            map.put("name", "接近感应设置查询");
            map.put("code", "inductionCfg");
            map.put("data", inductionCfg);
            map.put("type", "obj");
            return AjaxResultObj.success(map,"获取接近感应设置查询成功！","xxxx","Cooky");
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return AjaxResultObj.failed("糟了...系统出错了...");
        }
    }

    /**
     * 电池状态分析报告查询
     * @param deviceId
     * @return
     */
    @RequestMapping(value = "/queryBatteryCfg")
    @ResponseBody
    public AjaxResultObj queryBatteryCfg(@RequestParam("deviceId") String deviceId) {
        logger.info("获取电池状态分析报告查询信息");
        try {
            if(StringUtils.isEmpty(deviceId)){
                return AjaxResultObj.failed("请输入设备ID");
            }
            Map<String,Object> map = null;
            map = new HashMap<String,Object>();
            BatteryCfg batteryCfg = new BatteryCfg();
            batteryCfg.setCurrentRate("4.9% 每小时");
            batteryCfg.setSurplusTime("18小时,23分");
            map.put("name", "电池状态分析报告查询");
            map.put("code", "batteryCfg");
            map.put("data", batteryCfg);
            map.put("type", "obj");
            return AjaxResultObj.success(map,"获取电池状态分析报告查询成功！","xxxx","Cooky");
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return AjaxResultObj.failed("糟了...系统出错了...");
        }
    }

    /**
     * 生成餐厅地图查询
     * @param deviceId
     * @return
     */
    @RequestMapping(value = "/queryMapUrl")
    @ResponseBody
    public AjaxResultObj queryMapUrl(@RequestParam("deviceId") String deviceId) {
        logger.info("开始生成餐厅地图查询信息");
        try {
            if(StringUtils.isEmpty(deviceId)){
                return AjaxResultObj.failed("请输入设备ID");
            }
            Map<String,Object> map = null;
            map = new HashMap<String,Object>();
            map.put("name", "生成餐厅地图查询");
            map.put("code", "mapUrl");
            map.put("data", "xx/xxx.jpg");
            map.put("type", "map");
            return AjaxResultObj.success(map,"获取生成餐厅地图查询成功！","xxxx","Cooky");
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return AjaxResultObj.failed("糟了...系统出错了...");
        }
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public AjaxResultObj query(@RequestParam("data") String data) {
        logger.info("获取大学士动态状态信息");
        try {
            if(StringUtils.isEmpty(data)){
                return AjaxResultObj.failed("请输入设备ID");
            }
            Group group = JSON.parseObject(data, Group.class);
            List<Member> memberList = group.getMembers();
            Map<String,Object> mapAll = new HashMap<String,Object>();
            if(memberList!=null){
                for (Member member : memberList){
                    String action = member.getAction();
                    String parameter = member.getParameter();
                    if (action.trim().equals("querySwitchStatus")){//开/关机状态
                        Map<String,Object> map = monitorManageService.querySwitchStatus(parameter);
                        mapAll.put("switchStatusObj",map);
                    }else if (action.trim().equals("querySelfCheck")){//器件自检状态
                        Map<String,Object> map = monitorManageService.querySelfCheck(parameter);
                        mapAll.put("selfCheckObj", map);
                    }else if (action.trim().equals("queryNetworkSigna")){//网络信号质量查询
                        Map<String,Object> map = monitorManageService.queryNetworkSigna(parameter);
                        mapAll.put("networkSignaObj", map);
                    }else if (action.trim().equals("queryWirelessNetwork")){//433无线网络状态查询
                        Map<String,Object> map = monitorManageService.queryWirelessNetwork(parameter);
                        mapAll.put("queryWirelessNetwork", map);
                    }else if (action.trim().equals("queryBraceletCfg")){//翻台器/手环配置查询
                        Map<String,Object> map = monitorManageService.queryBraceletCfg(parameter);
                        mapAll.put("braceletCfgObj", map);
                    }else if (action.trim().equals("queryRAMObj")){//RAM使用情况查询
                        Map<String,Object> map = monitorManageService.queryRAMObj(parameter);
                        mapAll.put("rAMObjObj", map);
                    }else if (action.trim().equals("queryROMObj")){//ROM使用情况查询
                        Map<String,Object> map = monitorManageService.queryROMObj(parameter);
                        mapAll.put("rOMObjObj", map);
                    }else if (action.trim().equals("queryVolume")){//当前音量设置查询
                        Map<String,Object> map = monitorManageService.queryVolume(parameter);
                        mapAll.put("volumeObj", map);
                    }else if (action.trim().equals("queryCameraUrl")){//Camera图像/视频查看现场
                        Map<String,Object> map = monitorManageService.queryCameraUrl(parameter);
                        mapAll.put("cameraUrlObj", map);
                    }else if (action.trim().equals("queryObstacleCfg")){//避障设置查询
                        Map<String,Object> map = monitorManageService.queryObstacleCfg(parameter);
                        mapAll.put("obstacleCfgObj", map);
                    }else if (action.trim().equals("queryInductionCfg")){//接近感应设置查询
                        Map<String,Object> map = monitorManageService.queryInductionCfg(parameter);
                        mapAll.put("inductionCfgObj", map);
                    }else if (action.trim().equals("queryBatteryCfg")){//电池状态分析报告查询
                        Map<String,Object> map = monitorManageService.queryBatteryCfg(parameter);
                        mapAll.put("batteryCfgObj", map);
                    }else if (action.trim().equals("queryMapUrl")){//生成餐厅地图查询
                        Map<String,Object> map = monitorManageService.queryMapUrl(parameter);
                        mapAll.put("mapUrlObj", map);
                    }
                }
            }
            return AjaxResultObj.success(mapAll,"获取大学士动态状态信息成功！","xxxx","Cooky");
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return AjaxResultObj.failed("糟了...系统出错了...");
        }
    }

}

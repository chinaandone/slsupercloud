package com.clever.web.controller;

import com.clever.common.domain.MenuInfo;
import com.clever.common.service.MenuManageService;
import com.clever.common.service.PaginationService;
import com.clever.common.sql.SqlDomainNames;
import com.clever.common.view.AjaxResult;
import com.clever.common.view.PaginationView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * Info: clever
 * User: Gary.zhang@clever-m.com
 * Date: 2016-01-21
 * Time: 15:11
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@RestController
@RequestMapping(value = "/menu")
public class MenuManageController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(MenuManageController.class);

    @Autowired
    private PaginationService paginationService;
    @Autowired
    private MenuManageService menuManageService;


    @RequestMapping(value = "/index")
    public AjaxResult index(@RequestParam("shopId")String shopId, @RequestParam("indexId") String indexId){
        System.out.println(shopId+"----"+indexId);
        return AjaxResult.success("success");
    }

    @RequestMapping(value = "/list")
    public AjaxResult shopList(@ModelAttribute("paginationView") PaginationView paginationView,
                               @RequestParam("shopId") String shopId,
                               @RequestParam(value="queryKey",required = false) String queryKey) {
        logger.info("----商户菜品列表查询--shopId----" + shopId);
        paginationView.loadFilter().put("shopId", StringUtils.isBlank(shopId) ? null : shopId);
        paginationView.loadFilter().put("queryKey", StringUtils.isBlank(queryKey)?null : queryKey);
        paginationView.setDomain(SqlDomainNames.MENU_INFO_LIST);
        paginationService.listInPage(paginationView);
        return AjaxResult.success(paginationView);
    }

    @RequestMapping(value = "/save")
    public AjaxResult save(@RequestParam("scanCode") String scanCode,
                           @RequestParam("orgId") String orgId,
                           @RequestParam("name") String name,
                           @RequestParam("price") String price,
                           @RequestParam("photo") String photo,
                           @RequestParam("menuId") String menuId,
                           @RequestParam("subMenuId") String subMenuId,
                           @RequestParam("status") int status
                                                              ) {
        logger.info("----商户菜品添加-- orgId----" + orgId);
        //name字段长度限制判断
        if(name.length()>60){
            return AjaxResult.failed("商户菜品名称字数不得大于60");
        }
        //price字段只能为数字
        if(!StringUtils.isNumeric(price)){
            return AjaxResult.failed("商户菜品价格只能为整数");
        }
        //scanCode字段只能为数字
        if(!StringUtils.isNumeric(scanCode)){
            return AjaxResult.failed("商户菜品扫描码只能为整数");
        }
        MenuInfo menuInfo = new MenuInfo(Long.parseLong(orgId));
        menuInfo.setPhoto(photo);
        menuInfo.setScanCode(scanCode);
        menuInfo.setName(name);
        menuInfo.setPrice(price);
        menuInfo.setCreateTime(new Date());
        menuInfo.setUpdateTime(menuInfo.getCreateTime());
        menuInfo.setStatus(status);
        menuInfo.setMenuId(menuId);
        menuInfo.setSubMenuId(subMenuId);
        menuManageService.addMenuInfo(menuInfo);
        return AjaxResult.success("添加成功");
    }

    @RequestMapping(value = "/edit")
    public AjaxResult update(@RequestParam("id") long id,
                             @RequestParam("scanCode") String scanCode,
                             @RequestParam("orgId") String orgId,
                             @RequestParam("name") String name,
                             @RequestParam("price") String price,
                             @RequestParam("photo") String photo,
                             @RequestParam("menuId") String menuId,
                             @RequestParam("subMenuId") String subMenuId,
                             @RequestParam("status") String status) {
        logger.info("----商户菜品修改-- menuId----" + menuId);
        //name字段长度限制判断
        if(name.length()>60){
             return AjaxResult.failed("商户菜品名称字数不得大于60");
        }
        //price字段只能为数字
        if(!StringUtils.isNumeric(price)){
            return AjaxResult.failed("商户菜品价格只能为整数");
        }
        //scanCode字段只能为数字
        if(!StringUtils.isNumeric(scanCode)){
            return AjaxResult.failed("商户菜品扫描码只能为整数");
        }
        menuManageService.updateMenu(id,menuId, scanCode, orgId, price, name, photo, status,subMenuId);
        return AjaxResult.success("修改成功");
    }

    /*del this line*/
    @RequestMapping(value = "/deleteByShopId")
    public AjaxResult deleteByShopId(@RequestParam("shopId") long shopId){
        logger.info("---商户菜品删除-----"+ shopId);
        menuManageService.deleteByShopId(shopId);
        return AjaxResult.success("删除成功");
    }

    @RequestMapping(value = "/upload")
    public AjaxResult fileUpload(@RequestParam("file") CommonsMultipartFile[] files, @RequestParam("shopId") String shopId) {
        logger.info("----商户菜品上传----"+shopId);
        if(StringUtils.isEmpty(shopId)){
            return AjaxResult.failed("请输入需要导入的商户ID");
        }

        for (int i = 0; i < files.length; i++) {
            System.out.println("fileName---------->" + files[i].getOriginalFilename());
            if (!files[i].isEmpty()) {
                String type = files[i].getContentType();
                if(!type.equals("text/plain")){
                     return AjaxResult.failed("请上传txt文档，其他格式不支持");
                }
                int startTime = (int) System.currentTimeMillis();
                try {
                    BufferedReader d = new BufferedReader(new InputStreamReader(files[i].getInputStream(),"utf-8"));

                    String valueString = null;
                    int line = 0; //记录读取行数
                    while ((valueString = d.readLine())!=null) {
                        line++;
                        char s = valueString.trim().charAt(0);
                        //65279是空字符
                        if (s == 65279) {
                            if (valueString.length() > 1) {
                                valueString = valueString.substring(1);
                            }
                        }
                        String[] values = valueString.split("\\s+");
                        if(values.length!= 5){
                            if(line==1){  //第一行若格式错误，直接stop
                                return AjaxResult.failed("上传的文档格式存在错误，终止上传");
                            }else{   //后续出现格式错误，结束本次循环继续
                                continue;
                            }
                        }
                        int m =0;
                        MenuInfo menu = new MenuInfo(Long.parseLong(shopId));
                        for (int j = 0; j < values.length; j++) {
                            if(StringUtils.isNotEmpty(values[j])){
                                String temp = values[j].trim();
                                switch (m){
                                    case 0:
                                        String menuId = temp.substring(0, 5);
                                        String subMenuId = temp.substring(5, 7);
                                        String menuIds = temp.substring(7);
                                        menu.setMenuId(menuId.trim());
                                        menu.setSubMenuId(subMenuId.trim());
                                        menu.setName(menuIds);
                                        break;
                                    case 1:
                                        menu.setPrice((int)Double.parseDouble(temp.substring(0,temp.length()-2))+"");
                                        System.out.println(1+"---"+ values[j].substring(0, values[j].length() - 1));
                                        break;
                                    case 2:
                                        menu.setMenuCode(temp);
                                        System.out.println(2 + "---" + values[j]);
                                        break;
                                    case 3:
                                        break;
                                    case 4:
                                        menu.setScanCode(temp);
                                        System.out.println(4 + "---" + values[j]);
                                        break;
                                }

                                m++;
                                if(m>4){
                                    menu.setStatus(1);
                                    menuManageService.addMenuInfo(menu);
                                    m=0;
                                    break;
                                }
                            }
                        }
                    }
                    d.close();
                    int finalTime = (int) System.currentTimeMillis();
                    System.out.println(finalTime - startTime);

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("上传出错");
                }
            }
        }

        return AjaxResult.success("上传成功");
    }
}

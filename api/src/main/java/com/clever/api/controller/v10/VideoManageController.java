package com.clever.api.controller.v10;

import com.clever.api.controller.BaseController;
import com.clever.api.util.Constants;
import com.clever.api.view.ClientAjaxResult;
import com.clever.api.view.v10.VideoBusinessView;
import com.clever.common.domain.DetailText;
import com.clever.common.domain.RollMain;
import com.clever.common.domain.Video;
import com.clever.common.domain.VideoBusiness;
import com.clever.common.service.DetailTextManageService;
import com.clever.common.service.RollMainManageService;
import com.clever.common.service.VideoBusinessManageService;
import com.clever.common.service.VideoManageService;
import com.clever.common.sql.SqlDomainNames;
import com.clever.common.util.MyStringUtils;
import com.clever.common.util.TypeConverter;
import com.clever.common.view.PaginationView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Info: 视频接口
 * User: enva.liang@clever-m.com
 * Date: 2016-01-25
 * Time: 15:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@RestController
@Scope("prototype")
@RequestMapping("/v10")
public class VideoManageController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(VideoManageController.class);

    @Autowired
    private VideoBusinessManageService videoBusinessManageService;

    @Value("${Demand_Video_Switch}")
    private boolean Demand_Video_Switch;
    /**
     * 点点笔广告视频
     * @param orgId
     * @param type: 0或null只显示商户,1需要显示商业
     * @return
     */
    @RequestMapping(value = "/video/list")
    public ClientAjaxResult queryVideoList(@RequestParam(value = "orgId") Long orgId,
                                              @RequestParam(value = "type", required = false) Integer type) {
        logger.info("查询点点笔广告视频列表,orgId:" + orgId);
        try {
//            List<VideoBusiness> list = videoBusinessManageService.getEntities(new VideoBusiness(null, orgId, type, Constants.VIDEO_ROLLMAIN));//取待机广告视频
            //取待机广告视频和点播视频，让pad每天开机的时候就开始下载点播视频，这样在点播的时候才会流畅
            List<VideoBusiness> list = videoBusinessManageService.getEntities(new VideoBusiness(null, orgId, type, null));
            if(list == null){
                return ClientAjaxResult.success();
            }
            List<VideoBusinessView> list1 = new ArrayList<VideoBusinessView>();
            for(VideoBusiness v : list){
                VideoBusinessView view = new VideoBusinessView(v);
                if(view.getTimeStart() == null && view.getTimeEnd() == null){
                    continue;
                }else if(view.getTimeStart() == null && view.getTimeEnd().compareTo(new Date()) >= 0){
                    view.setEnable(1);
                }else {
                    int startCompare = view.getTimeStart().compareTo(new Date());
                    int endCompare = view.getTimeEnd().compareTo(new Date());
                    if (startCompare <= 0 && endCompare >= 0) {
                        view.setEnable(1);
                    } else {
                        continue;
                    }
                }

                //-----------------
                if(Demand_Video_Switch) {
                    if (view.getKind() == 2) {//20160712去掉点播视频，功能暂时不用了
                        continue;
                    }
                }
                //-----------------

                list1.add(view);
            }
            return ClientAjaxResult.success(list1);
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ClientAjaxResult.failed("糟了...系统出错了...");
        }
    }

    @RequestMapping(value = "/video/get")
    public ClientAjaxResult queryVideoById(@RequestParam(value = "vBId") Long vBId,
                                              @RequestParam(value = "type", required = false) Integer type) {
        logger.info("根据视频商业属性ID查询点点笔广告视频,videoBusinessId:" + vBId);
        try {
            VideoBusiness vB = (VideoBusiness)videoBusinessManageService.getEntity(new VideoBusiness(vBId,null , type,null));
            if(vB == null){
                return ClientAjaxResult.success();
            }
            VideoBusinessView vBV1 = new VideoBusinessView(vB);
            return ClientAjaxResult.success(vBV1);
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ClientAjaxResult.failed("糟了...系统出错了...");
        }
    }



}
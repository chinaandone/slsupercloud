package com.clever.common.repository.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.clever.common.client.view.AdvertisementView;
import com.clever.common.client.view.RollDetailView;
import com.clever.common.client.view.RollMainView;
import com.clever.common.util.HttpClientUtil;
import com.clever.common.util.ImageCompress;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
 * Created by wtw001 on 2015/8/10.
 */
public class RollMainManageControllerTest extends TestCase {
//    @Test
    public void test() {
        String jsonString = "";
        String url = "http://localhost:8080/rollMain/saveOrUpdate.do";
        Map params = new HashMap();
        List<RollMainView> rollMainViewList = new ArrayList<RollMainView>();
        RollMainView r1 = new RollMainView();
        r1.setRollMainId(100L);
        r1.setDescription("test1");
        r1.setEndTime(20160209L);
        r1.setOrderSeq(1);
        r1.setRollTime(20);
        r1.setStartTime(20160206L);
        r1.setTitle("testTitle1");
        r1.setType(1);
        r1.setPictrueId(1L);

        AdvertisementView a = new AdvertisementView();
        a.setAdvertisementId(100L);
        a.setPictrueId(1L);
//        a.setTitle("广告标题a");
        a.setOrderSeq(1);
//        a.setDescription("miaoshuaaa");

        AdvertisementView a1 = new AdvertisementView();
        a1.setAdvertisementId(101L);
        a1.setPictrueId(2L);
//        a1.setTitle("广告标题2");
        a1.setOrderSeq(2);
//        a1.setDescription("miaoshu2");

        RollDetailView d = new RollDetailView();
        d.setOrderSeq(2);
        d.setPictrueId(2L);
//        d.setRollTime(13);

        RollDetailView d1 = new RollDetailView();
        d1.setOrderSeq(3);
        d1.setPictrueId(3L);
//        d1.setRollTime(19);

        List<AdvertisementView> aList = new ArrayList<AdvertisementView>();
        aList.add(a);
        aList.add(a1);

        List<RollDetailView> rList = new ArrayList<RollDetailView>();
        rList.add(d);
        rList.add(d1);

//        r1.setAdvertisementViewList(aList);
        r1.setRollDetailViewList(rList);

        JSON js = (JSON) JSON.toJSON(r1);
        System.out.println("jsjsjsjsjsjsjsjs="+js);
        params.put("rollMainViewJson",js.toJSONString());
        jsonString = HttpClientUtil.doPost(url, params);
        System.out.println("ssssss="+jsonString);

//        String url = "http://localhost:8080/advertisement/edit.do";
//        Map params = new HashMap();
//        params.put("advertisementId", 111);
//        params.put("description", "ddd");
//        params.put("orderSeq",1);
//        params.put("title","1");
//        params.put("pictrueId",1L);
//        String jsonString = HttpClientUtil.doPost(url, params);
//        System.out.println("ssssss="+jsonString);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("开始2222222222222222222222：");
        ImageCompress imgCom = null;
        try {
            imgCom = new ImageCompress("F:\\下载图片\\036.jpg");
            imgCom.resizeFix(240, 240, "F:\\下载图片\\0362.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("结束：1111111111");
    }
}
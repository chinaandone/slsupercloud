package com.clever.api.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: zhongjiaren
 * Date: 2013-5-17
 * Time: 20:46:20
 * To change this template use File | Settings | File Templates.
 */
public class IPUtil {
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        	ip = request.getHeader("x-forwarded-for");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        	ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        	ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        //防止多级代理时返回过个ip。
        if(ip != null && ip.indexOf(",") != -1){
            ip= ip.substring(0,ip.indexOf(","));
        }
        return ip;
    }

    /**
     *
     * @param ip   192.168.7.116
     * @param ipSection  192.168.8.*  或者  192.168.7.116
     * @return
     */
    public static boolean singleSectionExists(String ip, String ipSection) {
        String[] ips = ip.split("\\.");
        String[] ipSections = ipSection.split("\\.");
        if(ips.length != 4 || ipSections.length != 4) {
            return false;
        }
        for(int i=0; i<4; i++) {
            if(!ipSections[i].equals("*") && Integer.parseInt(ips[i]) != Integer.parseInt(ipSections[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param ip   192.168.8.116
     * @param confIpSection   192.168.8.*,192.168.7.116
     * @return
     */
    public static boolean ipExists4Conf(String ip, String confIpSection) {
        boolean ipFlag = true;

        if (StringUtils.isNotEmpty(confIpSection)) {
            ipFlag = false;
            String[] confIpSections = confIpSection.split(",");

            if(confIpSections != null && confIpSections.length != 0) {
                for(String ipSection : confIpSections) {
                    ipFlag = singleSectionExists(ip, ipSection);
                    if(ipFlag)
                        break;
                }
            }
        }
        return ipFlag;
    }
}

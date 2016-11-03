package com.clever.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * INFO: 对应constant.properties中的常量配置
 * User: zhangxinglong@rui10.com
 * Date: 15-05-13
 * Time: 上午9:25
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public class PropertiesUtils {

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);
    private static Properties properties = SpringContextHolder.getBean("remoteConfigs");

    public static void initproperties(Properties properties) {
        PropertiesUtils.properties = properties;

    }

    /**
     * 获取系统环境变量的值.
     *
     * @param key 环境变量的key值.
     * @return 环境变量的value值.
     */
    public static String getPropertieValue(String key) {
        if (!properties.containsKey(key)) {
            logger.error("读取properties文件,KEY=" + key + "不存在");
            return null;
        }

        String value = properties.getProperty(key);
        return StringUtils.trim(value);
    }

    /**
     * 获取系统环境变量的值.
     *
     * @param keys 环境变量的key值.
     * @return 环境变量的value值.
     */
    public static String getPropertieValues(String... keys) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String key : keys) {
            if (!properties.containsKey(key)) {
                logger.error("读取properties文件,KEY=" + key + "不存在");
                return null;
            }

            String value = properties.getProperty(key);
            stringBuffer.append(value);
        }
        return StringUtils.trim(stringBuffer.toString());
    }


    /**
     * 获取系统环境变量的值.
     *
     * @param key 环境变量的key值.
     * @return 环境变量的value值.
     */
    public static String getPropertieValue(String key, String defaultValue) {
        if (StringUtils.isBlank(key)) {
            return defaultValue;
        }
        String value = properties.getProperty(key);
        if (StringUtils.isBlank(value)) {
            logger.error("读取properties文件,KEY=" + key + "不存在");
            return StringUtils.trim(defaultValue);
        }
        return StringUtils.trim(value);
    }
}

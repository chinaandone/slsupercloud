package com.clever.common.util; /**
 /**
 * INFO: 本方法提供给客户端请求接口中 涉及到分页参数的解析问题
 * User: zhaokai@mail.qianwang365.com
 * Date: 2014/9/26
 * Time: 16:26
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.clever.common.exception.DataValidateException;
import com.clever.common.view.ApiPageCondition;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JsonUtils {
    /**
     * 从pageCondition 解析出 page属性的值
     *
     * @param pageCondition JSON格式
     * @return
     */
    public static Integer getPageFormPageCondition(String pageCondition) throws DataValidateException {
        if (StringUtils.isBlank(pageCondition)) {
            throw new DataValidateException("请求参数不正确");
        }

        JSONObject object = JSON.parseObject(pageCondition.trim());
        Integer page = object.getIntValue(ApiPageCondition.PAGE);
        if (null == page) {
            throw new DataValidateException("请求参数不正确");
        }
        return page;

    }

    public static Long getKeyLongValueWithJson(String jsonStr, String key) {
        if (StringUtils.isBlank(jsonStr)) {
            return null;
        }

        JSONObject object = JSON.parseObject(jsonStr.trim());
        return object.getLong(key);
    }

    public static String getKeyStringalueWithJson(String jsonStr, String key) {
        if (StringUtils.isBlank(jsonStr)) {
            return null;
        }
        JSONObject object = JSON.parseObject(jsonStr.trim());
        return object.getString(key);
    }

    public static String setLabelDataType(String jsonStr, String value) {
        JSONObject object = new JSONObject();
        if (StringUtils.isNotBlank(jsonStr)) {
            object = JSON.parseObject(jsonStr.trim());
        }
        object.put(ApiPageCondition.DATA_TYPE, value);
        return object.toJSONString();
    }

    public static Integer getKeyIntegerValueWithJson(String jsonStr, String key) {
        if (StringUtils.isBlank(jsonStr)) {
            return null;
        }
        JSONObject object = JSON.parseObject(jsonStr.trim());
        return object.getInteger(key);
    }


    public static Map<String, Object> formatMapWithJson(String jsonStr) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(jsonStr)) {
            JSONObject jsonOject = JSONObject.parseObject(jsonStr);
            Set<Map.Entry<String, Object>> mapSet = jsonOject.entrySet();
            for (Map.Entry<String, Object> objectEntry : mapSet) {
                map.put(objectEntry.getKey(), objectEntry.getValue());
            }
        }
        return map;
    }


    public static Map<String, Object> formatMapWithJson(String jsonStr, Integer pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(jsonStr)) {
            JSONObject jsonOject = JSONObject.parseObject(jsonStr);
            Set<Map.Entry<String, Object>> mapSet = jsonOject.entrySet();
            for (Map.Entry<String, Object> objectEntry : mapSet) {
                map.put(objectEntry.getKey(), objectEntry.getValue());
            }
        }
        map.put("pageSize", pageSize);
        return map;
    }


    public static String getNewPageCondition(String oldPageCondition, Object[]... objects) {

        JSONObject jsonOject = null;
        if (StringUtils.isBlank(oldPageCondition)) {
            jsonOject = new JSONObject();
        } else {
            jsonOject = JSONObject.parseObject(oldPageCondition);
        }

        Object[] obj = null;
        for (int i = 0; i < objects.length; i++) {
            obj = objects[i];
            jsonOject.put(String.valueOf(obj[0]), obj[1]);
        }
        return jsonOject.toJSONString();
    }

    public static String getNoNextPageCondition(String oldPageCondition) {
        return getNewPageCondition(oldPageCondition, new Object[]{ApiPageCondition.HAS_NEXT_PAGE, false});
    }

    public static void main(String[] args) {
        String str = "{\"page1\":1}";
        System.out.println(getNewPageCondition(str, new Object[]{"page", false}, new String[]{"page3", "22dfdsf34"}));
        System.out.println(getNoNextPageCondition(""));
    }

}

package com.clever.common.util;

/**
 * Info: wt20150504
 * User: zhangxinglong@rui10.com
 * Date: 15/5/21
 * Time: 15:47
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public class ContantsUtil {

    public static int getTableTypeByPersons(int persons) {
        int type = 1;
        switch (persons) {
            case 1:
                type = 2;
                break;
            case 2:
                type = 2;
                break;
            case 3:
                type = 4;
                break;
            case 4:
                type = 4;
                break;
            case 5:
                type = 8;
                break;
            case 6:
                type = 8;
                break;
            case 7:
                type = 8;
                break;
            case 8:
                type = 8;
                break;
            case 9:
                type = 9;
                break;
            default:
                break;
        }
        return type;
    }
}

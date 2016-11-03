package com.clever.common.util;

import java.nio.charset.Charset;

/**
 * Info: 表情符号校验
 * User: zhangxinglong@rui10.com
 * Date: 15/6/17
 * Time: 11:09
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public class EmojiUtil {

    /**
     * 是否含有四字节的utf8
     *
     * @param s
     * @return true含有    false不含有
     */
    public static boolean has4Butf8(String s) {
        byte[] bs = s.getBytes(Charset.forName("utf8"));
        int size = bs.length;
        byte b;
        for (int i = 0; i < size; i++) {
            b = bs[i];
            if ((b & 0xF0) == 0xF0 && (i + 3 < size)) {
                if ((bs[i + 1] & 0x80) == 0x80 &&
                        (bs[i + 2] & 0x80) == 0x80 &&
                        (bs[i + 3] & 0x80) == 0x80) {
                    return true;
                }
            }
        }
        return false;
    }
}

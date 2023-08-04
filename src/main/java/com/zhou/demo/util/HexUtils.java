package com.zhou.demo.util;

import cn.hutool.core.util.HexUtil;

/**
 * 抽离分散在代码中的调用hutool的方法, 集中管理。
 *
 * @author laurence
 */
public class HexUtils {
    public static char[] encodeHex(byte[] data, boolean toLowerCase) {
        return HexUtil.encodeHex(data, toLowerCase);
    }

    public static String encodeHexStr(byte[] data, boolean toLowerCase) {
        return HexUtil.encodeHexStr(data, toLowerCase);
    }

    public static byte[] decodeHex(String hexStr) {
        return HexUtil.decodeHex(hexStr);
    }

    public static String encodeHexStr(String data) {
        return HexUtil.encodeHexStr(data);
    }

    public static String encodeHexStr(byte[] data) {
        return encodeHexStr(data, true);
    }
}

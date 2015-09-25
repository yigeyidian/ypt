package com.tanglang.ypt.utils;

/**
 * 网络数据路径工具类
 * Author： Administrator
 */
public class UrlUtils {

    public static String ROOT_PATH = "http://openapi.ypt.langma.cn/yws";
    public static String DB_ROOT_PATH = "http://openapi.db.39.net/app";

    public static String BRAND_PATH = DB_ROOT_PATH + "/GetDrugCompany?sign=9DFAAD5404FCB6168EA6840DCDFF39E5&app_key=app";

    /**
     * 版本信息路径
     *
     * @param opType
     * @return
     */
    public static String getVersionPath(int opType) {
        return ROOT_PATH + "/?json={\"op_type\":\"" +
                opType + "\",\"uid\":\"0\",\"c_ver\":\"40000\",\"c_type\":\"1\",\"cid\":\"1\"}";
    }

    public static String HOME_LAYOUT = ROOT_PATH + "/?json=%7B%22op_type%22%3A%221003%22%2C%22uid%22%3A%220%22%2C%22c_ver%22%3A%2240000%22%2C%22c_type%22%3A%221%22%2C%22cid%22%3A%221%22%7D";

}

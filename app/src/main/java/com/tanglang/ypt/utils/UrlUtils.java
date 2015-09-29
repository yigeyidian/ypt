package com.tanglang.ypt.utils;

/**
 * 网络数据路径工具类
 * Author： Administrator
 */
public class UrlUtils {

    public static String ROOT_PATH = "http://openapi.ypt.langma.cn/yws";
    public static String DB_ROOT_PATH = "http://openapi.db.39.net/app";

    /*品牌公司*/
    public static String BRAND_PATH = DB_ROOT_PATH + "/GetDrugCompany?sign=9DFAAD5404FCB6168EA6840DCDFF39E5&app_key=app";

    /*首页布局*/
    public static String HOME_LAYOUT = ROOT_PATH + "/?json=%7B%22op_type%22%3A%221003%22%2C%22uid%22%3A%220%22%2C%22c_ver%22%3A%2240000%22%2C%22c_type%22%3A%221%22%2C%22cid%22%3A%221%22%7D";

    public static String FAMILY_BOX = ROOT_PATH + "/?json=%7B%22op_type%22%3A%221006%22%2C%22uid%22%3A%220%22%2C%22c_ver%22%3A%2240000%22%2C%22activity_id%22%3A%221%22%2C%22c_type%22%3A%221%22%2C%22cid%22%3A%221%22%7D";
    public static String WEI_GAI = ROOT_PATH + "/?json=%7B%22op_type%22%3A%221006%22%2C%22uid%22%3A%220%22%2C%22c_ver%22%3A%2240000%22%2C%22activity_id%22%3A%222%22%2C%22c_type%22%3A%221%22%2C%22cid%22%3A%221%22%7D";
}

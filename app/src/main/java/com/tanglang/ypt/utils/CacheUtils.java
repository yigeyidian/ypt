package com.tanglang.ypt.utils;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 缓存工具类
 * Author： Administrator
 */
public class CacheUtils {
    /**
     * 缓存数据
     *
     * @param context
     * @param key
     * @param data
     */
    public static void saveCache(Context context, String key, String data) {
        String urlMd5 = MD5String(key);
        String cacheName = context.getCacheDir().getAbsolutePath() + File.separator + urlMd5 + ".txt";
        File cacheFile = new File(cacheName);
        FileWriter fw = null;
        try {
            fw = new FileWriter(cacheFile);
            fw.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取缓存数据
     *
     * @param context
     * @param key
     * @return
     */
    public static String getCache(Context context, String key) {
        String md5String = MD5String(key);
        String cacheName = context.getCacheDir().getAbsolutePath() + File.separator + md5String + ".txt";
        File cacheFile = new File(cacheName);
        if (!cacheFile.exists()) {
            return null;
        }
        FileReader fr = null;
        try {
            fr = new FileReader(cacheFile);
            StringBuffer sb = new StringBuffer();
            char[] buffer = new char[1024];
            int len = -1;
            while ((len = fr.read(buffer)) != -1) {
                sb.append(buffer, 0, len);
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    /**
     * 将字符串转换成MD5码
     *
     * @param str
     * @return
     */
    public static String MD5String(String str) {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] strs = digest.digest(str.getBytes());
            for (byte b : strs) {
                int i = b & 0xff;
                String hexStr = Integer.toHexString(i);
                if (hexStr.length() < 2) {
                    hexStr = "0" + hexStr;
                }
                sb.append(hexStr);
            }
        } catch (NoSuchAlgorithmException e) {
        }
        return sb.toString();
    }
}

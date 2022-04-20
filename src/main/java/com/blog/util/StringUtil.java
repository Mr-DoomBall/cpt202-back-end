package com.blog.util;

import org.apache.commons.lang3.ArrayUtils;

public class StringUtil {

    /**
     * 随机生成数字
     *
     * @param length 字符串长度
     * @return
     */
    public static String getRandomNum(int length) {
        String string = "1234567890"; //随机数字容器
        StringBuffer random = new StringBuffer();
        int len = string.length();
        for (int i = 0; i < length; i++) {
            random.append(string.charAt((int) Math.round(Math.random() * (len - 1))));
        }
        return random.toString();
    }

    /**
     * 随机生成字符串
     *
     * @param length 字符串长度
     * @return
     */
    public static String getRandomStr(int length) {
        String string = "abcdefghijklmnopqrstuvwxyz1234567890"; //随机字符串容器
        StringBuffer random = new StringBuffer();
        int len = string.length();
        for (int i = 0; i < length; i++) {
            random.append(string.charAt((int) Math.round(Math.random() * (len - 1))));
        }
        return random.toString();
    }

    /**
     * 判断文件名是否为图片
     */
    public static boolean isImage(String fileName) {
        String[] imgTypes = {"png", "jpg", "jpeg", "bmp", "gif", "pcx", "tga", "fpx", "cdr", "psd", "raw"};
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        if (ArrayUtils.contains(imgTypes, fileType)) {
            return true;
        } else {
            return false;
        }
    }

}

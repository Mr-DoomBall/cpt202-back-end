package com.blog.util;

import java.security.MessageDigest;

/**
 * 加密工具类
 */
public class SignUtil {
    /**
     * 对字符串进行MD5加密
     *
     * @param sourceStr
     * @return
     */
    public static String MD5(String sourceStr) {
        try {
            if (sourceStr == null) {
                return "";
            }
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes("UTF-8"));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密  
            return buf.toString();
            // 16位的加密  
            //return buf.toString().substring(8, 24);  
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

package com.wangyang.web.Util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    /***
     * MD5加码 生成32位md5码
     */
    public static String code(String inStr){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(inStr.getBytes());
            byte[] byteDigest = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0;offset<byteDigest.length;offset++){
                i = byteDigest[offset];
                if(i<0){
                    i += 256;
                }
                if(i<16){
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            //16位加密
//          return buf.toString().substring(8,24);
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args){
        System.out.println(code("123456"));
    }


}

package com.elec5619.utils;

import java.nio.charset.StandardCharsets;
import java.util.*;
import org.springframework.util.DigestUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RandomPwd {
    private static final int len = 10;

    public static String getPassWord() {
        // 如果len 小于等于3
        if (len <= 3) {
            throw new RuntimeException("密码的位数不能小于3位！");
        }
        //生成的随机数
        int i;
        //生成的密码的长度
        int count = 0;
        // 密码字典
        char[] str = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        // 大写字母密码字典
        List<Character> bigStrList = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
        Set<Character> bigStrSet = new HashSet<>(bigStrList);
        // 小写字母的密码字典
        List<Character> upperStrList = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
        Set<Character> upperStrSet = new HashSet<>(upperStrList);
        // 数字的密码字典
        List<Character> numStrList = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        Set<Character> numStrSet = new HashSet<>(numStrList);

        StringBuilder builder = new StringBuilder();
        Random r = new Random();
        boolean isContainBigChar = false;
        boolean isContainUpperChar = false;
        boolean isContainNumChar = false;
        while (count < len - 3) {
            //生成 0 ~ 密码字典-1之间的随机数
            i = r.nextInt(str.length);
            builder.append(str[i]);
            count++;
            if (!isContainBigChar && bigStrSet.contains(str[i])) {
                isContainBigChar = true;
            }
            if (!isContainUpperChar && upperStrSet.contains(str[i])) {
                isContainUpperChar = true;
            }
            if (!isContainNumChar && numStrSet.contains(str[i])) {
                isContainNumChar = true;
            }
        }
        // 如果不存在的，则加，确保一定会存在数字，大写字母，小写字母
        if (!isContainBigChar) {
            builder.append(bigStrList.get(r.nextInt(bigStrList.size())));
        }
        if (!isContainUpperChar) {
            builder.append(upperStrList.get(r.nextInt(upperStrList.size())));
        }
        if (!isContainNumChar) {
            builder.append(numStrList.get(r.nextInt(numStrList.size())));
        }
        while (builder.length() < len) {
            builder.append(str[r.nextInt(str.length)]);
        }
//        System.out.println();
//        String md5Password = getMD5(builder.toString());
        return builder.toString();
    }

    public static String getMD5(String input) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return "check jdk";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = input.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

//    public static void main(String[] args) {
//        System.out.println(getMD5("abc123"));
//    }
}

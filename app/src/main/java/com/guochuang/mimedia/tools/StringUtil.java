package com.guochuang.mimedia.tools;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.EditText;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static int toInt(String str) throws NumberFormatException {
        return Integer.parseInt(str);
    }

    public static double toDouble(String str) throws NumberFormatException {
        return Double.parseDouble(str);
    }

    /**
     * edittext 有无数据判断
     *
     * @param ets et集合
     * @return
     */
    public static EditText etNoData(ArrayList<EditText> ets) {
        for (int i = 0; i < ets.size(); i++) {
            if (ets.get(i).getText().toString().equals("")) {
                return ets.get(i);
            }
        }
        return null;
    }

    /**
     * 空判断
     */
    public static String isNull(Object o) {
        if (o != null && !o.toString().equals("")) {
            return o.toString();
        }
        return "";
    }

    /**
     * 把中文字符串转换为十六进制Unicode编码字符串
     */
    public static String stringToUnicode(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            if (ch > 255)
                str += "\\u" + Integer.toHexString(ch);
            else
                str += String.valueOf(s.charAt(i));
        }
        return str;
    }

    /**
     * 把十六进制Unicode编码字符串转换为中文字符串
     */
    public static String unicodeToString(String str) {
        if (!str.substring(0, 2).equals("")) {
            return str;
        }
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{2,4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }

    /**
     * 字典排序
     */
    public static String toSort(Map<String, String> paramsMap) {
        // 获取参数名并排序
        List<String> nameList = new ArrayList<>();
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            nameList.add(entry.getKey());
        }
        Collections.sort(nameList);

        // 拼装参数名和参数值
        StringBuilder result = new StringBuilder();
        for (String name : nameList) {
            Object value = paramsMap.get(name);
            result.append(name).append("=").append(value).append("&");
        }
        if (nameList.size()>0) {
            result.deleteCharAt(result.length() - 1);
        }
        return result.toString();
    }

    /**
     * uuid随机字符串
     */
    public static String getUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * MD5
     */
    @NonNull
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取散列值
     */
    public static HashMap<String, String> getSign(HashMap<String, String> hm) {
        String time = System.currentTimeMillis() + "";
        String nonce = StringUtil.getUuid();
        String sign = StringUtil.md5(StringUtil.toSort(hm) + "&" + time + "&" + nonce + "&" + JniUtil.getSign());
        HashMap<String, String> signHm = new HashMap<>();
        signHm.putAll(hm);
        signHm.put("h-sign", sign);
        signHm.put("h-time", time);
        signHm.put("h-nonce", nonce);
        return signHm;
    }

}

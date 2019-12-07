package com.xiehui.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;

/**
 * 字符串辅助类
 *
 * @author xiehui
 */
public class StringHelper {

    /**
     * 是否为空
     *
     * @param value 字符串值
     * @return 是否为空
     */
    public static boolean isEmpty(String value) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 截取字符串
     *
     * @param string 字符串
     * @param length 截取长度
     * @return 截取字符串
     */
    public static String truncate(String string, int length) {
        if (string != null && string.length() > length) {
            return string.substring(0, length);
        }
        return string;
    }

    /**
     * 转化列表
     *
     * @param list 列表
     * @return 字符串
     */
    public static String transList(List<Long> list) {
        // 检查空值
        if (list == null) {
            return null;
        }

        // 初始化
        StringBuilder sb = new StringBuilder();

        // 组装数据
        if (list != null && !list.isEmpty()) {
            Long[] array = list.toArray(new Long[0]);
            for (int i = 0; i < array.length; i++) {
                if (i != 0) {
                    sb.append(",");
                }
                sb.append(array[i]);
            }
        }

        // 返回数据
        return sb.toString();
    }

    /**
     * 解析列表
     *
     * @param string 字符串
     * @return 长整型列表
     */
    public static List<Long> parseList(String string) {
        // 初始化
        List<Long> list = new ArrayList<Long>();

        // 检查空值
        if (StringHelper.isEmpty(string)) {
            return list;
        }

        // 转化数据
        String[] items = string.split(",");
        if (items != null && items.length > 0) {
            for (String item : items) {
                list.add(Long.parseLong(item));
            }
        }

        // 返回列表
        return list;
    }

    /**
     * 划分字符串
     *
     * @param value 字符串值
     * @return 字符串列表
     */
    public static List<String> split(String value) {
        // 检查空值
        if (value == null) {
            return null;
        }

        // 划分字串
        String[] items = value.split(",");
        if (items == null || items.length == 0) {
            return null;
        }

        // 返回字串
        return Arrays.asList(items);
    }

    /**
     * 加密车牌号码
     *
     * @param plateNumber 车牌号码
     * @return 加密车牌号码
     */
    public static String encryptPlateNumber(String plateNumber) {
        // 检查空
        if (plateNumber == null || "".equals(plateNumber)) {
            return plateNumber;
        }

        // 初始化
        StringBuilder sb = new StringBuilder();

        // 加密数据
        if (plateNumber != null && plateNumber.length() > 4) {
            sb.append(plateNumber.substring(0, 1));
            int length = plateNumber.length() - 4;
            for (int i = 0; i < length; i++) {
                sb.append("*");
            }
            sb.append(plateNumber.substring(plateNumber.length() - 3));
        } else {
            sb.append(plateNumber);
        }

        // 返回数据
        return sb.toString();
    }

    /**
     * 加密电话号码
     *
     * @param phoneNumber 电话号码
     * @return 加密电话号码
     */
    public static String encryptPhoneNumber(String phoneNumber) {
        // 检查空
        if (phoneNumber == null || "".equals(phoneNumber)) {
            return phoneNumber;
        }

        // 初始化
        StringBuilder sb = new StringBuilder();

        // 加密数据
        if (phoneNumber != null && phoneNumber.length() > 7) {
            sb.append(phoneNumber.substring(0, 3));
            int length = phoneNumber.length() - 7;
            for (int i = 0; i < length; i++) {
                sb.append("*");
            }
            sb.append(phoneNumber.substring(phoneNumber.length() - 4));
        } else {
            sb.append(phoneNumber);
        }

        // 返回数据
        return sb.toString();
    }

    /**
     * 加密身份证号码141**********0090
     *
     * @param idCard 身份证号
     * @return 加密身份证号
     */
    public static String encryptIdCard(String idCard) {
        // 检查空
        if (idCard == null || "".equals(idCard)) {
            return idCard;
        }

        // 初始化
        StringBuilder sb = new StringBuilder();

        // idCard
        if (idCard != null && idCard.length() > 7) {
            sb.append(idCard.substring(0, 3));
            int length = idCard.length() - 7;
            for (int i = 0; i < length; i++) {
                sb.append("*");
            }
            sb.append(idCard.substring(idCard.length() - 4));
        } else {
            sb.append(idCard);
        }

        // 返回数据
        return sb.toString();
    }

    /**
     * 格式化金额--结尾不保留0
     *
     * @param money
     * @return
     */
    public static String parseMoneyNotRetain0(Long money) {
        // 验证金额
        if (money == null) {
            return null;
        }
        NumberFormat nf = NumberFormat.getInstance();
        return nf.format(Double.parseDouble(String.format("%.2f", money / 100.0)));
    }

    /**
     * 格式化金额
     *
     * @param money
     * @return
     */
    public static String parseMoney(Long money) {
        // 验证金额
        if (money == null) {
            return null;
        }

        return String.format("%.2f", money / 100.0);
    }

    /**
     * 格式化金额
     *
     * @param money
     * @return
     */
    public static String parseMoney(Long money, Double percent) {
        // 验证金额
        if (money == null || percent == null) {
            return null;
        }

        return String.format("%.2f", (money * percent) / 100.0);
    }

    /**
     * 分转换为元(取整)
     *
     * @param money Long类型金额(分)
     * @return 元(整数)
     */
    public static String parseMoney2YofInt(Long money) {
        // 验证金额
        if (money == null) {
            return null;
        }

        return String.format("%s", money / 100);
    }

    /**
     * 根据键值对填充字符串，如("hello ${name}",{name:"xiaoming"}) 输出：
     * 
     * @param content
     * @param map
     * @return
     */
    public static String renderString(String content, JSONObject map) {

        for (String entry : map.keySet()) {
            String regex = "\\$\\{" + entry + "\\}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(content);
            content = matcher.replaceAll(map.getString(entry));
        }
        return content;
    }

    public static String readFileContent(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * map转成xml格式的字符串
     *
     * @param param
     * @return
     */
    public static String GetMapToXML(Map<String, String> param) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        for (Map.Entry<String, String> entry : param.entrySet()) {
            sb.append("<" + entry.getKey() + ">");
            sb.append(entry.getValue());
            sb.append("</" + entry.getKey() + ">");
        }
        sb.append("</xml>");
        return sb.toString();
    }
}

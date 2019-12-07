package com.xiehui.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * Comments for RegexUtils.java
 */
public class RegexUtils {

    private static final String EMAIL_PATTERN_STRING =
        "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
    private static final String PHONE_PATTERN_STRING = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";
    private static final String CHINESE_PATTERN_STRING = "^[\\u4E00-\\u9FA5]+$";
    private static final String ENGLISH_PATTERN_STRING = "^[A-Za-z]+$";
    private static final String QQ_PATTERN_STRING = "^[1-9][0-9]{4,}$";
    private static final String NUMBER_PATTERN_STRING = "^\\d+$";
    private static final String CONTAINS_CHINESE_PATTERN_STRING = "[\u4e00-\u9fa5]";
    // 日期(yyyy-MM-dd或yyyy-MM-dd HH:mm:ss)
    private static final String DATE_PATTERN_STRING =
        "((^((1[8-9]\\d{2})|([2-9]\\d{3}))[-](10|12|0?[13578])[-](3[01]|[12][0-9]|0?[1-9])(\\s{1}([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9])?$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))[-](11|0?[469])[-](30|[12][0-9]|0?[1-9])(\\s{1}([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9])?$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))[-](0?2)[-](2[0-8]|1[0-9]|0?[1-9])(\\s{1}([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9])?$)|(^([2468][048]00)[-](0?2)[-](29)(\\s{1}([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9])?$)|(^([3579][26]00)[-](0?2)[-](29)(\\s{1}([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9])?$)|(^([1][89][0][48])[-](0?2)[-](29)(\\s{1}([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9])?$)|(^([2-9][0-9][0][48])[-](0?2)[-](29)(\\s{1}([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9])?$)|(^([1][89][2468][048])[-](0?2)[-](29)(\\s{1}([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9])?$)|(^([2-9][0-9][2468][048])[-](0?2)[-](29)(\\s{1}([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9])?$)|(^([1][89][13579][26])[-](0?2)[-](29)(\\s{1}([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9])?$)|(^([2-9][0-9][13579][26])[-](0?2)[-](29)(\\s{1}([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9])?$))";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_PATTERN_STRING);
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_PATTERN_STRING);
    private static final Pattern CHINESE_PATTERN = Pattern.compile(CHINESE_PATTERN_STRING);
    private static final Pattern ENGLISH_PATTERN = Pattern.compile(ENGLISH_PATTERN_STRING);
    private static final Pattern QQ_PATTERN = Pattern.compile(QQ_PATTERN_STRING);
    private static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_PATTERN_STRING);
    private static final Pattern CONTAINS_CHINESE_PATTERN = Pattern.compile(CONTAINS_CHINESE_PATTERN_STRING);
    private static final Pattern IPV4_PATTERN =
        Pattern.compile("(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}");
    private final static Pattern DATE_PATTERN = Pattern.compile(DATE_PATTERN_STRING);

    /*加密ID正则表达式*/
    public static final String SECURITY_ID_PATTTERN = "^[A-Za-z0-9\\_\\-\\~\\=\\/]+$";

    // 验证零和非零开头的整数
    public static final String POSITIVE_INTEGER_PATTTERN = "^(0|[1-9][0-9]*)$";

    public static boolean isEmail(String value) {
        if (StringUtils.isBlank(value)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(value).find();
    }

    public static boolean isNotEmail(String value) {
        return !isEmail(value);
    }

    public static boolean isPhone(String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        return PHONE_PATTERN.matcher(value).find();
    }

    public static boolean isNotPhone(String value) {
        return !isPhone(value);
    }

    public static boolean isChinese(String value) {
        if (StringUtils.isBlank(value)) {
            return false;
        }
        return CHINESE_PATTERN.matcher(value).find();
    }

    public static boolean isNotChinese(String value) {
        return !isChinese(value);
    }

    public static boolean isContainsChinese(String value) {
        Matcher matcher = CONTAINS_CHINESE_PATTERN.matcher(value);
        boolean flg = false;
        if (matcher.find()) {
            flg = true;
        }
        return flg;
    }

    public static boolean isNotContainsChinese(String value) {
        return !isContainsChinese(value);
    }

    public static boolean isEnglish(String value) {
        if (StringUtils.isBlank(value)) {
            return false;
        }
        return ENGLISH_PATTERN.matcher(value).find();
    }

    public static boolean isNotEnglish(String value) {
        return !isEnglish(value);
    }

    public static boolean isQQ(String value) {
        if (StringUtils.isBlank(value)) {
            return false;
        }
        return QQ_PATTERN.matcher(value).find();
    }

    public static boolean isNotQQ(String value) {
        return !isQQ(value);
    }

    public static boolean isNumber(String value) {
        if (StringUtils.isBlank(value)) {
            return false;
        }
        return NUMBER_PATTERN.matcher(value).find();
    }

    public static boolean isNotNumber(String value) {
        return !isNumber(value);
    }

    public static boolean isDate(String value) {
        if (StringUtils.isBlank(value)) {
            return false;
        }
        return DATE_PATTERN.matcher(value).find();
    }

    public static boolean isNotDate(String value) {
        return !isDate(value);
    }

    public static boolean isMatch(String desc, String regex) {
        if (StringUtils.isEmpty(desc) || StringUtils.isEmpty(regex)) {
            return false;
        }
        return Pattern.compile(regex).matcher(desc).find();
    }

    public static String removeRepeatChar(String source) {
        source = StringUtils.trimToEmpty(source);
        if (StringUtils.isNotBlank(source)) {
            return source.replaceAll("(?s)(.)(?=.*\\1)", "");
        } else {
            return source;
        }
    }

    public static int removeRepeatCharCount(String source) {
        source = StringUtils.trimToEmpty(source);
        if (StringUtils.isNotBlank(source)) {
            return StringUtils.length(source.replaceAll("(?s)(.)(?=.*\\1)", ""));
        } else {
            return 0;
        }
    }

    /**
     * 判断重复字符
     *
     * @param str 字符串
     * @param rate 重复字符占总字符数比率
     * @return 例: rate=0.2(或2/10) 10个字符中, 去重后字符数小于等于2 return true 反之 return false
     */
    public static boolean isRepeatChar(String str, float rate) {
        if (StringUtils.isBlank(str)) {
            return true;
        }

        int len = StringUtils.length(str);

        return 1.0 * RegexUtils.removeRepeatCharCount(str) / len < rate;
    }

    public static boolean isIpv4(String ip) {
        Matcher matcher = IPV4_PATTERN.matcher(ip);
        return matcher.matches();
    }

    public static void main(String[] args) {}

}

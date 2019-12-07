package com.xiehui.common.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.*;

/**
 * @Auther: MR.LUO
 * @Date: 2019
 * @Description: 联登贷款api接口
 */
public class HttpHelper {

    private static final String channel = "xxxx";
    private static final String secret = "900150983cd24fb0d6963f7d28e17f72";

    //获取请求参数
    public static Map<String, Object> getHttpMap(Map<String, Object> map,String channel,String secret) {
        Map<String, Object> paramMap = new LinkedHashMap<>();
        paramMap.put("channel", channel);
        paramMap.put("biz_enc", "0");
        paramMap.put("des_key", "");
        paramMap.put("timestamp", DateToTimestamp(new Date()));
        if (map.isEmpty()) {
            paramMap.put("biz_data", "");
        } else {
            paramMap.put("biz_data", JSON.toJSONString(map));
        }
        String signStr = getSignStr(paramMap, secret);
        //System.out.println("signStr:" + signStr);
        paramMap.put("sign", signStr);
        return paramMap;
    }

    //获取签名参数
    public static String getSignStr(Map<String, Object> params, String secret) {
        List keys = new ArrayList(params.keySet());
        Collections.sort(keys);
        String str = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = String.valueOf(keys.get(i));
            String value = String.valueOf(params.get(key));
            str += value;
        }
        str += secret;
        //System.out.println(String.format("签名串： %s\n", str));
        String sign = sha1(str);
        //System.out.println(String.format("签名： %s\n", sign));
        return org.apache.commons.codec.binary.Base64.encodeBase64String(sign.getBytes());
    }

    /*sha1加密*/
    public static String sha1(String decript) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    //获取时间戳
    public static Integer DateToTimestamp(Date time) {
        Timestamp ts = new Timestamp(time.getTime());
        return (int) ((ts.getTime()) / 1000);
    }

    //httpPost请求
    public static String postJson(String url, String json) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        httppost.addHeader(HTTP.CONTENT_TYPE, "application/json");
        try {
            StringEntity se = new StringEntity(json);
            se.setContentType("text/json");
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httppost.setEntity(se);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity, "UTF-8");
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static void main(String[] args) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("mobile", "17814341111");
        map.put("product_id", 119);
        Map<String, Object> param = HttpHelper.getHttpMap(map,channel,secret);
        System.out.println("param:" + param);
        String query = HttpHelper.postJson("http://api.minzhiglobal.com.cn/test/api/loan/auth-url", JSON.toJSONString(param));

        System.out.println(query);
    }
}

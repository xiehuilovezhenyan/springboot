package com.xiehui.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 请求辅助类
 *
 * @author cychen
 */
@Slf4j
public class RequestHelper {

    /**
     * 构造函数
     */
    private RequestHelper() {}

    /**
     * 获取URI
     *
     * @param request HTTP请求
     * @return URI
     */
    public static String getURI(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        if (StringUtils.isNotBlank(contextPath)) {
            contextPath = contextPath + "/";
        } else {
            contextPath = "/";
        }
        if (StringUtils.startsWith(uri, contextPath)) {
            return uri.substring(contextPath.length());
        }
        return uri;
    }

    /**
     * 获取数据
     *
     * @param request HTTP请求
     * @return data
     */
    public static String getData(HttpServletRequest request) {
        // 初始化
        String data = null;
        InputStream inStream = null;
        ByteArrayOutputStream outStream = null;

        try {
            inStream = request.getInputStream();
            int _buffer_size = 1024;
            if (inStream != null) {
                outStream = new ByteArrayOutputStream();
                byte[] tempBytes = new byte[_buffer_size];
                int count = -1;
                while ((count = inStream.read(tempBytes, 0, _buffer_size)) != -1) {
                    outStream.write(tempBytes, 0, count);
                }
                tempBytes = null;
                outStream.flush();
                // 将流转换成字符串
                data = new String(outStream.toByteArray(), "UTF-8");
            }
        } catch (IOException e) {
            log.error("获取数据失败", e);
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    log.error("获取数据失败", e);
                }
            }
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    log.error("获取数据失败", e);
                }
            }
        }

        // 返回
        return data;
    }

}

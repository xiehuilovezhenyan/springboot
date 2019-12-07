package com.xiehui.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.xiehui.common.core.annotation.BodyReaderHttpServletRequestWrapper;
import com.xiehui.common.core.xss.XssHttpServletRequestWrapper;


public abstract class WebUtils {

    public static final String JSON_SUFFIX = ".json";

    public static final String REQUEST_METHOD_POST = "post";

    public static final String REQUEST_METHOD_GET = "get";

    public static String getParaStr(HttpServletRequest request) {
        StringBuffer para = new StringBuffer("");
        try {
            // 获取原始请求
            boolean isGetReq = "GET".equalsIgnoreCase(request.getMethod());
            HttpServletRequest servletRequest = request;
            if (!isGetReq) {
                servletRequest = new BodyReaderHttpServletRequestWrapper(request);
                String bodyContent = HttpBodyHelper.getBodyContent(servletRequest);
                para.append(bodyContent);
            } else {
                if (servletRequest instanceof XssHttpServletRequestWrapper) {
                    ServletRequest tempRequest = ((XssHttpServletRequestWrapper)servletRequest).getRequest();
                    if (tempRequest instanceof HttpServletRequest) {
                        servletRequest = (HttpServletRequest)tempRequest;
                    }
                }
                Enumeration parameterNames = servletRequest.getParameterNames();
                while (parameterNames.hasMoreElements()) {
                    String key = (String)parameterNames.nextElement();
                    String value = Arrays.toString(servletRequest.getParameterValues(key));
                    if (para.length() == 0) {
                        para.append(key + "=" + value);
                    } else {
                        para.append("|" + key + "=" + value);
                    }
                }
            }
        } catch (Exception e) {
            para.append("获取参数异常");
        }

        return para.toString();
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = "";
        Set<String> ipSet = getIpAddrSet(request);
        if (!ipSet.isEmpty()) {
            ip = ipSet.iterator().next();
        }
        return ip;
    }

    private static final ThreadLocal<String> requestIdThreadLocal = new ThreadLocal<>();

    public static String getRequestId() {
        String requestId = requestIdThreadLocal.get();
        if (StringUtils.isBlank(requestId)) {
            requestId = UUID.randomUUID().toString();
            requestIdThreadLocal.set(requestId);
        }
        return requestId;
    }

    private static boolean ignoreIps(String ip) {
        if (StringUtils.isBlank(ip) || !RegexUtils.isIpv4(ip)) {
            return true;
        } else {
            return false;
        }
    }

    // 获取非内网的真实和代理的所有ip
    public static Set<String> getIpAddrSet(HttpServletRequest request) {
        Set<String> ipSet = new HashSet<String>();
        Set<String> sourceIps = new HashSet<String>();

        sourceIps.add(request.getHeader("RemoteIp"));
        sourceIps.add(request.getHeader("X-Real-IP"));
        sourceIps.add(request.getHeader("x-forwarded-for"));
        sourceIps.add(request.getHeader("Proxy-Client-IP"));
        sourceIps.add(request.getHeader("WL-Proxy-Client-IP"));

        for (String sourceIp : sourceIps) {
            if (StringUtils.isBlank(sourceIp)) {
                continue;
            }
            String[] ipArr = sourceIp.split("[ |,|\\|;]");
            if (ipArr != null && ipArr.length > 0) {
                for (int i = 0; i < ipArr.length; i++) {
                    String ip = StringUtils.trim(ipArr[i]);
                    if (ignoreIps(ip) && IpUtil.isOuter(ip)) {
                        ipSet.add(ip);
                    }
                }
            }
        }
        if (ipSet.size() == 0) {
            ipSet.add(request.getRemoteAddr());
        }
        return ipSet;
    }

    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    @SuppressWarnings("unchecked")
    public static String getRequestPath(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder(request.getRequestURI());
        Enumeration<String> enumeration = request.getParameterNames();
        if (enumeration.hasMoreElements()) {
            sb.append("?");
        }
        while (enumeration.hasMoreElements()) {
            Object object = enumeration.nextElement();
            sb.append(object);
            sb.append("=");
            sb.append(request.getParameter(object.toString()));
            sb.append("&");
        }
        String requesturi = "";
        String contextPath = request.getContextPath();
        if (sb.indexOf("&") != -1) {
            requesturi = sb.substring(0, sb.lastIndexOf("&"));
        } else {
            requesturi = sb.toString();
        }
        requesturi = requesturi.substring(requesturi.indexOf(contextPath) + contextPath.length());
        return requesturi;
    }

    /**
     * json数据返回
     *
     * @param response
     * @param str
     */
    public static void printWrite(HttpServletResponse response, String str) {
        if (StringUtils.isBlank(str)) {
            throw new RuntimeException("return str is null");
        }
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }

    /**
     * 是否为post请求
     *
     * @param request
     * @return
     */
    public static boolean isPost(HttpServletRequest request) {
        return StringUtils.equalsIgnoreCase(REQUEST_METHOD_POST, request.getMethod());
    }

    /**
     * 是否为get请求
     *
     * @param request
     * @return
     */
    public static boolean isGet(HttpServletRequest request) {
        return StringUtils.equalsIgnoreCase(REQUEST_METHOD_GET, request.getMethod());
    }

    /**
     * 是否为json请求
     *
     * @param request
     * @return
     */
    public static boolean isJsonRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return StringUtils.endsWith(uri, JSON_SUFFIX);
    }

    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With")) ? true : false;
    }
}

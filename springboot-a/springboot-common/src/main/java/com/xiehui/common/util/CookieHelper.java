package com.xiehui.common.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieHelper {

	public static String getCookieValue(String key, HttpServletRequest req) {
		String str = null;
		Cookie cookies[] = req.getCookies();
		if (null == cookies)
			return null;
		for (int i = 0; i < cookies.length && str == null; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals(key)) {
				return cookie.getValue();
			}
		}
		return null;
	}

	public static void saveCookie(HttpServletResponse response, String key, String value, String domain) {
		saveCookie(response, key, value, -1, domain);
	}

	public static void saveCookie(HttpServletResponse response, String key, String value, int second, String domain) {
		value = StringUtils.remove(value, '\n');
		value = StringUtils.remove(value, '\r');
		Cookie cookie = new Cookie(key, value);
		cookie.setPath("/");
		cookie.setMaxAge(second);
		if (StringUtils.isNotBlank(domain)) {
			if (!StringUtils.startsWith(domain, ".")) {
				domain = "." + domain;
			}
			cookie.setDomain(domain);
		}
		response.addCookie(cookie);
	}

	public static void clearCookie(HttpServletResponse response, String key, String domain) {
		Cookie cookie = new Cookie(key, null);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		if (StringUtils.isNotBlank(domain)) {
			cookie.setDomain(domain);
		}
		response.addCookie(cookie);
	}
}

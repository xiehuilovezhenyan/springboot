package com.xiehui.common.core.annotation;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.xiehui.common.util.HttpBodyHelper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * 解决二次读取Body的问题
 */
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

	private byte[] body;

	public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		/*
		 * Enumeration e = request.getHeaderNames(); while (e.hasMoreElements()) {
		 * String name = (String)e.nextElement(); String value =
		 * request.getHeader(name);
		 * 
		 * }
		 */
		body = HttpBodyHelper.getBodyContent(request).getBytes(Charset.forName("UTF-8"));
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {

		final ByteArrayInputStream bais = new ByteArrayInputStream(body);

		return new ServletInputStream() {
			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setReadListener(ReadListener readListener) {

			}

			@Override
			public int read() throws IOException {
				return bais.read();
			}
		};
	}

	/*
	 * public void setInputStream(byte[] body) { this.body = body; }
	 * 
	 * @Override public String getHeader(String name) { return
	 * super.getHeader(name); }
	 * 
	 * @Override public Enumeration<String> getHeaderNames() { return
	 * super.getHeaderNames(); }
	 * 
	 * @Override public Enumeration<String> getHeaders(String name) { return
	 * super.getHeaders(name); }
	 */

}

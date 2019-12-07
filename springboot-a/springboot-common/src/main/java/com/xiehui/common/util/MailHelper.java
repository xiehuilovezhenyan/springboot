package com.xiehui.common.util;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.*;

import com.xiehui.common.core.exception.CustomException;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;

/**
 * 邮件工具类
 *
 * @author liudo
 * @date 2018/09/21
 */
public class MailHelper {
	// 邮件发送协议
	private final static String PROTOCOL = "smtp";
	// SMTP邮件服务器
	private final static String HOST = "smtp.mxhichina.com";
	// SMTP邮件服务器默认端口
	private final static String PORT = "465";
	// 是否要求身份认证
	private final static String IS_AUTH = "true";
	// 发送html content类型设置
	private static String CONTENT_TYPE_HTML = "text/html;charset=utf-8";
	// MIME子类型为"related"
	private static String MIME_MULTI_PART_RELATED = "related";
	// MIME子类型为"mixed"
	private static String MIME_MULTI_PART_MIXED = "mixed";
	// MIME子类型为"alternative"
	private static String MIME_MULTI_PART_ALTERNATIVE = "alternative";

	/**
	 * 获取session
	 *
	 * @param from
	 * @param passrowd
	 * @return
	 */
	private static Session getSession(String from, String passrowd) {
		// 初始化连接邮件服务器的会话信息
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", PROTOCOL);
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.port", PORT);
		props.put("mail.smtp.auth", IS_AUTH);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		Session session = Session.getDefaultInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, passrowd);
			}
		});
		return session;
	}

	/**
	 * 设置收件人
	 *
	 * @param tos
	 * @throws MessagingException
	 */
	private static void setRecipients(MimeMessage message, RecipientType type, String... tos)
			throws MessagingException {
		InternetAddress[] addresses = new InternetAddress[tos.length];
		for (int i = 0; i < tos.length; i++) {
			addresses[i] = new InternetAddress(tos[i]);
		}
		message.setRecipients(type, addresses);
	}

	/**
	 * 发送简单的文本邮件
	 *
	 * @param from
	 * @param passrowd
	 * @param title
	 * @param text
	 * @param ccs
	 * @param tos
	 * @return
	 * @throws CustomException
	 */
	public static boolean sendTextEmail(String from, String passrowd, String title, String text, String[] ccs,
			String... tos) throws CustomException {
		try {
			Session session = getSession(from, passrowd);
			// 创建MimeMessage实例对象
			MimeMessage message = new MimeMessage(session);
			// 设置发件人
			message.setFrom(new InternetAddress(from));
			// 设置邮件主题
			message.setSubject(title);
			// 设置收件人
			setRecipients(message, RecipientType.TO, tos);
			// 设置抄送人
			setRecipients(message, RecipientType.CC, ccs);
			// 设置发送时间
			message.setSentDate(new Date());
			// 设置纯文本内容为邮件正文
			message.setText(text);
			// 保存并生成最终的邮件内容
			message.saveChanges();
			// 获得Transport实例对象
			Transport transport = session.getTransport();
			// 将message对象传递给transport对象，将邮件发送出去
			transport.sendMessage(message, message.getAllRecipients());
			// 关闭连接
			transport.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("发送邮件异常" + e);
		}
	}

	/**
	 * 发送简单的html邮件
	 *
	 * @param from
	 * @param passrowd
	 * @param title
	 * @param html
	 * @param ccs
	 * @param tos
	 * @return
	 * @throws CustomException
	 */
	public static boolean sendHtmlEmail(String from, String passrowd, String title, String html, String[] ccs,
			String... tos) throws CustomException {
		try {
			Session session = getSession(from, passrowd);
			// 创建MimeMessage实例对象
			MimeMessage message = new MimeMessage(session);
			// 设置邮件主题
			message.setSubject(title);
			// 设置发送人
			message.setFrom(new InternetAddress(from));
			// 设置发送时间
			message.setSentDate(new Date());
			// 设置收件人
			setRecipients(message, RecipientType.TO, tos);
			// 设置抄送人
			setRecipients(message, RecipientType.CC, ccs);
			// 设置html内容为邮件正文，指定MIME类型为text/html类型，并指定字符编码为gbk
			message.setContent(html, CONTENT_TYPE_HTML);
			// 保存并生成最终的邮件内容
			message.saveChanges();
			// 发送邮件
			Transport.send(message);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("发送邮件异常" + e);
		}
	}

	/**
	 * 添加附件
	 *
	 * @param mailContent
	 * @param attachmentPaths
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	private static void setAttachments(MimeMultipart mailContent, String[] attachmentPaths)
			throws MessagingException, UnsupportedEncodingException {
		for (String filePath : attachmentPaths) {
			MimeBodyPart attach = new MimeBodyPart();
			DataHandler dh = new DataHandler(new FileDataSource(filePath));
			String name = dh.getName();
			attach.setDataHandler(dh);
			attach.setFileName(MimeUtility.encodeText(name));// 设置中文编码
			mailContent.addBodyPart(attach);
		}
	}

	/**
	 * 发送带内嵌图片、附件、多收件人(显示邮箱姓名)、邮件优先级、阅读回执的完整的HTML邮件
	 *
	 * @param from
	 * @param passrowd
	 * @param title
	 * @param html
	 * @param attachmentPaths
	 * @param ccs             抄送
	 * @param tos             接收人
	 * @return
	 * @throws CustomException
	 */
	public static boolean sendMultipleEmail(String from, String passrowd, String title, String html,
			String[] attachmentPaths, String[] ccs, String... tos) throws CustomException {
		try {
			Session session = getSession(from, passrowd);
			// 创建MimeMessage实例对象
			MimeMessage message = new MimeMessage(session);
			// 设置邮件主题
			message.setSubject(title);
			// 设置发送人
			message.setFrom(new InternetAddress(from));
			// 设置发送时间
			message.setSentDate(new Date());
			// 设置收件人
			setRecipients(message, RecipientType.TO, tos);
			// 设置抄送人
			if (Objects.nonNull(ccs))
				setRecipients(message, RecipientType.CC, ccs);
			// 创建一个MIME子类型为"mixed"的MimeMultipart对象，表示这是一封混合组合类型的邮件MIME_MULTI_PART_MIXED
			MimeMultipart mailContent = new MimeMultipart(MIME_MULTI_PART_MIXED);
			message.setContent(mailContent);
			// 设置附件
			if (null != attachmentPaths && attachmentPaths.length > 0) {
				setAttachments(mailContent, attachmentPaths);
			}
			// 内容
			MimeBodyPart mailBody = new MimeBodyPart();
			mailContent.addBodyPart(mailBody);
			// 邮件正文(内嵌图片+html文本)
			MimeMultipart body = new MimeMultipart(MIME_MULTI_PART_RELATED); // 邮件正文也是一个组合体,需要指明组合关系
			mailBody.setContent(body);
			MimeBodyPart htmlPart = new MimeBodyPart();
			body.addBodyPart(htmlPart);
			// html邮件内容
			MimeMultipart htmlMultipart = new MimeMultipart(MIME_MULTI_PART_ALTERNATIVE);
			htmlPart.setContent(htmlMultipart);
			MimeBodyPart htmlContent = new MimeBodyPart();
			htmlContent.setContent(html, CONTENT_TYPE_HTML);
			htmlMultipart.addBodyPart(htmlContent);
			// 保存并生成最终的邮件内容
			message.saveChanges();
			// 发送邮件
			Transport.send(message);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("发送邮件异常" + e);
		}
	}
}

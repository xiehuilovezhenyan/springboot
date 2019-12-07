package com.xiehui.common.util;

import javax.imageio.ImageIO;

import com.xiehui.common.core.exception.CustomException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * 验证码辅助类
 *
 * @author cychen
 */
public class CaptchaHelper {

	/**
	 * 字符数组
	 */
	private static final char[] CODE_ARRAY = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	/**
	 * 构造函数
	 */
	private CaptchaHelper() {
	}

	/**
	 * 获取验证码
	 *
	 * @param count 验证码个数
	 * @return 验证码
	 */
	public static String getCaptcha(int count) {
		// 初始化
		Random random = new Random();
		StringBuilder sb = new StringBuilder();

		// 组装数据
		for (int i = 0; i < count; i++) {
			sb.append(CODE_ARRAY[random.nextInt(CODE_ARRAY.length)]);
		}

		// 返回数据
		return sb.toString();
	}

	/**
	 * 获取验证图片
	 *
	 * @param verifyCode 验证码
	 * @return 验证图片
	 * @throws CustomException kakaka异常
	 */
	public static byte[] getCaptchaImage(String verifyCode) throws CustomException {
		// 初始化
		int width = 100;
		int height = 30;
		int count = verifyCode.length();
		int offset = width / count;
		int fontHeight = height - 2;
		int codeY = height - 4;

		// 绘制图片
		Random random = new Random();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Consolas", Font.PLAIN, fontHeight));
		g.setColor(Color.BLACK);
		for (int i = 0; i < 10; i++) {
			int x1 = random.nextInt(width);
			int y1 = random.nextInt(height);
			int x2 = random.nextInt(width);
			int y2 = random.nextInt(height);
			int red = random.nextInt(255);
			int green = random.nextInt(255);
			int blue = random.nextInt(255);
			if (x1 > width) {
				x1 = width;
			}
			if (x2 > width) {
				x2 = width;
			}
			if (y1 > height) {
				y1 = height;
			}
			if (y2 > height) {
				y2 = height;
			}
			g.setColor(new Color(red, green, blue));
			g.drawLine(x1, y1, x2, y2);
		}
		for (int i = 0; i < count; i++) {
			int red = random.nextInt(250);
			int green = random.nextInt(250);
			int blue = random.nextInt(250);
			String code = String.valueOf(verifyCode.charAt(i));
			g.setColor(new Color(red, green, blue));
			g.drawString(code, i * offset + 2, codeY);
		}
		g.dispose();

		// 写入缓存
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "jpeg", baos);
		} catch (IOException e) {
			throw new CustomException("写入图片异常", e);
		}

		// 返回字节
		return baos.toByteArray();
	}

	/**
	 * 主函数
	 *
	 * @param args 参数数组
	 * @throws CustomException kakaka异常
	 * @throws IOException     IO异常
	 */
	public static void main(String[] args) throws CustomException, IOException {
		byte[] bytes = getCaptchaImage(getCaptcha(4));
		FileOutputStream fos = new FileOutputStream("D:/a.jpg");
		fos.write(bytes);
		fos.close();
	}

}

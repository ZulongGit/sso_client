package com.krm.common.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import com.krm.common.utils.FileUtils;
/**
 * 验证码生成器
 * @author dsna
 *
 */
public class ValidateCode {
	// 图片的宽度。
	private static int width = 160;
	// 图片的高度。
	private static int height = 40;
	// 验证码字符个数
	private static int codeCount = 5;
	// 验证码干扰线数
	private static int lineCount = 150;

	private static char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N',  'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z',  '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	public ValidateCode(OutputStream out) throws IOException {
		createCode(out);
	}

	/**
	 * 
	 * @param width 图片宽
	 * @param height 图片高
	 * @throws IOException 
	 */
	public ValidateCode(int width,int height, OutputStream out) throws IOException {
		ValidateCode.width=width;
		ValidateCode.height=height;
		createCode(out);
	}
	/**
	 * 
	 * @param width 图片宽
	 * @param height 图片高
	 * @param codeCount 字符个数
	 * @param lineCount 干扰线条数
	 * @throws IOException 
	 */
	public ValidateCode(int width,int height,int codeCount,int lineCount, OutputStream out) throws IOException {
		ValidateCode.width=width;
		ValidateCode.height=height;
		ValidateCode.codeCount=codeCount;
		ValidateCode.lineCount=lineCount;
		createCode(out);
	}
	
	public static String createCode(OutputStream out) throws IOException {
		// 验证码图片Buffer
		BufferedImage buffImg = null;
		int x = 0,fontHeight=0,codeY=0;
		int red = 0, green = 0, blue = 0;
		
		x = width / (codeCount +2);//每个字符的宽度
		fontHeight = height - 2;//字体的高度
		codeY = height - 4;
		
		// 图像buffer
		buffImg = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();
		// 生成随机数
		Random random = new Random();
		// 将图像填充为白色
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		// 创建字体
		Font font = new Font("Arial",Font.PLAIN, fontHeight);
		g.setFont(font);
		
		for (int i = 0; i < lineCount; i++) {
			int xs = random.nextInt(width);
			int ys = random.nextInt(height);
			int xe = xs+random.nextInt(width/8);
			int ye = ys+random.nextInt(height/8);
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);
			g.setColor(new Color(red, green, blue));
			g.drawLine(xs, ys, xe, ye);
		}
		
		// randomCode记录随机产生的验证码
		StringBuffer randomCode = new StringBuffer();
		// 随机产生codeCount个字符的验证码。
		for (int i = 0; i < codeCount; i++) {
			String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
			// 产生随机的颜色值，让输出的每个字符的颜色值都将不同。
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);
			g.setColor(new Color(red, green, blue));
			g.drawString(strRand, (i + 1) * x, codeY);
			// 将产生的四个随机数组合在一起。
			randomCode.append(strRand);
		}
		ImageIO.write(buffImg, "png", out);
		out.close();
		// 将四位数字的验证码保存到Session中。
		return randomCode.toString();		
	}
	
	public static void write(String path, BufferedImage buffImg) throws IOException {
		File file = new File(path);
		if(!file.exists()){
			FileUtils.createFile(path);
		}
		OutputStream sos = new FileOutputStream(path);
		ImageIO.write(buffImg, "png", sos);
		sos.close();
	}
	
}

package com.krm.common.utils.encrypt;

import java.io.File;

import com.krm.crypto.cryptohandle;
import com.krm.crypto.utils.fileutil;

/**
 * 数据源配置文件加密方法
 * @author Parker
 *
 */
public class EncryptUtil
{
	public static void main(String[] args) throws Exception
	{
		cryptohandle p = new cryptohandle("sm2");
		String pubk = "04F6E0C3345AE42B51E06BF50B98834988D54EBC7460FE135A48171BC0629EAE205EEDE253A530608178A98F1E19BB737302813BA39ED3FA3C51639D7A20C7391A";

		String path="这里请填上resource.properties文件的绝对路径（注意备份，此程序执行完会覆盖明文版配置文件）";
		byte[] a = fileutil.filetoByte(path+"resources.properties");
		byte[] b = p.encrypt(pubk, a);
		fileutil.bytetoFile(path+"temp.properties", b);
		System.out.println("文件加密完毕。");
		fileutil.deleteFile(path+"resources.properties");
		File file = new File(path+"temp.properties");
		file.renameTo(new File(path+"resources.properties"));
	}
}

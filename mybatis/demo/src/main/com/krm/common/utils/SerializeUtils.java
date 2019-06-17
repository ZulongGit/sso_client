package com.krm.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by winkie on 15/3/6.
 */
public class SerializeUtils {

    private static Logger logger = LoggerFactory.getLogger(SerializeUtils.class);

    /**
     * 把对象写入到文件中
     * @param obj
     * @param fileFullName
     */
    public static void writeObject2File(Object obj, String fileFullName) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileFullName));
            out.writeObject(obj);
            out.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件中读取对象
     * @param fileFullName
     * @return
     */
    public static Object readObjectFromFile(String fileFullName) {
        Object obj = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileFullName));
            obj = in.readObject();
            in.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 把字节数组转换为对象
     * @param bytes
     * @return
     */
    public static Object readObjectFromBytes(byte[] bytes) {
        Object obj = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes));
            obj = in.readObject();
            in.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 把对象转换为字节数组
     * @param obj
     * @return
     */
    public static byte[] writeObject2Bytes(Object obj) {
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(bytesOut);
            out.writeObject(obj);
            out.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return bytesOut.toByteArray();
    }

    /**
     * 把对象转换为字符串，使用base64处理
     * @param obj
     * @return
     */
    public static String converObject2String(Object obj) {
        byte[] objBytes = writeObject2Bytes(obj);
        return Base64.encodeToString(objBytes);
    }

    /**
     * 把字符串转化为对象 使用base64处理
     * @param objStr
     * @return
     */
    public static Object converString2Object(String objStr) {
        byte[] objBytes = Base64.decode(objStr);
        return readObjectFromBytes(objBytes);
    }
    
    public static void main(String[] args) {
		
    	System.out.println(converObject2String("SELECT ID as key,NAME as value FROM SYS_organ  "));
    	System.out.println(converString2Object("rO0ABXQATFNFTEVDVCB0LlBST19OQU1FIExBQkxFLCB0LklEIFZBTFVFIEZST00gR0VOX1BST0pFQ1RTIHQgd2hlcmUgdC5kZWxfZmxhZyA9IDA="));
	}
}

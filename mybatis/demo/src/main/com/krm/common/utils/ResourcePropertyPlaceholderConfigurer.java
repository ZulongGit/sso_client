package com.krm.common.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

import com.krm.crypto.cryptohandle;

public class ResourcePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer{
	private Resource[] locations;
	private String fileEncoding;

	public void setLocations(Resource[] locations){
		this.locations = locations;
	}

	public void setFileEncoding(String fileEncoding)
	{
		this.fileEncoding = fileEncoding;
	}

	public void loadProperties(Properties props) throws IOException{
		if (this.locations != null){
			PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
			cryptohandle p;
			try
			{
				p = new cryptohandle("sm2");
				String prik = "3690655E33D5EA3D9A4AE1A1ADD766FDEA045CDEAA43A9206FB8C430CEFE0D94";

				for (int i = 0; i < this.locations.length; i++){
					Resource location = this.locations[i];
					if (logger.isInfoEnabled()){
						logger.info("Loading properties file from " + location);
					}
					InputStream is = null;
					try{
						is = location.getInputStream();
						int count = 0;  
				        while (count == 0) {  
				            count = is.available();  
				        }  
				        byte[] b = new byte[count];  
				        is.read(b);
				        logger.info("解密文件读取完毕。");
						byte[] d = p.decrypt(prik, b);
						logger.info("文件解密完毕。");
						InputStream in = new ByteArrayInputStream(d);
						if (fileEncoding != null){
							propertiesPersister.load(props,new InputStreamReader(in, fileEncoding));
							in.close();
						} else{
							propertiesPersister.load(props, in);
							in.close();
						}
					} finally{
						if (is != null){
							is.close();
						}
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}

			
		}
	}
}

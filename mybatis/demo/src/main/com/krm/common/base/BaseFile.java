package com.krm.common.base;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author Parker
 *
 */
public class BaseFile implements Serializable{

	private static final long serialVersionUID = 8204340062516970076L;
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}  

	
}

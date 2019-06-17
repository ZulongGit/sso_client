package com.krm.common.beetl.function;

import org.springframework.stereotype.Component;

import com.krm.common.utils.FileUtils;

@Component
public class FileFunction {
	
	public String formatFileSize(Long size){
		if(size == null){
			return "未知";
		}
		return FileUtils.getHumanSize(size);
	}
}

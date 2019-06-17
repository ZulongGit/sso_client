package com.krm.web.tools.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Maps;
import com.krm.common.base.BaseController;
import com.krm.common.constant.SysConstant;
import com.krm.common.utils.FileUtils;
import com.krm.ueditor.ActionEnter;

@Controller
@RequestMapping("tools/richText")
public class RichTextController extends BaseController {

	public static final String BASE_URL = "tools/richText";
	private static final String BASE_PATH = "tools/richText/";
	public static final String IMG_URL = "/file/view/image?fileName=";
	
	@Override
	protected String getBaseUrl() {
		return BASE_URL;
	}

	@Override
	protected String getBasePath() {
		return BASE_PATH;
	}

	@RequestMapping("initConfig")
    public void initConfig(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	 request.setCharacterEncoding( "utf-8" );
         response.setHeader("Content-Type" , "text/html");
         String roolPath = request.getSession().getServletContext().getRealPath("/");
         String configStr = new ActionEnter(request, roolPath).exec();
         response.getWriter().write(configStr);
    }
    
    @RequestMapping(value = "upload/{mode}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> upload(@RequestParam Map<String,Object> params, @PathVariable String mode, 
    		@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
    	logger.info("开始上传{}","富文本文件");
    	String targetDirectory = SysConstant.getValue("upload.rootPath") + File.separator + mode;
		String fileName = null;
		if(file.getSize() != 0){
			fileName = file.getOriginalFilename();
			int last = fileName.lastIndexOf(".");  
			String type = fileName.substring(last);  
			File target = new File(targetDirectory, fileName);
			// 如果文件已经存在，则删除原有文件
			if (target.exists()) {
				target.delete();
			}
			// 复制file对象，实现上传
			try {
				FileUtils.copyInputStreamToFile(file.getInputStream(), target);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Map<String,Object> result = Maps.newHashMap();
			result.put("state", "SUCCESS");
			result.put("original", file.getOriginalFilename());
			result.put("size", file.getSize());
			result.put("title", fileName);
			result.put("type", type);
			result.put("url", "/file/view/"+mode+"?fileName=" + fileName);
			logger.info("文件上传成功");
			return result;
//			if(fileType.equals(".jpg") || fileType.equals(".jpeg") || fileType.equals(".png")){
//			}
		}
		return null;
    }
}

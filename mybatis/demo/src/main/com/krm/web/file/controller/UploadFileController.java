package com.krm.web.file.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.krm.common.base.BaseController;
import com.krm.common.base.Result;
import com.krm.common.constant.SysConstant;
import com.krm.common.utils.FileUtils;
import com.krm.common.utils.MediaTransferUtil;
import com.krm.web.file.model.UploadFile;
import com.krm.web.file.service.UploadFileService;




/**
 * 
 * @author Parker
 * 附件控制层
 * 2017-12-11
 */
@Controller
@RequestMapping("file")
public class UploadFileController extends BaseController {
	
	public static final String BASE_URL = "file";
	private static final String BASE_PATH = "file/";
	
	@Resource
	private UploadFileService uploadFileService;
	
	@Override
	protected String getBaseUrl() {
		return BASE_URL;
	}

	@Override
	protected String getBasePath() {
		return BASE_PATH;
	}

	@Override
	protected String getBasePermission() {
		return "file:uploadFile";
	}
	
	@RequestMapping(value="list", method=RequestMethod.POST)
	@ResponseBody
	public List<UploadFile> list(@RequestParam Map<String, Object> params,Model model){
		logger.info("查询附件列表，参数：" + params.toString());
		try {
			for (String key : params.keySet()){ // 处理中文乱码
				String paramsTrans = new String(((String) params.get(key)).getBytes("ISO-8859-1"), "UTF-8");
				paramsTrans = java.net.URLDecoder.decode(paramsTrans, "UTF-8");
				params.put(key, paramsTrans.trim());
			}
		} catch (Exception e) {
		}
		List<UploadFile> list = uploadFileService.entityList(params);
		return list;
	}
	
	/**
	 * 删除
	 * @param 
	 * @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	@ResponseBody
	public Result del(String id, @RequestParam Map<String, Object> params){
		logger.info("开始删除附件，参数：" + id);
		checkPermission("delete");
		int count = uploadFileService.deleteUploadFile(id);
		if(count > 0){
			String targetDirectory = SysConstant.getValue("upload.rootPath") + File.separator + "file";
			File target = new File(targetDirectory, params.get("fileName").toString());
			if(target.exists()){
				FileUtils.deleteFile(target.getAbsolutePath());
			}
			logger.info("删除附件成功！");
			return Result.successResult();
		}
		logger.info("删除附件失败！");
		return Result.errorResult();
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deletes",method=RequestMethod.POST)
	@ResponseBody
	public Result dels(@RequestParam(value = "ids[]") String[] ids, @RequestParam Map<String, Object> params){
		logger.info("开始批量删除附件，参数：" + ids);
		checkPermission("delete");
		int count = uploadFileService.deleteUploadFile(ids);
		if(count > 0){
			logger.info("删除附件成功！");
			return Result.successResult();
		}
		logger.info("删除附件失败！");
		return Result.errorResult();
	}
	
	/**
	 * 弹窗显示
	 * @param params {"mode":"1.add 2.edit 3.detail}
	 * @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(String id, @RequestParam Map<String, Object> params, @PathVariable String mode, Model model){
		if(StringUtils.equals("upload", mode)){
			logger.info("弹窗显示【附件】上传页面(" + BASE_PATH + "file-upload)");
			model.addAttribute("relateId", params.containsKey("relateId")?params.get("relateId"):null);
			model.addAttribute("view", 0);
			return  BASE_PATH + "file-upload";
		}else if(StringUtils.equals("view", mode)){
			logger.info("弹窗显示【附件】查看页面(" + BASE_PATH + "file-upload)");
			model.addAttribute("relateId", params.containsKey("relateId")?params.get("relateId"):null);
			model.addAttribute("view", 1);
			return  BASE_PATH + "file-upload";
		}
		return  BASE_PATH + "file-upload";
	}
	
	/**
	 * 文件上传
	 * @param params
	 * @param request
	 * @param files
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="upload",method=RequestMethod.POST)
	@ResponseBody
    public Result upload(@RequestParam Map<String, Object> params, HttpServletRequest request,
    			@RequestParam("file") CommonsMultipartFile files[], HttpServletResponse response, Model model ) {
    	logger.info("开始上传附件");  
//    	String permission = params.containsKey("permission")?params.get("permission").toString():"sys:UploadFile:update";
//    	SecurityUtils.getSubject().checkPermission(permission);
        String relateId = params.get("relateId").toString();
        String str = "jpeg,bmp,png,gif,jpg,ico,"
        		+ "zip,7z,tar,rar,"
        		+ "mp3,wav,mp4,mov,"
        		+ "doc,docx,ppt,pptx,xls,xlsx,pdf";
    	String targetDirectory = SysConstant.getValue("upload.rootPath") + File.separator + "file";
    	String [] allType = str.split(",");
    	StringBuilder errorMsg = new StringBuilder("");
    	int successNum = 0;
		int failureNum = 0;
    	List<String> list = Lists.newArrayList();
    	for (int i = 0; i < allType.length; i++) {
			list.add(allType[i]);
		}
        for (int i = 0; i < files.length; i++) {
			CommonsMultipartFile file = files[i];
        	String fileName = file.getOriginalFilename();
        	String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        	if(!list.contains(suffix)){
        		failureNum ++;
        		errorMsg.append("<br/>文件" + fileName + "格式不支持！ ");
        		continue;
        	}
/*        	if(file.getSize() > 104857600){
        		failureNum ++;
        		errorMsg.append("<br/>文件" + fileName + "大小超过了默认值（100Mb）！ ");
        		continue;
        	}
*/			File target = new File(targetDirectory, fileName);
			// 如果文件已经存在，则删除原有文件
			if (target.exists()) {
				target.delete();
			}
			// 复制file对象，实现上传
			try {
				FileUtils.copyInputStreamToFile(file.getInputStream(), target);
				if(suffix.equals("mp3")){
					MediaTransferUtil.mp3ToWav(target, target.getAbsolutePath().replace(suffix, "wav"));
				}
				if(suffix.equals("wav")){
					MediaTransferUtil.wavToMp3(target, target.getAbsolutePath().replace(suffix, "mp3"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			UploadFile accessory = new UploadFile();
			accessory.setId(uploadFileService.generateId());
			accessory.setDocTitle(file.getOriginalFilename());
			accessory.setFileName(fileName);
			accessory.setExtName(suffix);
			accessory.setFileSize(String.valueOf(file.getSize()));
			accessory.setDisporder(i);
			accessory.setRelateId(relateId);
			uploadFileService.save(accessory);
			successNum ++;
		}
        int repeatNum = uploadFileService.deleteRepeted(params);
//        int repeatNum = 0;
        successNum = successNum - repeatNum;
        String returnMsg = "已成功上传"+successNum+"个文件";
        if (repeatNum > 0){
        	errorMsg.append("<br/>" + repeatNum + "个文件已存在！ ");
        }
        if (failureNum > 0 || repeatNum > 0){
        	returnMsg += "，失败上传 " + (failureNum + repeatNum) + "个文件，失败信息如下："+errorMsg;
		}
        logger.info(returnMsg);
//        params.put("id", params.get("docType"));
//        model.addAttribute("fileList", sysAccessoryService.queryByParam(params));
        if(failureNum > 0){
        	return new Result(0, returnMsg);
        }
        return new Result(1, returnMsg);
    }
	
	/**
	 * 预览图片
	 * @param type
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @throws IOException
	 */
	@RequestMapping("view/{type}")
	public void download(@PathVariable String type,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws IOException {
		String fileName = request.getParameter("fileName");
		fileName = new String((fileName).getBytes("ISO-8859-1"),"UTF-8");
		fileName = java.net.URLDecoder.decode(fileName, "UTF-8");
		String doctType = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
		logger.info("开始预览图片：{}！",fileName);
		String targetDirectory = SysConstant.getValue("upload.rootPath") + File.separator + type;
		File file = new File(targetDirectory + File.separator + fileName);
		if(file.exists()){
			//读取本地图片输入流  
			FileInputStream inputStream = new FileInputStream(file.getAbsolutePath());  
			int i = inputStream.available();  
			//byte数组用于存放图片字节数据  
			byte[] buff = new byte[i];  
			inputStream.read(buff);  
			//记得关闭输入流  
			inputStream.close();  
			response.setHeader("Accept-Ranges", "bytes");
			response.setContentType("image/"+doctType);
			OutputStream out = response.getOutputStream();
			out.write(buff);  
			//关闭响应输出流  
			out.close();  
		}else{
			logger.warn("请求的文件不存在:{}",file.getAbsolutePath());
		}
	}
	
	/**
	 * 下载文件
	 * @param type
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="downFile/{type}",method=RequestMethod.GET)
	public void download(@PathVariable String type, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileName = request.getParameter("fileName");
		try {
			fileName = new String(fileName.getBytes("ISO-8859-1"), "UTF-8");
			fileName = java.net.URLDecoder.decode(fileName, "UTF-8");
		} catch (Exception e) {
		}
		logger.info("系统开始获取文件【{}】！", fileName);
		String targetDirectory = SysConstant.getValue("upload.rootPath") + File.separator + type;
		File file = new File(targetDirectory + File.separator + fileName);
		if(file.exists()){
			try {
				FileInputStream inputStream = new FileInputStream(file.getAbsolutePath());  
				FileUtils.downloadFile(response, inputStream, fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			logger.warn("请求的文件不存在:{}",file.getAbsolutePath());
		}
	}
	
}

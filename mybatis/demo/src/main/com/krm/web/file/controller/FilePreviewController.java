package com.krm.web.file.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.google.common.collect.Lists;
import com.krm.common.constant.SysConstant;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.InputFormatException;
import it.sauronsoftware.jave.MultimediaInfo;


/**
 * 附件预览类
 * @author Parker
 *
 */
@Controller
@RequestMapping(FilePreviewController.BASE_URL)
public class FilePreviewController{
	public static final String BASE_URL = "file/document/";
    private static final String BASE_PATH = "/file/preview/docPreview";

    protected Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 文档预览入口
	 */
	@RequestMapping("entrance")
    public String entrance(String fileName, HttpServletRequest request, HttpServletResponse response, 
    		RedirectAttributes redirectAttributes, Model model) {
		List<String> office = stringToList("doc,docx,ppt,pptx,xls,xlsx,pdf");
		List<String> image = stringToList("jpeg,bmp,png,gif,jpg,ico");
		List<String> media = stringToList("mp3,mp4,wav,mov,mkv");
		List<String> other = stringToList("zip,7z,tar,rar");
		String suffix = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
		try {
			if(office.contains(suffix)){
				return "redirect:/file/document/docPreview/"+suffix+"?fileName="+java.net.URLEncoder.encode(fileName, "UTF-8");
			}else if(image.contains(suffix)){
				return "redirect:/file/view/file?fileName="+java.net.URLEncoder.encode(fileName, "UTF-8");
			}else if(media.contains(suffix)){
				request.setAttribute("filePath", "/file/downFile/file?fileName=");
				model.addAttribute("fileName", fileName);
				if(suffix.equals("mp3") || suffix.equals("wav")){
					try {
						File source =new File(SysConstant.getValue("upload.rootPath") + File.separator + "file" + File.separator + fileName); 
						Encoder encoder = new Encoder();
						MultimediaInfo m;
						m = encoder.getInfo(source);
						long time = m.getDuration();
						long duration = time/1000;
						model.addAttribute("duration", duration);
						model.addAttribute("time", time/60000+":"+(time/1000-time/60000*60));
					} catch (InputFormatException e) {
						e.printStackTrace();
					} catch (EncoderException e) {
						e.printStackTrace();
					}
					return "file/preview/audioPreView";
				}else{
					return "file/preview/videoPreView";
				}
			}else if(other.contains(suffix)){
				return "redirect:/file/downFile/file?fileName="+java.net.URLEncoder.encode(fileName, "UTF-8");
			}
		} catch (Exception e) {
			logger.debug(e.toString());
		}
		return null;
	}
	
	@RequestMapping("docPreview/{type}")
    public String docPreview(@RequestParam Map<String,Object> params, @PathVariable String type, HttpServletRequest request, HttpServletResponse response, 
    		RedirectAttributes redirectAttributes, Model model) throws Exception {
		String file = params.get("fileName").toString();
		try {
			file = new String(file.getBytes("ISO-8859-1"),"UTF-8");
			file = java.net.URLDecoder.decode(file, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String sourceFilePath = SysConstant.getValue("upload.rootPath") + File.separator + "file" + File.separator + file;
		String fileName = file.substring(0, file.lastIndexOf("."));
		File docFile = new File(sourceFilePath);
		File pdfFile = new File(SysConstant.getValue("upload.rootPath") + File.separator + "temp" + File.separator + fileName+ ".pdf");
		if(!type.equals("pdf")){
			Long start = System.currentTimeMillis();
			doc2pdf(request, docFile, pdfFile);
			Long end = System.currentTimeMillis();
			DecimalFormat df = new DecimalFormat("######0.00");
			logger.info("转换PDF用时："+df.format((double)(end-start)/(double)1000)+"秒");
			model.addAttribute("filePath", "/file/downFile/temp?fileName="+pdfFile.getName());
		}else{
			model.addAttribute("filePath", "/file/downFile/file?fileName="+pdfFile.getName());
		}
    	return getBasePath();
    }
	

	@RequestMapping("steps")
    public String steps(Model model) throws Exception {
		return "file/preview/steps";
	}
	/**
	 *  转为PDF 
	 * 
	 * @param file
	 *       
	 */
	private void doc2pdf(HttpServletRequest request, File docFile, File pdfFile) throws Exception {
		if (docFile.exists()) {
			if (!pdfFile.exists()) {
				OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
				try {
					connection.connect();
					DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
					converter.convert(docFile, pdfFile);
					// close the connection
					connection.disconnect();
					logger.info("pdf转换成功，PDF输出： "+ pdfFile.getPath());
				} catch (java.net.ConnectException e) {
					e.printStackTrace();
					logger.debug("PDF转换器异常，openOffice 服务未启动！");
					throw new RuntimeException("PDF转换器异常，openOffice 服务未启动！<br>"
							+ "<a href=\""+request.getScheme()+"://"+ request.getServerName()+ ":"+ request.getServerPort() 
							+request.getContextPath() + "/file/document/steps\">openOffice软件下载与安装步骤</a>");
				} catch (com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException e) {
					e.printStackTrace();
					logger.debug("PDF转换器异常，读取转换文件失败");
					throw new RuntimeException("PDF转换器异常，读取转换文件失败！");
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
			} else {
				logger.info("已经转换为pdf，不需要再进行转化 ");
			}
		} else {
			logger.debug("PDF转换器异常，需要转换的文档不存在， 无法转换");
		}
	}


	public void exeCmd(String commandStr) {
		BufferedReader br = null;
		try {
			Process p = Runtime.getRuntime().exec(commandStr);
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			logger.info(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public List<String> stringToList(String str){
		String [] allType = str.split(",");
		List<String> list = Lists.newArrayList();
    	for (int i = 0; i < allType.length; i++) {
			list.add(allType[i]);
		}
    	return list;
	}
	
	
	@ExceptionHandler
    public String exceptionHandler(Throwable e, HttpServletRequest request) {
        logger.error("操作异常", e);
        logger.error("『*****系统内部发生错误，抛出异常，以下是异常信息：*****』", e);
        String msg = e.getMessage() == null ?  "[500] 抱歉，系统内部发生错误，请联系管理员！" : e.getMessage();
        request.setAttribute("msg", msg);
        return "error/error2";
    }

	protected String getBaseUrl() {
		return BASE_URL;
	}

	protected String getBasePath() {
		return BASE_PATH;
	}
}


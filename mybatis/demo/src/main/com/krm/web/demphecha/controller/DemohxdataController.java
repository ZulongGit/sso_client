package com.krm.web.demphecha.controller;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.krm.common.base.BaseController;
import com.krm.common.base.CommonEntity;
import com.krm.common.base.Result;
import com.krm.common.constant.Constant;
import com.krm.common.utils.DateUtils;
import com.krm.common.utils.StringConvert;
import com.krm.common.utils.StringUtil;
import com.krm.common.utils.excel.ExportExcel;
import com.krm.common.utils.excel.ImportExcel;
import com.krm.web.demphecha.model.Demohxdata;
import com.krm.web.demphecha.service.DemohxdataService;
import com.krm.web.util.SysUserUtils;

/**
 * 
 * @author Parker
 * 数据核查表控制层
 * 2018-08-23
 */
@Controller
@RequestMapping("demphecha/demohxdata")
public class DemohxdataController extends BaseController {
	
	public static final String BASE_URL = "demphecha/demohxdata";
	private static final String BASE_PATH = "demphecha/demohxdata/";
	
	@Resource
	private DemohxdataService demohxdataService;
	
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
		return "demphecha:demohxdata";
	}
	
	/**
	 * 跳转到模块页面
	 * @param model
	 * @return 模块html
	 */
	@RequestMapping
	public String toDemohxdata(Model model){
		logger.info("跳转到数据核查表页面(" + getBasePath() + "demohxdata-list)");
		checkPermission("query");
		return getBasePath() + "demohxdata-list";
	}
	
	/**
	 * 分页显示
	 * @param params
	 * @return
	 */
	@RequestMapping(value="list", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<CommonEntity> list(@RequestParam Map<String, Object> params, Model model){
		logger.info("分页显示数据核查表，参数：" + params.toString());
        checkPermission("query");
        //权限语句
		params.put("dynamicSQL", SysUserUtils.dataScopeFilterString1("", "", getBaseUrl(), "id"));
		if (params.containsKey("sortC")){
			//如果传过来的参数是驼峰式，这里需要将驼峰转成下划线式
			params.put("sortC", StringConvert.camelhumpToUnderline(params.get("sortC").toString()));
		}
		PageInfo<CommonEntity> page = demohxdataService.queryPageInfo(params);
		return page;
	}
	
	/**
	 * 添加或更新
	 * @param params
	 * @return
	 */
	@RequestMapping(value="save", method = RequestMethod.POST)
	@ResponseBody
	public Result save(@ModelAttribute Demohxdata entry, MultipartHttpServletRequest request){
		logger.info("开始保存数据核查表");
		int count = 0;
		if(StringUtil.isEmpty(entry.getId())){
			checkPermission("add");
       		entry.setId(demohxdataService.generateId());
       		count = demohxdataService.save(entry);
		}else{
			checkPermission("update");
       		count = demohxdataService.update(entry);
		}
		if(count > 0){
			logger.info("保存数据核查表成功！");
			return Result.successResult();
		}
		return Result.errorResult();
	}
	
	/**
	 * 删除
	 * @param 
	 * @return
	 */
	@RequestMapping(value="delete", method = RequestMethod.POST)
	@ResponseBody
	public Result del(String id, @RequestParam Map<String, Object> params){
		logger.info("开始删除数据核查表，参数：" + id);
		checkPermission("delete");
		int count = demohxdataService.deleteDemohxdata(id);
		if(count > 0){
			logger.info("删除数据核查表成功！");
			return Result.successResult();
		}
		logger.info("删除数据核查表失败！");
		return Result.errorResult();
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deletes", method = RequestMethod.POST)
	@ResponseBody
	public Result dels(@RequestParam(value = "ids[]") String[] ids, @RequestParam Map<String, Object> params){
		logger.info("开始批量删除数据核查表，参数：" + ids);
		checkPermission("delete");
		int count = demohxdataService.deleteDemohxdata(ids);
		if(count > 0){
			logger.info("删除数据核查表成功！");
			return Result.successResult();
		}
		logger.info("删除数据核查表失败！");
		return Result.errorResult();
	}
	
	/**
	 * 弹窗显示
	 * @param params {"mode":"1.add 2.edit 3.detail}
	 * @return
	 */
	@RequestMapping(value="{mode}/showlayer", method=RequestMethod.POST)
	public String layer(String id, @RequestParam Map<String, Object> params, @PathVariable String mode, Model model){
		Demohxdata entry = null;
		if(StringUtils.equals("add", mode)){
			logger.info("弹窗显示【数据核查表】添加页面(" + getBasePath() + "demohxdata-add)");
			checkPermission("add");
			return getBasePath() + "demohxdata-add";
		}else if(StringUtils.equals("edit", mode)){
			logger.info("弹窗显示【数据核查表】编辑页面(" + getBasePath() + "demohxdata-update)");
			checkPermission("update");
			params.put("id", id);
			entry = demohxdataService.queryOne(params);
			model.addAttribute("entry", entry);
			return getBasePath() + "demohxdata-update";
		}else if(StringUtils.equals("detail", mode)){
			logger.info("弹窗显示【数据核查表】详情页面(" + getBasePath() + "demohxdata-detail)");
			checkPermission("query");
			params.put("id", id);
			CommonEntity entity = demohxdataService.queryOneCommon(params);
			model.addAttribute("entry", entity);
		}else if(StringUtils.equals("import", mode)){
			logger.info("弹窗显示【数据核查表】Excel导入页面(" + getBasePath() + "demohxdata-import)");
			checkPermission("import");
			return getBasePath() + "demohxdata-import";
		}
		return getBasePath() + "demohxdata-detail";
	}
	
	/**
     * 数据核查表Excel导入模板
     * @param response
     * @param redirectAttributes
     * @return
     * @throws Exception 
     */
    @RequestMapping("import/template/download")
    public void importDemohxdataTemplate(HttpServletResponse response) throws Exception {
    	logger.info("开始下载数据核查表Excel导入模板");
    	checkPermission("import");
		String fileName = "数据核查表Excel导入模板.xlsx";
		List<Demohxdata> list = Lists.newArrayList();
		list.add(new Demohxdata());
		new ExportExcel("数据核查表", Demohxdata.class, 2).setDataList(list).write(response, fileName).dispose();
    }
    
    /**
     * 数据核查表数据导入
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "import", method=RequestMethod.POST)
    @ResponseBody
    public Result importFile(@RequestParam("file") MultipartFile fileList[], HttpServletResponse response) throws Exception {
    	logger.info("开始导入数据核查表数据");
    	checkPermission("import");
    	Long start = System.currentTimeMillis();
    	int successNum = 0;
    	int failureNum = 0;
    	for (MultipartFile file : fileList) {
    		ImportExcel ei;
    		StringBuilder failureMsg = new StringBuilder();
   			ei = new ImportExcel(file, 1, 0);
   			List<Demohxdata> list = ei.getDataList(Demohxdata.class);
   			for (Demohxdata entry : list) {
   				entry.setId(demohxdataService.generateId());
   				entry.setCreateBy(SysUserUtils.getCacheLoginUser().getId());
   				entry.setCreateDate(new Date());
   				entry.setDelFlag(Constant.DEL_FLAG_NORMAL);
			}
   			successNum = demohxdataService.insertBatch(list);
    		if (failureNum > 0){
    			failureMsg.insert(0, "，失败导入 " + failureNum + " 条数据核查表数据，导入信息如下：");
    		}
    		Long end = System.currentTimeMillis();
    		DecimalFormat df = new DecimalFormat("######0.00");
    		logger.info("导入用时"+df.format((double)(end-start)/(double)1000)+"秒");
		}
    	return new Result(1, "操作成功！，成功导入"+successNum+"条，失败导入"+failureNum+"条");
    }
    
    /**
	 * 数据核查表导出excel
	 */
	@RequestMapping(value="export", method = RequestMethod.POST)
	public void export(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception{
		logger.info("开始导出数据核查表数据");
		checkPermission("export");
		String fileName = "数据核查表" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
		//权限语句
		params.put("dynamicSQL", SysUserUtils.dataScopeFilterString1("", "", getBaseUrl(), "id"));
		try {
			for (String key : params.keySet()){ // 处理中文乱码
				String paramsTrans = new String(((String) params.get(key)).getBytes("ISO-8859-1"), "UTF-8");
				paramsTrans = java.net.URLDecoder.decode(paramsTrans, "UTF-8");
				params.put(key, paramsTrans.trim());
			}
		} catch (Exception e) {
		}
		List<Demohxdata> list = demohxdataService.entityList(params);
		new ExportExcel("数据核查表", Demohxdata.class).setDataList(list)
			.write(response, fileName).dispose();
	}
}
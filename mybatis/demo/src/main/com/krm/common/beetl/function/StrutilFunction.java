package com.krm.common.beetl.function;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.krm.common.utils.StringConvert;
import com.krm.common.utils.StringUtil;

@Component
public class StrutilFunction {

	/**
	 * 日志对象
	 */
	protected static Logger logger = LoggerFactory.getLogger(StrutilFunction.class);
	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String regEx_img = "<img[^>]+>"; // 定义图片标签的正则表达式
    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符
    
	public String subStringTo(String str,int start,int end){
		if(str != null && str.length() > 0){
			return str.substring(start, end);
		}
		return "";
	}
	
	public String subStringLen(String str,int len){
		if(str != null && str.length() > 0 && len < str.length()){
			return str.substring(0,len)+"…";
		}
		return str;
	}
	
	public boolean isEmpty(String str){
		if(str == null || str.trim().length() == 0)
			return true;
		return false;			
	}
	
	public boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
	
	/**
	 * 将驼峰风格替换为下划线风格
	 */
	public String camelhumpToUnderline(String str) {
		return StringConvert.camelhumpToUnderline(str);
	}
	
	
	/**
	 * 将下划线风格替换为驼峰风格
	 */
	public String underlineToCamelhump(String str) {
		return StringConvert.underlineToCamelhump(str);
	}
	
	
	public String getMaxName(String str){
		return StringUtil.firstCharToUpper(str);
	}
	
	
	 /**
     * @param htmlStr
     * @return
     *  删除Html标签
     */
    public static String delHTMLTag(String htmlStr) {
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签

        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
        return htmlStr.trim(); // 返回文本字符串
    }
    
    public static String getTextFromHtml(String htmlStr){
    	return getTextFromHtml(htmlStr, 0);
    }
    
    public static String getTextFromHtml(String htmlStr,int imgFlag){
    	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    	StringBuilder sb = new StringBuilder();
    	if(imgFlag == 1){
	    	Pattern p_img = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
	        Matcher m_img = p_img.matcher(htmlStr);
	        while(m_img.find()){
	        	String path = request.getContextPath();
	        	String str = m_img.group().replaceAll(path, request.getScheme()+"://"+ request.getServerName()+ ":"+ request.getServerPort() +request.getContextPath());
	        	sb.append(StringEscapeUtils.escapeHtml3(str));
	        }
    	}
    	htmlStr = delHTMLTag(htmlStr);
    	htmlStr = StringUtils.replace(htmlStr, "&amp;", "&");
    	htmlStr = htmlStr.replaceAll(" ", " ").replaceAll("&nbsp;", " ");
    	htmlStr = StringUtils.replace(htmlStr, "&apos;", "'");
    	htmlStr = StringUtils.replace(htmlStr, "&#39;", "'");
        htmlStr = StringUtils.replace(htmlStr, "&quot;", "\"");
        htmlStr = StringUtils.replace(htmlStr, "&nbsp;", "");// 替换空格
        htmlStr = StringUtils.replace(htmlStr, "&ldquo;", "“");
        htmlStr = StringUtils.replace(htmlStr, "&rdquo;", "”");
        if(imgFlag == 1){
        	htmlStr += sb.toString();
        }
    	return htmlStr;
    }
    
    public static void main(String[] args) {
    	String str = "&lt;p&gt;弹层之美&lt;span class=&quot;layer-tool&quot; style=&quot;position: absolute; right: 0px; top: 18px;&quot;&gt;&lt;a class=&quot;layui-btn&quot; style=&quot;color: rgb(255, 255, 255); vertical-align: middle; outline: 0px; -webkit-appearance: none; transition: all 0.3s; box-sizing: border-box; display: inline-block; height: 38px; line-height: 38px; padding: 0px 18px; background-color: rgb(0, 150, 136); white-space: nowrap; text-align: center; border: none; border-radius: 2px; cursor: pointer; user-select: none;&quot;&gt;在线调试&lt;/a&gt;&lt;span class=&quot;Apple-converted-space&quot;&gt;&amp;nbsp;&lt;/span&gt;&lt;a href=&quot;http://layer.layui.com/skin.html&quot; class=&quot;layui-btn layui-btn-normal&quot; target=&quot;_blank&quot; style=&quot;color: rgb(255, 255, 255); text-decoration-line: none; vertical-align: middle; outline: 0px; -webkit-appearance: none; transition: all 0.3s; box-sizing: border-box; display: inline-block; height: 38px; line-height: 38px; padding: 0px 18px; background-color: rgb(30, 159, 255); white-space: nowrap; text-align: center; border: none; border-radius: 2px; user-select: none; margin-left: 10px;&quot;&gt;扩展皮肤&lt;/a&gt;&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;margin-top: 0px; margin-bottom: 10px; padding: 0px; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); text-indent: 2em; line-height: 23px;&quot;&gt;&lt;em style=&quot;font-family: &amp;quot;Bookman Old Style&amp;quot;;&quot;&gt;layer&lt;/em&gt;是一款近年来备受青睐的web弹层组件，她具备全方位的解决方案，致力于服务各水平段的开发人员，您的页面会轻松地拥有丰富友好的操作体验。&lt;/p&gt;&lt;p style=&quot;margin-top: 0px; margin-bottom: 10px; padding: 0px; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); text-indent: 2em; line-height: 23px;&quot;&gt;在与同类组件的比较中，&lt;em style=&quot;font-family: &amp;quot;Bookman Old Style&amp;quot;;&quot;&gt;layer&lt;/em&gt;总是能轻易获胜。她尽可能地在以更少的代码展现更强健的功能，且格外注重性能的提升、易用和实用性，正因如此，越来越多的开发者将媚眼投上了&lt;em style=&quot;font-family: &amp;quot;Bookman Old Style&amp;quot;;&quot;&gt;layer&lt;/em&gt;（已被&lt;em style=&quot;font-weight: 900;&quot;&gt;5601398&lt;/em&gt;人次关注）。&lt;em style=&quot;font-family: &amp;quot;Bookman Old Style&amp;quot;;&quot;&gt;layer&lt;/em&gt;&lt;span class=&quot;Apple-converted-space&quot;&gt;&amp;nbsp;&lt;/span&gt;甚至兼容了包括 IE6 在内的所有主流浏览器。她数量可观的接口，使得您可以自定义太多您需要的风格，每一种弹层模式各具特色，广受欢迎。当然，这种&ldquo;王婆卖瓜&rdquo;的陈述听起来总是有点难受，因此你需要进一步了解她是否真的如你所愿。&lt;/p&gt;&lt;p style=&quot;margin-top: 0px; margin-bottom: 10px; padding: 0px; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); text-indent: 2em; line-height: 23px;&quot;&gt;&lt;em style=&quot;font-family: &amp;quot;Bookman Old Style&amp;quot;;&quot;&gt;layer&lt;/em&gt;&lt;span class=&quot;Apple-converted-space&quot;&gt;&amp;nbsp;&lt;/span&gt;采用 MIT 开源许可证，&lt;em style=&quot;font-weight: 900;&quot;&gt;将会永久性提供无偿服务&lt;/em&gt;。因着数年的坚持维护，截至到2017年9月13日，已运用在超过&lt;span class=&quot;Apple-converted-space&quot;&gt;&amp;nbsp;&lt;/span&gt;&lt;em style=&quot;font-weight: 900;&quot;&gt;30万&lt;/em&gt;&lt;span class=&quot;Apple-converted-space&quot;&gt;&amp;nbsp;&lt;/span&gt;家 Web 平台，其中不乏众多知名大型网站。目前 layer 已经成为国内乃至全世界最多人使用的 Web 弹层解决方案，并且她仍在与 Layui 一并高速发展。&lt;a href=&quot;http://fly.layui.com/&quot; target=&quot;_blank&quot; style=&quot;color: rgb(51, 51, 51); text-decoration-line: none;&quot;&gt;&lt;em style=&quot;font-weight: 900;&quot;&gt;Fly&lt;/em&gt;&lt;/a&gt;&lt;/p&gt;&lt;p&gt;先睹为快&lt;/p&gt;&lt;p&gt;&lt;br/&gt;&lt;/p&gt;";
    	System.out.println(getTextFromHtml(StringEscapeUtils.unescapeHtml3(str),1));
	}
}

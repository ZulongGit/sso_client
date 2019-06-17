package com.krm.web.tools.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.krm.web.tools.model.MailModel;

@Service("emailService")
public class EmailService {
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private JavaMailSenderImpl javaMailSender;
    
    public void emailManage(){
        MailModel mail = new MailModel();
        //主题
        mail.setEmailFrom(javaMailSender.getUsername());
        mail.setSubject("清单"); 
        mail.setToEmails("zhangyu@krmsoft.com");
        //附件
        Map<String, String> attachments = new HashMap<String, String>();
//        attachments.put("清单.xlsx",excelPath+"清单.xlsx");
        mail.setAttachments(attachments);
        
        //内容
        StringBuilder builder = new StringBuilder();
        builder.append("<html><body>你好！<br />");
        builder.append("&nbsp&nbsp&nbsp&nbsp附件是个人清单。<br />");
        builder.append("&nbsp&nbsp&nbsp&nbsp其中人信息；<br />");
        builder.append("</body></html>");
        String content = builder.toString();
        
        mail.setContent(content);
        
        sendEmail(mail);
    }


	public void sendRegisterEmail(String eamil, String url) {
		MailModel mail = new MailModel();
		mail.setEmailFrom(javaMailSender.getUsername());
		mail.setSubject("账号激活");
		mail.setToEmails(eamil);
		//内容
        StringBuilder builder = new StringBuilder();
        builder.append("<html><body>您好！<br />");
        builder.append("&nbsp;&nbsp;&nbsp;&nbsp;感谢您的注册，请点击下面链接以激活您的账号：<br />");
        builder.append("&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+url+"'>"+url+"</a><br />");
        builder.append("</body></html>");
        String content = builder.toString();
        mail.setContent(content);
        sendEmail(mail);
	}

    /**
     * 发送邮件
     * @param mail
     */
    public void sendEmail(MailModel mail)  {
        // 建立邮件消息
        MimeMessage message = javaMailSender.createMimeMessage();
        
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            // 设置发件人邮箱
            if (mail.getEmailFrom()!=null) {
//                messageHelper.setFrom(mail.getEmailFrom());
                messageHelper.setFrom(mail.getEmailFrom(), "自动化编程系统管理员"); 
            }
            
            // 设置收件人邮箱
            if (mail.getToEmails()!=null) {
                String[] toEmailArray = mail.getToEmails().split(";");
                List<String> toEmailList = new ArrayList<String>();
                if (null == toEmailArray || toEmailArray.length <= 0) {
                    throw new RuntimeException("收件人邮箱不得为空！");
                } else {
                    for (String s : toEmailArray) {
                        if (s!=null&&!s.equals("")) {
                            toEmailList.add(s);
                        }
                    }
                    if (null == toEmailList || toEmailList.size() <= 0) {
                        throw new RuntimeException("收件人邮箱不得为空！");
                    } else {
                        toEmailArray = new String[toEmailList.size()];
                        for (int i = 0; i < toEmailList.size(); i++) {
                            toEmailArray[i] = toEmailList.get(i);
                        }
                    }
                }
                messageHelper.setTo(toEmailArray);
            }
            
            // 邮件主题
            if (mail.getSubject()!=null) {
                messageHelper.setSubject(mail.getSubject());
            }
            // true 表示启动HTML格式的邮件
            messageHelper.setText(mail.getContent(), true);
            
            // 添加图片
            if (null != mail.getPictures()) {
                for (Iterator<Map.Entry<String, String>> it = mail.getPictures().entrySet()
                        .iterator(); it.hasNext();) {
                    Map.Entry<String, String> entry = it.next();
                    String cid = entry.getKey();
                    String filePath = entry.getValue();
                    if (null == cid || null == filePath) {
                        throw new RuntimeException("请确认每张图片的ID和图片地址是否齐全！");
                    }
                    
                    File file = new File(filePath);
                    if (!file.exists()) {
                        throw new RuntimeException("图片" + filePath + "不存在！");
                    }
                    
                    FileSystemResource img = new FileSystemResource(file);
                    messageHelper.addInline(cid, img);
                }
            }
            
            // 添加附件
            if (null != mail.getAttachments()) {
                for (Iterator<Map.Entry<String, String>> it = mail.getAttachments()
                        .entrySet().iterator(); it.hasNext();) {
                    Map.Entry<String, String> entry = it.next();
                    String cid = entry.getKey();
                    String filePath = entry.getValue();
                    if (null == cid || null == filePath) {
                        throw new RuntimeException("请确认每个附件的ID和地址是否齐全！");
                    }
                    
                    File file = new File(filePath);
                    if (!file.exists()) {
                        throw new RuntimeException("附件" + filePath + "不存在！");
                    }
                    
                    FileSystemResource fileResource = new FileSystemResource(file);
                    messageHelper.addAttachment(cid, fileResource);
                }
            }
            messageHelper.setSentDate(new Date());
            // 发送邮件
            javaMailSender.send(message);
            logger.info("------------发送邮件完成----------");
            
        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }
}

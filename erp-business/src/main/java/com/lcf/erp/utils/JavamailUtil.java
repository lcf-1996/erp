package com.lcf.erp.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/*
	发送邮件工具类
*/
public class JavamailUtil {

	/**
	 * 发送邮件
	 * @param javaMailSender 负责发送邮件
	 * @param receivers 接收者
	 * @param subject 邮件标题
	 * @param content 邮件内容
	 */
	public static void sendMail(JavaMailSender javaMailSender, String from
			, String[] receivers, String subject,String content) {
		SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(receivers);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
	}
	
}

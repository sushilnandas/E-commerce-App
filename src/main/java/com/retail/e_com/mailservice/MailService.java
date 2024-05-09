package com.retail.e_com.mailservice;

import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.retail.e_com.utility.MessageModel;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Component
public class MailService {
	
	private JavaMailSender javaMailSender;
	
	public void sendMailMessage(MessageModel messageModel) throws MessagingException{
		
		MimeMessage message =  javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,true);
		helper.setTo(messageModel.getTo());
		helper.setSubject(messageModel.getSubject());
		helper.setText(messageModel.getText(),true);
		javaMailSender.send(message);
		
	}

}

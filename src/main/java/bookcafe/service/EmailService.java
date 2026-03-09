package bookcafe.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
public class EmailService {
	
	@Value("${spring.mail.username}")
	private String senderEmail;
	
	private final JavaMailSender mailSender;
	
	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	 
	public void sendSimpleEmail(String to) {
	        
		SimpleMailMessage message = new SimpleMailMessage();
	        
		message.setTo(to);
	        
		message.setSubject("이메일 인증을 위한 발송");
		
		int authCode = (int)(Math.random()*1_000_000);
	        
		message.setText("인증 번호는 : " + authCode + " 입니다." );
	        
		message.setFrom(senderEmail);
	        
		mailSender.send(message);
	 }
	
	public boolean verifyEmailAuthCode(String emailAddress, String authCode) {
		return true;
	}
	
}

package bookcafe.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import bookcafe.data.entity.EmailAuthentication;
import bookcafe.data.repository.EmailAuthenticationRepository;

@Service
public class EmailService {
	
	@Value("${spring.mail.username}")
	private String senderEmail;
	
	private final JavaMailSender mailSender;
	
	private EmailAuthenticationRepository emailAuthRepository;
	
	public EmailService(JavaMailSender mailSender, EmailAuthenticationRepository emailAuthRepository) {
		this.mailSender = mailSender;
		this.emailAuthRepository = emailAuthRepository;
	}
	 
	public void sendSimpleEmail(String to) {
	        
		SimpleMailMessage message = new SimpleMailMessage();
	        
		message.setTo(to);
	        
		message.setSubject("이메일 인증을 위한 발송");
		
		int authCode = (int)(Math.random()*1_000_000);
	        
		message.setText("인증 번호는 : " + authCode + " 입니다." );
	        
		message.setFrom(senderEmail);
	        
		mailSender.send(message);
		
		EmailAuthentication emailAuth = new EmailAuthentication(to, String.valueOf(authCode) , false);
		
		emailAuthRepository.save(emailAuth);
	 }
	
	public boolean verifyEmailAuthCode(String emailAddress, String authCode) {
		return true;
	}
	
}

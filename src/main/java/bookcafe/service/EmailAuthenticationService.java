package bookcafe.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import bookcafe.data.entity.EmailAuthentication;
import bookcafe.data.repository.EmailAuthenticationRepository;
import jakarta.servlet.http.HttpSession;

@Service
public class EmailAuthenticationService {
	
	@Value("${spring.mail.username}")
	private String senderEmail;
	
	private final JavaMailSender mailSender;
	
	private EmailAuthenticationRepository emailAuthRepository;
		
	private HttpSession session;
	
	public EmailAuthenticationService(JavaMailSender mailSender, EmailAuthenticationRepository emailAuthRepository ,HttpSession session) {
		this.mailSender = mailSender;
		this.emailAuthRepository = emailAuthRepository;
		this.session = session;
	}
	 
	public void sendEmailAuthCode(String to) {
	        
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
	
	
	public boolean verifyEmailAuthCode(String emailAddress, String userSubmitAuthCode) {
		
		Optional<EmailAuthentication> emailAuthInfo = emailAuthRepository.findByEmailAddress(emailAddress);
		
		if(emailAuthInfo.isEmpty())
			return false;
		
		EmailAuthentication emailAuth = emailAuthInfo.get();
		
		String authCode = emailAuth.getAuthCode();
		
		LocalDateTime emailAuthSendTime = emailAuth.getCratedAt();
		
		long elapsedMinuteAfterSend = ChronoUnit.MINUTES.between(emailAuthSendTime, LocalDateTime.now());
		
		if(Objects.equals(authCode, userSubmitAuthCode) && elapsedMinuteAfterSend <= 5) {
			emailAuth.setAuthenticated(true);
			session.setAttribute("verifiedEmailAddress", emailAddress);
			emailAuthRepository.save(emailAuth);
			return true;
		}
		
		return false;
	}
	
	
	public boolean isVerifiedEmailAddress(String emailAddress) {
		Optional<EmailAuthentication> emailAuthInfo = emailAuthRepository.findByEmailAddress(emailAddress);
		
		if(emailAuthInfo.isEmpty())
			return false;
		
		EmailAuthentication emailAuth = emailAuthInfo.get();
		
		Object verifiedEmailAddress = session.getAttribute("verifiedEmailAddress");
		
		if(emailAuth.isAuthenticated() && Objects.equals(emailAddress, verifiedEmailAddress)) {
			session.removeAttribute("verifiedEmailAddress");
			emailAuthRepository.delete(emailAuth);
			return true;
		}
		
		return false;
	}
	
}

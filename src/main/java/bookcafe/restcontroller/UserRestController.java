package bookcafe.restcontroller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bookcafe.data.repository.SiteUserRepository;
import bookcafe.service.EmailAuthenticationService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserRestController {
	
	private SiteUserRepository userRepo;
	
	private EmailAuthenticationService emailAuthenticationService;


    @GetMapping
	public ResponseEntity<ApiResponse<Boolean>> isUsernameAvailable(@RequestParam("username")String username){

    	if(userRepo.existsByUsername(username))
			return ResponseEntity.status(403).body(new ApiResponse<Boolean>("사용 할 수 없는 아이디입니다." , Boolean.FALSE));
					
		return ResponseEntity.ok(new ApiResponse<Boolean>("사용 가능한 아이디입니다." , Boolean.TRUE));
		
	}
    
    @GetMapping("/send/email/auth")
    public ResponseEntity<ApiResponse<?>> sendEmailForUserEmailAuth(@RequestParam("emailAddress")String emailAddress){
    	
    	emailAuthenticationService.sendEmailAuthCode(emailAddress);
    	
    	return ResponseEntity.ok(new ApiResponse<>("인증메일이 발송되었습니다." , null));
    }
    
    
    @PostMapping("/verify/email/auth")
    public ResponseEntity<ApiResponse<?>> verifyUserSubmitEmailAuthCode(@RequestBody Map<String, String> body){
    	
    	String emailAddress = body.get("emailAddress");
    	String userSubmitAuthCode = body.get("emailAuthCode");
    	
    	if(emailAuthenticationService.verifyEmailAuthCode(emailAddress, userSubmitAuthCode))
    		return ResponseEntity.ok(new ApiResponse<>("인증 성공" , null));
    	
    	return ResponseEntity.status(403).body(new ApiResponse<>("인증 실패", null));
    }
    
    
}

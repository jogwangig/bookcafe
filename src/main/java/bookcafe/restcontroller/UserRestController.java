package bookcafe.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bookcafe.data.repository.SiteUserRepository;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserRestController {
	
	private SiteUserRepository userRepo;


    @GetMapping
	public ResponseEntity<ApiResponse<Boolean>> isUsernameAvailable(@RequestParam("username")String username){

    	if(userRepo.existsByUsername(username))
			return ResponseEntity.status(403).body(new ApiResponse<Boolean>("사용 할 수 없는 아이디입니다." , Boolean.FALSE));
					
    	
		
		return ResponseEntity.ok(new ApiResponse<Boolean>("사용 가능한 아이디입니다." , Boolean.TRUE));
		
	}
}

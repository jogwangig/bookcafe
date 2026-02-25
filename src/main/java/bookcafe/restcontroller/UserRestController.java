package bookcafe.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import bookcafe.controller.ReadingRecordController;
import bookcafe.data.repository.SiteUserRepository;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserRestController {

    private final ReadingRecordController readingRecordController;
	
	private SiteUserRepository userRepo;

    UserRestController(ReadingRecordController readingRecordController) {
        this.readingRecordController = readingRecordController;
    }

    @GetMapping
	public ResponseEntity<Boolean> isUsernameAvailable(@RequestParam("username")String username){
		if(userRepo.exexistsByUsername(username))
			return ResponseEntity.ok(Boolean.valueOf(false));
		
		return ResponseEntity.ok(Boolean.valueOf(true));
	}
}

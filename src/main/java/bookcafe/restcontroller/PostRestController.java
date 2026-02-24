package bookcafe.restcontroller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import bookcafe.data.entity.Post;
import bookcafe.data.repository.PostRepository;
import bookcafe.security.CustomUserDetails;
import bookcafe.service.PostAuthService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class PostRestController {
	
	private PostRepository postRepo;
	
	private PostAuthService postAuthService;
	
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> deletePost(@RequestParam("postId")long postId, @AuthenticationPrincipal CustomUserDetails userDetails){
		Post post = postRepo.findById(postId).get();
		
		if(userDetails == null)
			return ResponseEntity.badRequest().body("삭제 실패");
		
		Long userId = userDetails.getId();
		
		if(post.getUser().getId().equals(userId)) {
			postRepo.deleteById(postId);
			return ResponseEntity.ok().body("게시글이 삭제되었습니다.");
		}
		
		return ResponseEntity.badRequest().body("삭제 실패");
			
	}
	
	
	@PostMapping("/delete")
	public ResponseEntity<String> deletePost(@RequestParam("postId")long postId, @RequestBody Map<String, String> body){
		
		Post post = postRepo.findById(postId).get();
		
		String pwd = body.get("pwd");
				
		if(postAuthService.authenticateForDelete(post, pwd)) {
			postRepo.deleteById(postId);
			
			postAuthService.flushDeleteAuth(postId);
			
			return ResponseEntity.ok().body("게시글이 삭제되었습니다.");
		}
		
		return ResponseEntity.badRequest().body("삭제 실패");
		
	}
	
	
	@PostMapping("/modify/auth")
	public ResponseEntity<String> authenticateUserForModification(@RequestParam("postId")long postId,
			@RequestBody Map<String, String> body, HttpSession session) {
		
		Post post = postRepo.findById(postId).get();
		
		String pwd = body.get("pwd");
		
		if(postAuthService.authenticateForModification(post, pwd)) {
			return ResponseEntity.ok().body("인증 성공");
		}

		return ResponseEntity.badRequest().body("인증 실패");
	}
	
	
	
	
	@GetMapping("/modify/auth")
	public ResponseEntity<String> authenticateUserForModification(@RequestParam("postId")long postId,
			@AuthenticationPrincipal CustomUserDetails userDetails) {
		
		Post post = postRepo.findById(postId).get();
		
		if(userDetails == null)
			return ResponseEntity.badRequest().body("인증 실패");
		
		if(postAuthService.isAuthenticatedForModification(post, userDetails)) {
			return ResponseEntity.ok().body("인증 성공");
		}

		return ResponseEntity.badRequest().body("인증 실패");
	}

}

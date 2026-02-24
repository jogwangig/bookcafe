package bookcafe.restcontroller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import bookcafe.data.entity.Post;
import bookcafe.data.repository.PostRepository;
import bookcafe.security.CustomUserDetails;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/post")
@AllArgsConstructor
public class PostRestController {
	
	private PostRepository postRepo;
	

	
	
	@ResponseBody
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
	
	
	@ResponseBody
	@PostMapping("/delete")
	public ResponseEntity<String> deletePost(@RequestParam("postId")long postId,@RequestBody Map<String, String> body){
		
		Post post = postRepo.findById(postId).get();
		
		String pwd = body.get("pwd");
				
		if(post.getAnonymousUserPwd().equals(pwd)) {
			postRepo.deleteById(postId);
			return ResponseEntity.ok().body("게시글이 삭제되었습니다.");
		}
		
		return ResponseEntity.badRequest().body("삭제 실패");
		
	}

}

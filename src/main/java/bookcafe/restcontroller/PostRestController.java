package bookcafe.restcontroller;

import java.util.Map;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bookcafe.data.dto.creation.CommentCreationDto;
import bookcafe.data.entity.Comment;
import bookcafe.data.entity.Post;
import bookcafe.data.repository.CommentRepository;
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
	
	private CommentRepository commentRepo;
	
	private PostAuthService postAuthService;
	
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> deletePost(@RequestParam("postId")long postId, @AuthenticationPrincipal CustomUserDetails userDetails){
		Post post = postRepo.findById(postId).get();
		
		if(userDetails == null)
			return ResponseEntity.badRequest().body("삭제 실패");
		
		Long userId = userDetails.getId();
		
		if(postAuthService.isAuthenticatedForEdit(post, userDetails)) {
			postRepo.deleteById(postId);
			return ResponseEntity.ok().body("게시글이 삭제되었습니다.");
		}
		
		return ResponseEntity.badRequest().body("삭제 실패");
			
	}
	
	
	@PostMapping("/delete")
	public ResponseEntity<String> deletePost(@RequestParam("postId")long postId, @RequestBody Map<String, String> body){
		
		Post post = postRepo.findById(postId).get();
		
		String pwd = body.get("pwd");
				
		if(postAuthService.authenticateForEdit(post, pwd)) {
			postRepo.deleteById(postId);
			
			postAuthService.flushEditAuth(postId);
			
			return ResponseEntity.ok().body("게시글이 삭제되었습니다.");
		}
		
		return ResponseEntity.badRequest().body("삭제 실패");
		
	}
	
	
	@PostMapping("/modify/auth")
	public ResponseEntity<String> authenticateUserForModification(@RequestParam("postId")long postId,
			@RequestBody Map<String, String> body) {
		
		Post post = postRepo.findById(postId).get();
		
		String pwd = body.get("pwd");
		
		if(postAuthService.authenticateForEdit(post, pwd)) {
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
		
		if(postAuthService.isAuthenticatedForEdit(post, userDetails)) {
			return ResponseEntity.ok().body("인증 성공");
		}

		return ResponseEntity.badRequest().body("인증 실패");
	}
	
	
	
	@PostMapping(path = "/comment", params = {"postId" , "type"})
	public String processCommentCreation(@RequestBody CommentCreationDto body, 
			@RequestParam("postId")long postId, @RequestParam("type")String type) {

		Comment comment = body.toEntity();
		
		comment.setPost(postRepo.getReferenceById(postId));
		
		commentRepo.save(comment);
		
		return "redirect:/post?postId=" + postId;
	}
	
	@PostMapping(path = "/comment", params = {"commentId" , "type"})
	public String processCommentModification(@RequestBody CommentCreationDto body, 
			@RequestParam("commentId")long commentId, @RequestParam("type")String type, 
			@AuthenticationPrincipal CustomUserDetails userDetails,  HttpSession session) {

		
		Comment comment = commentRepo.findById(commentId).get();
		
		if(!comment.isWrittenByAnonymous() && userDetails != null && comment.getUser().getId().equals(userDetails.getId())) {
			
			comment.setContent(body.getContent());
			
			commentRepo.save(comment);
		}
		
		if(comment.isWrittenByAnonymous() && 
				Objects.equals(session.getAttribute("comment-auth-" + commentId), true)) {
			
			comment.setContent(body.getContent());
			
			commentRepo.save(comment);
			
			session.removeAttribute("comment-auth-" + commentId);
		}
			
		
		return "redirect:/post?postId=" ;
	}
	
	
	
	@GetMapping("/comment/modify")
	public ResponseEntity<CommentCreationDto> getCommentForEdit(@RequestParam("commentId")Long commentId, 
			@AuthenticationPrincipal CustomUserDetails userDetails){
		
		if(userDetails == null)
			return ResponseEntity.badRequest().build();
		
		Comment comment = commentRepo.findById(commentId).get();
		
		CommentCreationDto c = new CommentCreationDto(comment.getAnonymousUsername(), comment.getAnonymousUserPwd(), comment.getContent());
		
		if(comment.getUser().getId().equals(userDetails.getId())) {
			return ResponseEntity.ok().body(c);
		}
		
		return ResponseEntity.badRequest().build();
		
	}
	
	
	@PostMapping("/comment/modify")
	public ResponseEntity<CommentCreationDto> getCommentForEdit(@RequestParam("commentId")Long commentId, 
			@RequestBody Map<String, String> body, HttpSession session){
		
		
		Comment comment = commentRepo.findById(commentId).get();
		
		String pwd = body.get("pwd");
		
		CommentCreationDto c = new CommentCreationDto(comment.getAnonymousUsername(), comment.getAnonymousUserPwd(), comment.getContent());
		
		
		if(Objects.equals(comment.getAnonymousUserPwd(), pwd)) {
			session.setAttribute("comment-auth-" + commentId, true);
			return ResponseEntity.ok().body(c);
		}

		return ResponseEntity.badRequest().build();
		
	}
	
	
	@GetMapping("/comment/delete")
	public ResponseEntity<String> deleteComment(@RequestParam("commentId")Long commentId, 
			@AuthenticationPrincipal CustomUserDetails userDetails){
		
		if(userDetails == null)
			return ResponseEntity.badRequest().build();
		
		Comment comment = commentRepo.findById(commentId).get();
		
		
		if(comment.getUser().getId().equals(userDetails.getId())) {
			commentRepo.deleteById(commentId);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.badRequest().build();
		
	}
	
	@PostMapping("/comment/delete")
	public ResponseEntity<String> deleteComment(@RequestParam("commentId")Long commentId, 
			@RequestBody Map<String, String> body){
		
		
		Comment comment = commentRepo.findById(commentId).get();
		
		String pwd = body.get("pwd");
				
		
		if(Objects.equals(comment.getAnonymousUserPwd(), pwd)) {
			commentRepo.deleteById(commentId);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.badRequest().build();
		
	}

}

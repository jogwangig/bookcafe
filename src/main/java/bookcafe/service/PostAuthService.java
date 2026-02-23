package bookcafe.service;

import org.springframework.stereotype.Service;
import bookcafe.controller.ReadingRecordController;
import bookcafe.data.entity.Post;
import bookcafe.data.repository.PostRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostAuthService {

	
	private PostRepository postRepo;
	
	private HttpSession session;


	
	
	public boolean isAuthenticatedUser(Long postId , Long userId) {
		
		Post post = postRepo.findById(postId).get();
						
		return post.getUser().getId().equals(userId);
	}
	
	
	public boolean isAuthenticatedAnonymousUser(Long postId) {
				
		return session.getAttribute("post-modification-auth-" + postId) != null &&
					session.getAttribute("post-modification-auth-" + postId).equals(true);
						
	}
	
	
	public boolean authenticateAnonymousUser(Long postId, String pwd) {
		Post post = postRepo.findById(postId).get();
		
		if(post.getAnonymousUserPwd().equals(pwd)) {
			session.setAttribute("post-modification-auth-" + postId , true);
			return true;
		}
		
		return false;
	}
}

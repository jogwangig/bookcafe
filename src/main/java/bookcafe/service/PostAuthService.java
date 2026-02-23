package bookcafe.service;

import org.springframework.stereotype.Service;

import bookcafe.data.entity.Post;
import bookcafe.data.repository.PostRepository;
import bookcafe.security.CustomUserDetails;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostAuthService {

	
	private PostRepository postRepo;
	
	private HttpSession session;


	
	
	public boolean isAuthenticated(Long postId, CustomUserDetails userDetails) {
		Post post = postRepo.findById(postId).get();
		
		if(isPostWrittenByLoginUser(post)&&isLoginUser(userDetails) 
				&& isAuthenticatedUser(postId, userDetails.getId()))
			return true;
		
		if(!isPostWrittenByLoginUser(post) && isAuthenticatedAnonymousUser(postId))
			return true;
		
		return false;
		
		
		
	}
	
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
	
	
	public boolean isPostWrittenByLoginUser(Post post) {
		return (post.getAnonymousUsername() == null);
	}
	
	public void flushModificationAuth(Long postId) {
		if(session.getAttribute("post-modification-auth-" + postId) != null)
			session.removeAttribute("post-modification-auth-" + postId);
	}
	
	private boolean isLoginUser(CustomUserDetails userDetails) {
		return userDetails instanceof CustomUserDetails;
	}
}

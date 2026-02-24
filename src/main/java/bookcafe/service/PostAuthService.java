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


	
	
	public boolean isAuthenticated(Post post, CustomUserDetails userDetails) {
		
		if(isPostWrittenByLoginUser(post)&&isLoginUser(userDetails) 
				&& isAuthorOfPost(post, userDetails.getId()))
			return true;
		
		if(!isPostWrittenByLoginUser(post) && isAuthenticatedForModification(post))
			return true;
		
		return false;
		
		
		
	}
	
	public boolean isAuthorOfPost(Post post , Long userId) {
								
		return post.getUser().getId().equals(userId);
	}
	
	
	public boolean isAuthenticatedForModification(Post post) {
				
		return session.getAttribute("post-modification-auth-" + post.getId()) != null &&
					session.getAttribute("post-modification-auth-" + post.getId()).equals(true);
						
	}
	
	
	public boolean authenticateForModification(Long postId, String pwd) {
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

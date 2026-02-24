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


	
	
	public boolean isAuthenticatedForModification(Post post, CustomUserDetails userDetails) {
		
		if(!post.isWrittenByAnonymous()&&isLoginUser(userDetails) 
				&& isAuthorOfPost(post, userDetails.getId()))
			return true;
		
		if(post.isWrittenByAnonymous() && isAuthenticatedForModification(post))
			return true;
		
		return false;
		
		
		
	}
	
	
	public boolean authenticateForModification(Post post, String pwd) {
		
		if(post.getAnonymousUserPwd().equals(pwd)) {
			session.setAttribute("post-modification-auth-" + post.getId() , true);
			return true;
		}
		
		return false;
	}
	
	public boolean authenticateForDelete(Post post, String pwd) {
		
		if(post.getAnonymousUserPwd().equals(pwd)) {
			session.setAttribute("post-delete-auth-" + post.getId() , true);
			return true;
		}
		
		return false;
	}
	
	
	public void flushModificationAuth(Long postId) {
		if(session.getAttribute("post-modification-auth-" + postId) != null)
			session.removeAttribute("post-modification-auth-" + postId);
	}
	
	
	
	public void flushDeleteAuth(Long postId) {
		if(session.getAttribute("post-delete-auth-" + postId) != null)
			session.removeAttribute("post-delete-auth-" + postId);
	}
	
	
	private boolean isAuthorOfPost(Post post , Long userId) {
		
		return post.getUser().getId().equals(userId);
	}
	
	
	private boolean isAuthenticatedForModification(Post post) {
				
		return session.getAttribute("post-modification-auth-" + post.getId()) != null &&
					session.getAttribute("post-modification-auth-" + post.getId()).equals(true);
						
	}
	
	private boolean isLoginUser(CustomUserDetails userDetails) {
		return userDetails instanceof CustomUserDetails;
	}
}

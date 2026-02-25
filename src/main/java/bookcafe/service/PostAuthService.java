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
	
	private final String authName = "post-auth-";


	
	
	public boolean isAuthenticatedForEdit(Post post, CustomUserDetails userDetails) {
		
		if(!post.isWrittenByAnonymous()&&isLoginUser(userDetails) 
				&& isAuthorOfPost(post, userDetails.getId()))
			return true;
		
		if(post.isWrittenByAnonymous() && isAuthenticatedForEdit(post))
			return true;
		
		return false;	
		
	}
	
	
	public boolean authenticateForEdit(Post post, String pwd) {
		
		if(post.getAnonymousUserPwd().equals(pwd)) {
			session.setAttribute(authName + post.getId() , true);
			return true;
		}
		
		return false;
	}
	
	
	public void flushEditAuth(Long postId) {
		if(session.getAttribute("post-auth-" + postId) != null)
			session.removeAttribute("post-auth-" + postId);
	}
	
	
	
	private boolean isAuthorOfPost(Post post , Long userId) {
		
		return post.getUser().getId().equals(userId);
	}
	
	
	private boolean isAuthenticatedForEdit(Post post) {
				
		return session.getAttribute("post-auth-" + post.getId()) != null &&
					session.getAttribute("post-auth-" + post.getId()).equals(true);
						
	}
	
	
	
	private boolean isLoginUser(CustomUserDetails userDetails) {
		return userDetails instanceof CustomUserDetails;
	}
}

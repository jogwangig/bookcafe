package bookcafe.service;

import org.springframework.stereotype.Service;

import bookcafe.data.entity.Comment;
import bookcafe.data.entity.Post;
import bookcafe.util.ItemOwnerChecker;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostAuthService {
	
	private HttpSession session;
	
	private final String authName = "post-auth-";
	
	private final String commentAuthName = "comment-auth-";
	
	private ItemOwnerChecker itemOwnerChecker;

	
	public boolean isAuthenticatedForPostEdit(Post post) {
		
		if(!post.isWrittenByAnonymous()&&itemOwnerChecker.isOwnerOfItem(post))
			return true;
		
		if(post.isWrittenByAnonymous() && isAuthenticatedForPostPwd(post))
			return true;
		
		return false;	
		
	}
	
	public boolean isAuthenticatedForCommentEdit(Comment comment) {
		if(!comment.isWrittenByAnonymous() && itemOwnerChecker.isOwnerOfItem(comment))
			return true;
		
		if(comment.isWrittenByAnonymous() && isAuthenticatedForCommentPwd(comment))
			return true;
		
		return false;	
		
	}
	
	
	public boolean authenticateForPostPwd(Post post, String pwd) {
		
		if(post.getAnonymousUserPwd().equals(pwd)) {
			session.setAttribute(authName + post.getId() , true);
			return true;
		}
		
		return false;
	}
	
	public boolean authenticateForCommentPwd(Comment comment, String pwd) {
		
		if(comment.getAnonymousUserPwd().equals(pwd)) {
			session.setAttribute(commentAuthName + comment.getId() , true);
			return true;
		}
		
		return false;
	}
	
	
	public void flushPostPwdAuth(Post post) {
		if(session.getAttribute("post-auth-" + post.getId()) != null)
			session.removeAttribute("post-auth-" + post.getId());
	}
	
	
	public void flushCommentPwdAuth(Comment comment) {
		if(session.getAttribute(commentAuthName + comment.getId()) != null)
			session.removeAttribute(commentAuthName + comment.getId());
	}
	
	
	
	private boolean isAuthenticatedForPostPwd(Post post) {
				
		return session.getAttribute("post-auth-" + post.getId()) != null &&
					session.getAttribute("post-auth-" + post.getId()).equals(true);
						
	}
	
	private boolean isAuthenticatedForCommentPwd(Comment comment) {
		
		return session.getAttribute(commentAuthName + comment.getId()) != null &&
					session.getAttribute(commentAuthName + comment.getId()).equals(true);
						
	}
	
	
	
	
}

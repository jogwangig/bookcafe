package bookcafe.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import bookcafe.security.CustomUserDetails;

@Component
public class SecurityContextUtil {
	
	public Long getCurrentUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails))
			return null;
		
		if(authentication.getPrincipal() instanceof CustomUserDetails) {
			CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
			Long userId = userDetails.getId();
			return userId;
		}
		
		return null;
	}
}

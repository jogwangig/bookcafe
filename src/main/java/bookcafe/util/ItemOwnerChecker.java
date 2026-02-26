package bookcafe.util;

import java.util.Objects;

import org.springframework.stereotype.Component;

import bookcafe.data.ItemBase;
import bookcafe.exception.InaccessibleItemException;
import bookcafe.security.CustomUserDetails;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ItemOwnerChecker {
	
	private SecurityContextUtil securityContextUtil;
	
	public static boolean isOwnerOfItem(ItemBase item, CustomUserDetails userDetails) {
		System.out.println(userDetails.getId());
		
		System.out.println(item.getUser().getId());
		
		return item.getUser().getId().equals(userDetails.getId());
	}
	
	public void throwExceptionIfNotOwner(ItemBase item) {
		System.out.println(securityContextUtil.getCurrentUserId());
		System.out.println(item.getUser().getId());
		
		if(!Objects.equals(item.getUser().getId() , securityContextUtil.getCurrentUserId()))
			throw new InaccessibleItemException("접근이 불가능한 책입니다.");
	}

}

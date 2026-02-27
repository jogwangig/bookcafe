package bookcafe.util;

import java.util.Objects;

import org.springframework.stereotype.Component;

import bookcafe.data.ItemBase;
import bookcafe.exception.InaccessibleItemException;
import bookcafe.security.CustomUserDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor
public class ItemOwnerChecker {
	
	private SecurityContextUtil securityContextUtil;
	
	public static boolean isOwnerOfItem(ItemBase item, CustomUserDetails userDetails) {

		
		return item.getUser().getId().equals(userDetails.getId());
	}
	
	public void throwExceptionIfNotOwner(ItemBase item) {

		
		if(!Objects.equals(item.getUser().getId() , securityContextUtil.getCurrentUserId())) {
			String s = "사용자 : " + securityContextUtil.getCurrentUsername() + 
					" 가 " + "사용자 : " + item.getUser().getUsername() + " 의 아이템에 접근하려고 했습니다.";
			
//			System.out.println(s);
			
			log.warn(s);
			
			throw new InaccessibleItemException("접근이 불가능한 책입니다.");
		}
			
	}

}

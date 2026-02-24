package bookcafe.util;

import bookcafe.data.ItemBase;
import bookcafe.security.CustomUserDetails;

public class ItemOwnerChecker {
	
	public static boolean isOwnerOfItem(ItemBase item, CustomUserDetails userDetails) {
		return item.getUser().getId().equals(userDetails.getId());
	}

}

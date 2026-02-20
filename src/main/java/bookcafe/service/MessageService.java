package bookcafe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import bookcafe.data.entity.SiteUser;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MessageService {
	
	public void sendMessage(SiteUser user, String msgContent) {
		
	}
	
	public void sendMessage(List<SiteUser> users, String msgContent) {
		
	}
	
	
}

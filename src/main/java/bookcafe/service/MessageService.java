package bookcafe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import bookcafe.data.entity.Message;
import bookcafe.data.entity.SiteUser;
import bookcafe.data.repository.MessageRepository;
import bookcafe.data.repository.SiteUserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MessageService {
	
	private SiteUserRepository userRepo;
	
	private MessageRepository msgRepo;
	
	public void sendMessageFromAdmin(SiteUser user, String msgContent) {
		Message msg = Message.builder().sender(userRepo.getReferenceById(2l))
										.receipient(user).conntent(msgContent).build();
		
		msgRepo.save(msg);
		
	}
	
	public void sendMessageFromAdmin(List<SiteUser> users, String msgContent) {
		for(SiteUser u : users)
			sendMessageFromAdmin(u, msgContent);
		
	}
	
	
}

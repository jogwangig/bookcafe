package bookcafe.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import bookcafe.data.dto.BookClubDto;
import bookcafe.data.entity.BookClub;
import bookcafe.data.entity.BookClubComment;
import bookcafe.data.entity.BookClubParticipant;
import bookcafe.data.entity.SiteUser;
import bookcafe.data.repository.BookClubCommentRepository;
import bookcafe.data.repository.BookClubParticipantRepository;
import bookcafe.data.repository.BookClubRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookClubService {
	
	private BookClubRepository bookClubRepo;
	
	private BookClubParticipantRepository bookClubParticipantRepo;
	
	private BookClubCommentRepository bookClubCommentRepo;
	
	private MessageService msgService;
	
	
	public void createNewBookClub(BookClubDto bookClubDto) throws IOException {
		BookClub bm = toEntity(bookClubDto);
		
		System.out.println(bm);
		bookClubRepo.save(bm);
	}
	
	
	public void registerCommentToBookClub(Long bookClubId, BookClubComment newComment) {
		
		List<BookClubParticipant> participants = bookClubParticipantRepo.findByBookClubId(bookClubId);
		
		List<SiteUser> users = participants.stream().map(BookClubParticipant::getUser)
													.toList();
		
		BookClub bookClub = bookClubRepo.findById(bookClubId).get();
		
		String msgContent = bookClub.getName() + " 에 새로운 댓글이 달렸습니다.";
		
		newComment.setBookClub(bookClub);
		
		bookClubCommentRepo.save(newComment);
		
		msgService.sendMessageFromAdmin(users, msgContent);
	}
	
	
	
	private BookClub toEntity(BookClubDto bookClubDto) throws IOException {
		
		
		byte[] coverImg = (bookClubDto.getCoverImage().isEmpty())?null:bookClubDto.getCoverImage().getBytes();
		
		return BookClub.builder().name(bookClubDto.getName())
							.bookInfo(bookClubDto.getBookInfo())
							.coverImage(coverImg).build();
	}

}

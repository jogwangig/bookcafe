package bookcafe.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import bookcafe.data.dto.BookMeetingDto;
import bookcafe.data.entity.BookMeeting;
import bookcafe.data.repository.BookMeetingRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookMeetingService {
	
	private BookMeetingRepository bookMeetingRepo;

	
	public void createNewBookMeeting(BookMeetingDto bookMeetingDto) throws IOException {
		BookMeeting bm = toEntity(bookMeetingDto);
		
		System.out.println(bm);
		bookMeetingRepo.save(bm);
	}
	
	private BookMeeting toEntity(BookMeetingDto bookMeetingDto) throws IOException {
		
		
		byte[] coverImg = (bookMeetingDto.getCoverImage().isEmpty())?null:bookMeetingDto.getCoverImage().getBytes();
		
		return BookMeeting.builder().name(bookMeetingDto.getName())
							.bookInfo(bookMeetingDto.getBookInfo())
							.coverImage(coverImg).build();
	}

}

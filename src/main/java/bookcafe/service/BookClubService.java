package bookcafe.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import bookcafe.data.dto.BookClubDto;
import bookcafe.data.entity.BookClub;
import bookcafe.data.repository.BookClubRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookClubService {
	
	private BookClubRepository bookClubRepo;

	
	public void createNewBookMeeting(BookClubDto bookClubDto) throws IOException {
		BookClub bm = toEntity(bookClubDto);
		
		System.out.println(bm);
		bookClubRepo.save(bm);
	}
	
	private BookClub toEntity(BookClubDto bookClubDto) throws IOException {
		
		
		byte[] coverImg = (bookClubDto.getCoverImage().isEmpty())?null:bookClubDto.getCoverImage().getBytes();
		
		return BookClub.builder().name(bookClubDto.getName())
							.bookInfo(bookClubDto.getBookInfo())
							.coverImage(coverImg).build();
	}

}

package bookcafe.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import bookcafe.data.dto.display.BookDisplayDto;
import bookcafe.data.dto.display.BookShelfWithBooksDisplayDto;
import bookcafe.data.dto.display.BookShelfWithBooksDisplayFlatDto;
import bookcafe.data.repository.BookShelfRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookShelfDisplayService {
	
	private BookShelfRepository  bookShelfRepo;
		
	public List<BookShelfWithBooksDisplayDto> getAllBookShelfDtosForDisplay(long userId){
		List<BookShelfWithBooksDisplayFlatDto> flatDtos = bookShelfRepo.findAllDisplayDtosWithBooksByUserId(userId);
		
		return generateFromFlat(flatDtos);
	
	}
	
	public BookShelfWithBooksDisplayDto getBookShelfDtosForDisplay(long id){
		List<BookShelfWithBooksDisplayFlatDto> flatDtos = bookShelfRepo.findDisplayDtosWithBooksById(id);
		
		return generateFromFlat(flatDtos).get(0);
	}
	
	
	
	private List<BookShelfWithBooksDisplayDto> generateFromFlat(List<BookShelfWithBooksDisplayFlatDto> flatDtos){
		
		Map<Long, List<BookShelfWithBooksDisplayFlatDto>> bookShelfDtosGroupedById = flatDtos.stream()
										.collect(Collectors.groupingBy(BookShelfWithBooksDisplayFlatDto::getId));

		return bookShelfDtosGroupedById.values().stream()
					.map(list -> {
							BookShelfWithBooksDisplayFlatDto first = list.get(0);
					
							List<BookDisplayDto> bookDisplayDtos = list.stream()
										.filter(bsd->bsd.getBookId() != null)
										.map(bsd -> new BookDisplayDto(bsd.getBookId(), bsd.getBookTitle()))
										.toList();
					
							return new BookShelfWithBooksDisplayDto(first.getId(), first.getName(), bookDisplayDtos);
					}).toList();
	
	}
	
	
	
}

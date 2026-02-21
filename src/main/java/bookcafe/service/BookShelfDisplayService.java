package bookcafe.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import bookcafe.data.dto.BookDto;
import bookcafe.data.dto.display.BookShelfWithBooksDto;
import bookcafe.data.dto.display.BookShelfWithBooksFlatDto;
import bookcafe.data.repository.BookShelfRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookShelfDisplayService {
	
	private BookShelfRepository  bookShelfRepo;
		
	public List<BookShelfWithBooksDto> getAllBookShelfDtosForDisplay(long userId){
		List<BookShelfWithBooksFlatDto> flatDtos = bookShelfRepo.findByUserIdWithBooks(userId);
		
		return generateFromFlat(flatDtos);
	
	}
	
	public BookShelfWithBooksDto getBookShelfDtosForDisplay(long id){
		List<BookShelfWithBooksFlatDto> flatDtos = bookShelfRepo.findByIdWithBooks(id);
		
		return generateFromFlat(flatDtos).get(0);
	}
	
	
	
	private List<BookShelfWithBooksDto> generateFromFlat(List<BookShelfWithBooksFlatDto> flatDtos){
		
		Map<Long, List<BookShelfWithBooksFlatDto>> bookShelfDtosGroupedById = flatDtos.stream()
										.collect(Collectors.groupingBy(BookShelfWithBooksFlatDto::getId));

		return bookShelfDtosGroupedById.values().stream()
					.map(list -> {
							BookShelfWithBooksFlatDto first = list.get(0);
					
							List<BookDto> bookDtos = list.stream()
										.filter(bsd->bsd.getBookId() != null)
										.map(bsd -> new BookDto(bsd.getBookId(), bsd.getBookTitle()))
										.toList();
					
							return new BookShelfWithBooksDto(first.getId(), first.getName(), bookDtos);
					}).toList();
	
	}
	
	
	
}

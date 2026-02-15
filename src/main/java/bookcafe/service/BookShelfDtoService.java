package bookcafe.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import bookcafe.data.dto.BookDto;
import bookcafe.data.dto.BookShelfWithBooksFlatDto;
import bookcafe.data.dto.BookShelfWithBooksDto;
import bookcafe.data.repository.BookShelfRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookShelfDtoService {
	
	private BookShelfRepository  bookShelfRepo;
	
	public List<BookShelfWithBooksDto> getDtoForBookShelfDisplay(long userId){
		List<BookShelfWithBooksFlatDto> flatDtos = bookShelfRepo.findByUserIdWithBooks(userId);
		
		Map<Long, List<BookShelfWithBooksFlatDto>> bookShelfDtosGroupedById = flatDtos.stream()
																	.collect(Collectors.groupingBy(BookShelfWithBooksFlatDto::getId));
		
		return bookShelfDtosGroupedById.values().stream()
						.map(list -> {
							BookShelfWithBooksFlatDto first = list.get(0);
							
							List<BookDto> bookDtos = list.stream().map(bsd -> {
								return new BookDto(bsd.getBookId(), bsd.getBookTitle());
							}).toList();
							
							return new BookShelfWithBooksDto(first.getId(), first.getName(), bookDtos);
						}).toList();
	}
}

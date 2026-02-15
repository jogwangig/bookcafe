package bookcafe.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import bookcafe.data.dto.BookDto;
import bookcafe.data.dto.BookShelfDto;
import bookcafe.data.dto.BookShelfWithBooksDto;
import bookcafe.data.repository.BookShelfRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookShelfDtoService {
	
	private BookShelfRepository  bookShelfRepo;
	
	public List<BookShelfWithBooksDto> getDtoForBookShelfDisplay(long userId){
		List<BookShelfDto> bookShelfDtos = bookShelfRepo.findByUserIdWithBooks(userId);
		
		Map<Long, List<BookShelfDto>> bookShelfDtosGroupedById = bookShelfDtos.stream()
																	.collect(Collectors.groupingBy(BookShelfDto::getId));
		
		return bookShelfDtosGroupedById.values().stream()
						.map(list -> {
							BookShelfDto first = list.get(0);
							
							List<BookDto> bookDtos = list.stream().map(bsd -> {
								return new BookDto(bsd.getBookId(), bsd.getBookTitle());
							}).toList();
							
							return new BookShelfWithBooksDto(first.getId(), first.getName(), bookDtos);
						}).toList();
	}
}

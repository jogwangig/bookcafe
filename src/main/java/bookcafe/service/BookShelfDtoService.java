package bookcafe.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import bookcafe.data.dto.BookDto;
import bookcafe.data.dto.BookShelfWithBooksDto;
import bookcafe.data.dto.BookShelfWithBooksFlatDto;
import bookcafe.data.repository.BookRepository;
import bookcafe.data.repository.BookShelfRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookShelfDtoService {
	
	private BookShelfRepository  bookShelfRepo;
		
	public List<BookShelfWithBooksDto> getDtoForBookShelfDisplay(long userId){
		List<BookShelfWithBooksFlatDto> flatDtos = bookShelfRepo.findByUserIdWithBooks(userId);
		
//		List<Long> bookShelfIds = bookShelfRepo.findByUserId(userId).stream().map(BookShelf::getId).toList();
		
//		List<BookWithBookShelfIdDto> books = bookRepo.findBookWithBookShelfIdDtos(bookShelfIds);
		
//		books.forEach(System.out::println);
		
//		Map<Long, List<BookWithBookShelfIdDto>> booksGrouped = books.stream().collect(Collectors.groupingBy(BookWithBookShelfIdDto::getBookShelfId));
		
//		booksGrouped.values().forEach(System.out::println);
				
		Map<Long, List<BookShelfWithBooksFlatDto>> bookShelfDtosGroupedById = flatDtos.stream()
																	.collect(Collectors.groupingBy(BookShelfWithBooksFlatDto::getId));
		
		return bookShelfDtosGroupedById.values().stream()
						.map(list -> {
							BookShelfWithBooksFlatDto first = list.get(0);
							
							List<BookDto> bookDtos = list.stream()
							.filter(bsd->bsd.getBookId() != null)
							.map(bsd -> {
								return new BookDto(bsd.getBookId(), bsd.getBookTitle());
							}).toList();
							
							return new BookShelfWithBooksDto(first.getId(), first.getName(), bookDtos);
						}).toList();
	}
}

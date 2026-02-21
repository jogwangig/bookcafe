package bookcafe.data.dto.creation;

import bookcafe.data.entity.BookShelf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookShelfCreationDto implements CreationDto<BookShelf> {
	private String name;
	
	public BookShelf toEntity() {
		return BookShelf.builder().name(name).build();
	}
}

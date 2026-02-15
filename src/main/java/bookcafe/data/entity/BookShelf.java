package bookcafe.data.entity;

import java.util.List;

import bookcafe.data.ItemBase;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookShelf extends ItemBase {
	
	private String name;
	
	@OneToMany(mappedBy = "bookShelf")
	private List<Book> books;
	
	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class BookShelfDTO{
		private long id;
		
		private String name;
		
		private List<Book> books;
	}

}

package bookcafe.data.entity;

import java.util.List;

import bookcafe.data.ItemBase;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookShelf extends ItemBase {
	
	private String name;
	
	@OneToMany(mappedBy = "bookShelf", fetch = FetchType.LAZY)
	private List<Book> books;

}

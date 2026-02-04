package bookcafe.data.entity;

import bookcafe.data.ItemBase;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookShelf extends ItemBase {
	
	private String name;

}

package bookcafe.data.entity;

import bookcafe.data.ItemBase;
import bookcafe.data.valueobject.BookInfo;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public class Book extends ItemBase{
	
	@Embedded
	private BookInfo bookInfo;
	
	@ManyToOne
	private BookShelf bookShelf;
	
}

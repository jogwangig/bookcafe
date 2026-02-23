package bookcafe.data.entity;

import java.util.List;

import bookcafe.data.ItemBase;
import bookcafe.data.valueobject.BookInfo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BOOK_SHELF_ID")
	private BookShelf bookShelf;
	
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private byte[] coverImage;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "book")
	private List<ReadingRecord> readingRecords;
	
}

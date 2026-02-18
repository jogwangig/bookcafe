package bookcafe.data.entity;

import bookcafe.data.ItemBase;
import bookcafe.data.valueobject.BookInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
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
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookMeeting extends ItemBase {
	private String name;
	
	@Embedded
	private BookInfo bookInfo;
	
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private byte[] coverImage;
	
	
}

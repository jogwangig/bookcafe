package bookcafe.data.entity;

import bookcafe.data.ItemBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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
public class ReadingRecord extends ItemBase{
	@Column(length = 1000)
	private String content;
	
	@ManyToOne
	private Book book;
}

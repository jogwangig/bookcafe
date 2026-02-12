package bookcafe.data.entity;

import bookcafe.data.ItemBase;
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
public class Post extends ItemBase{
	
	private String title;
	
	private String content;
	
	@ManyToOne
	private Board board;
}

package bookcafe.data.entity;

import bookcafe.data.ItemBase;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Post extends ItemBase{
	
	@ManyToOne
	private Board board;
}

package bookcafe.data.entity;

import bookcafe.data.ItemBase;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Reply extends ItemBase {
	
	@ManyToOne
	private Comment comment;

}

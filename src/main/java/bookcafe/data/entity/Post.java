package bookcafe.data.entity;

import java.util.List;

import bookcafe.data.ItemBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
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
public class Post extends ItemBase{
	
	private String anonymousUsername;
	
	private String anonymousUserPwd;
	
	private String title;
	
	@Column(length=1000)
	private String content;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Board board;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.REMOVE)
	private List<Comment> comments;
	
	
	public boolean isWrittenByAnonymous() {
		return this.getAnonymousUsername() != null;
	}
}

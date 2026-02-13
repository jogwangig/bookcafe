package bookcafe.data.entity;

import bookcafe.data.ItemBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	
	private String anonymousUsername;
	
	private String anonymousUserPwd;
	
	private String title;
	
	@Column(length=1000)
	private String content;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Board board;
}

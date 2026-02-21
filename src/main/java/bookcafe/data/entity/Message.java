package bookcafe.data.entity;

import bookcafe.data.Base;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message extends Base{
	
	@ManyToOne(fetch = FetchType.LAZY)
	private SiteUser sender;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private SiteUser receipient;
	
	private String conntent;
}

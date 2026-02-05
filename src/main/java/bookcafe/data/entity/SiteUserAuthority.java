package bookcafe.data.entity;

import bookcafe.data.Base;
import bookcafe.data.ItemBase;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SiteUserAuthority extends ItemBase{
	
	@Enumerated(EnumType.STRING)
	private AuthorityType authorityType;
	
	
	public enum AuthorityType{
		ADMIN, NORMAL
	}
	

}

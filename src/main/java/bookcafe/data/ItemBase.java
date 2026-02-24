package bookcafe.data;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import bookcafe.data.entity.SiteUser;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;

@Getter
@MappedSuperclass
@ToString(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public abstract class ItemBase extends Base{
	
	@CreatedBy
	@ManyToOne(fetch = FetchType.LAZY)
	private SiteUser user;

}

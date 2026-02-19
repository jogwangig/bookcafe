package bookcafe.data.entity;

import bookcafe.data.ItemBase;
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
@AllArgsConstructor
@NoArgsConstructor
public class BookClubParticipant extends ItemBase {
	
	@ManyToOne(fetch = FetchType.LAZY)
	private BookClub bookClub;

}

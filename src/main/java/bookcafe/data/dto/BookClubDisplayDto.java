package bookcafe.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookClubDisplayDto {
	
	private long id;
	
	private String name;
	
	private boolean isParticipated;
}

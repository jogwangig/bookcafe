package bookcafe.data.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReadingRecordDto {
	private LocalDateTime createdAt;
	private String content;
	private String bookTitle;

}

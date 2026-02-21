package bookcafe.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import bookcafe.data.dto.display.PostPageDto;
import bookcafe.data.repository.PostRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BoardPagingService {
	
	private PostRepository postRepo;
	
	
	public Page<PostPageDto> getPostPageOfBoard(long boardId, int pageNum) {
		
		int adjustedpageNum = pageNum-1;
				
		PageRequest pageRequest = PageRequest.of(adjustedpageNum ,5 , Sort.by("cratedAt").descending());
		Page<PostPageDto> postPageDto = postRepo.findPostPageByBoardId(boardId, pageRequest);
		
		return postPageDto;
		
	}
}

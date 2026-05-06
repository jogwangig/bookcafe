package bookcafe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import bookcafe.data.dto.display.PostDisplayDto;
import bookcafe.data.repository.PostRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BoardPagingService {
	
	private PostRepository postRepo;
	
	@Value("${paging.default.size}") 
	private int pageSize;
	
	@Autowired
	public BoardPagingService(PostRepository postRepo) {
		this.postRepo = postRepo;
	}
	
	
	public Page<PostDisplayDto> getPostPageOfBoard(String boardName, int pageNum) {
		
		int adjustedpageNum = pageNum-1;
		
				
		PageRequest pageRequest = PageRequest.of(adjustedpageNum , pageSize , Sort.by("cratedAt").descending());
//		Page<PostDisplayDto> postDisplayDto = postRepo.findPostPageByBoardId(boardId, pageRequest);
		
		Page<PostDisplayDto> postDisplayDto = postRepo.findPostPageByBoardName(boardName, pageRequest);
		
		return postDisplayDto;
		
	}
}

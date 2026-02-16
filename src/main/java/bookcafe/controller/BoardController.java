package bookcafe.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bookcafe.data.dto.PostPageDto;
import bookcafe.data.entity.Board;
import bookcafe.data.repository.BoardRepository;
import bookcafe.data.repository.PostRepository;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/board")
@AllArgsConstructor
public class BoardController {
	
	private BoardRepository boardRepo;
	
	private PostRepository postRepo;
	
	@GetMapping
	public String main(Model model) {
		Board board = boardRepo.findByName("main");
		
		PageRequest pageRequest = PageRequest.of(0 ,5 , Sort.by("cratedAt").descending());
		Page<PostPageDto> postPageDto = postRepo.findByBoardId(board.getId(), pageRequest);
		
		
		model.addAttribute("boardId", board.getId());
		model.addAttribute("board", board);
		model.addAttribute("postPage", postPageDto);
		
		return "/board";
	}
	
	@GetMapping(params = {"boardId", "pageNum"})
	public String displayBoard(Model model, @RequestParam("boardId") long boardId, 
			@RequestParam("pageNum")int pageNum) {
		
		int adjustedpageNum = pageNum-1;
		
		Board board = boardRepo.findById(boardId).get();
		
		PageRequest pageRequest = PageRequest.of(adjustedpageNum ,10 , Sort.by("cratedAt").descending());
		Page<PostPageDto> postPageDto = postRepo.findByBoardId(board.getId(), pageRequest);
		
		
		model.addAttribute("boardId", board.getId());
		model.addAttribute("board", board);
		model.addAttribute("postPage", postPageDto);
		
		return "/board";
	}
	
	
	
	@GetMapping("/board-list")
	public String displayBoards(Model model) {
		List<Board> boards = boardRepo.findAll();
		model.addAttribute("boards", boards);
		
		return "/board-list";
	}
	
	@GetMapping("/create")
	public String getBoardCreationForm(Model model) {
		model.addAttribute("board", new Board());
		return "/form/board-creation-form";
	}
	
	@PostMapping("/create")
	public String processBoardCreationForm(@ModelAttribute("board")Board newBoard) {
		boardRepo.save(newBoard);
		return "redirect:/";
	}

}

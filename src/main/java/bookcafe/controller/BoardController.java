package bookcafe.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bookcafe.data.dto.display.BoardDisplayDto;
import bookcafe.data.dto.display.PostDisplayDto;
import bookcafe.data.entity.Board;
import bookcafe.data.repository.BoardRepository;
import bookcafe.service.BoardPagingService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/board")
@AllArgsConstructor
public class BoardController {
	
	private BoardRepository boardRepo;
		
	private BoardPagingService boardPagingService;
	
	
	@GetMapping
	public String displayBoard(Model model,
			@RequestParam(value = "boardName", required = false, defaultValue = "main")String boardName, 
			@RequestParam(value = "pageNum", required = false, defaultValue = "${paging.default.page}" )int pageNum) {
		
		BoardDisplayDto board = boardRepo.findDtoByName(boardName);
		
		Page<PostDisplayDto> postDisplayDto = boardPagingService.getPostPageOfBoard(boardName, pageNum);
		
		
		model.addAttribute("board", board);
		model.addAttribute("postPage", postDisplayDto);
		
		return "/board";
	}
	
	
	
	@GetMapping("/board-list")
	public String displayBoards(Model model) {
		List<BoardDisplayDto> boards = boardRepo.findAllDtos();
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
	
	
//	@ModelAttribute("boardId")
//	public long addBoardId(@RequestParam(value = "boardId", required = false)Long boardId) {
//		if(boardId == null) return 2;
//		return boardId;
//	}
	

}

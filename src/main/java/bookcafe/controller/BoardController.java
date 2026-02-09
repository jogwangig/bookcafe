package bookcafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import bookcafe.data.entity.Board;
import bookcafe.data.repository.BoardRepository;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/board")
@AllArgsConstructor
public class BoardController {
	
	private BoardRepository boardRepo;
	
	@GetMapping
	public String main() {
		return "/board";
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

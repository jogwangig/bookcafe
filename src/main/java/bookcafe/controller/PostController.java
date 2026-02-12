package bookcafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bookcafe.data.entity.Post;
import bookcafe.data.repository.BoardRepository;
import bookcafe.data.repository.PostRepository;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/post")
public class PostController {
	
	private PostRepository postRepo;
	
	private BoardRepository boardRepo;
	
	@GetMapping("/create")
	public String createPost(Model model, @RequestParam("boardId")long boardId) {
		
		model.addAttribute("post", new Post());
		model.addAttribute("boardId", boardId);

		return "/form/post-creation-form";
	}
	
	@PostMapping("/create")
	public String processPostCreationForm(@ModelAttribute("post")Post post, @RequestParam("boardId")long boardId) {
		
		post.setBoard(boardRepo.getReferenceById(boardId));
		postRepo.save(post);

		return "redirect:/board";
	}

}

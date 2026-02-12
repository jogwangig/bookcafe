package bookcafe.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import bookcafe.data.entity.Post;
import bookcafe.data.repository.PostRepository;
import bookcafe.security.CustomUserDetails;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/post")
public class PostController {
	
	private PostRepository postRepo;
	
	@GetMapping("/create")
	public String createBook(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		
		model.addAttribute("post", new Post());

		return "/form/post-creation-form";
	}

}

package bookcafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bookcafe.data.dto.creation.PostCreationDto;
import bookcafe.data.entity.Comment;
import bookcafe.data.entity.Post;
import bookcafe.data.repository.BoardRepository;
import bookcafe.data.repository.CommentRepository;
import bookcafe.data.repository.PostRepository;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/post")
public class PostController {
	
	private PostRepository postRepo;
	
	private CommentRepository commentRepo;
	
	private BoardRepository boardRepo;
	
	@GetMapping(params = "postId")
	public String displayPostById(Model model, @RequestParam("postId") long postId) {
		Post post = postRepo.findById(postId).get();
		
		model.addAttribute("newComment", new Comment());
		model.addAttribute("post", post);
		model.addAttribute("boardId", post.getBoard().getId());
		model.addAttribute("comments", commentRepo.findByPostId(postId));
		
		return "post";
	}
	
	
	@GetMapping("/create")
	public String getPostCreationForm(Model model, @RequestParam("boardId")long boardId) {
		
		model.addAttribute("post", new PostCreationDto());
		model.addAttribute("boardId", boardId);

		return "/form/post-creation-form";
	}
	
	@PostMapping("/create")
	public String processPostCreationForm(@ModelAttribute("post")PostCreationDto post, @RequestParam("boardId")long boardId) {
		
		Post newPost = post.toEntity();
		
//		System.out.println(post.getBoardId());
		
		newPost.setBoard(boardRepo.getReferenceById(boardId));
		postRepo.save(newPost);

		return "redirect:/board";
	}

}

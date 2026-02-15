package bookcafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bookcafe.data.entity.Comment;
import bookcafe.data.repository.CommentRepository;
import bookcafe.data.repository.PostRepository;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/comment")
@AllArgsConstructor
public class CommentController {
	
	private CommentRepository commentRepo;
	
	private PostRepository postRepo;
	
	
	@PostMapping(path = "/create" , params = "postId")
	public String processCommentCreationForm(@ModelAttribute("newComment")Comment comment, @RequestParam("postId")long postId) {
//		commentRepo.save(Comment.builder().
//				post(postRepo.getReferenceById(postId)).
//				content(comment.getContent()).build());
		
		comment.setPost(postRepo.getReferenceById(postId));
		
		commentRepo.save(comment);
		
		return "redirect:/post?postId=" + postId;
	}

}

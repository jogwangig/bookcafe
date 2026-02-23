package bookcafe.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bookcafe.data.dto.PostDetailDto;
import bookcafe.data.dto.creation.PostCreationDto;
import bookcafe.data.entity.Comment;
import bookcafe.data.entity.Post;
import bookcafe.data.repository.BoardRepository;
import bookcafe.data.repository.CommentRepository;
import bookcafe.data.repository.PostRepository;
import bookcafe.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/post")
public class PostController {
	
	private PostRepository postRepo;
	
	private CommentRepository commentRepo;
	
	private BoardRepository boardRepo;
	
	@GetMapping(params = "postId")
	public String displayPostById(Model model, @RequestParam("postId") long postId, HttpServletRequest req) {
		
		
		PostDetailDto post = postRepo.findPostDetailDtoById(postId);
						
		model.addAttribute("post", post);
		model.addAttribute("newComment", new Comment());
		model.addAttribute("boardId", post.getBoardId());
		model.addAttribute("comments", commentRepo.findByPostId(postId));
		
		return "post";
	}
	
	
	@GetMapping("/create")
	public String getPostCreationForm(Model model, @RequestParam("boardId")long boardId) {
		
		PostCreationDto postCreationDto = new PostCreationDto();
		postCreationDto.setBoardId(boardId);
		
		model.addAttribute("post", postCreationDto);
		model.addAttribute("boardId", boardId);

		return "/form/post-creation-form";
	}
	
	@PostMapping("/create")
	public String processPostCreationForm(@ModelAttribute("post")PostCreationDto post, @RequestParam("boardId")long boardId) {
		
		Post newPost = post.toEntity();
		
		System.out.println(post.getBoardId());
		
		newPost.setBoard(boardRepo.getReferenceById(post.getBoardId()));
		postRepo.save(newPost);

		return "redirect:/board?boardId="+boardId;
	}
	
	
	@GetMapping("/modify")
	public String getPostModificationForm(Model model, 
			@RequestParam("postId")long postId, HttpSession session,
			@AuthenticationPrincipal CustomUserDetails userDetails) {
		
		Post post = postRepo.findById(postId).get();
		
		if(post.getAnonymousUsername() == null) {
			if(!(userDetails instanceof CustomUserDetails))
				return "redirect:/post?postId="+postId;
				
			Long userId = userDetails.getId();
			
			if(post.getUser().getId().equals(userId)) {
				
				PostCreationDto postCreationDto = postRepo.findCreationDtoById(postId);

				model.addAttribute("postId", postId);
				model.addAttribute("post", postCreationDto);
				
			}else {
				return "redirect:/post?postId="+postId;
			}
			
		}else {
			
			if(session.getAttribute("post-modification-auth-" + postId) != null &&
					session.getAttribute("post-modification-auth-" + postId).equals(true)) {
				
				session.removeAttribute("post-modification-auth-" + postId);
				
				PostCreationDto postCreationDto = postRepo.findCreationDtoById(postId);

				model.addAttribute("postId", postId);
				model.addAttribute("post", postCreationDto);
				
				
				
				
			}else {
				model.addAttribute("postId", postId);
				return "/form/post-modification-auth-form";
			}
			
		}
		

		return "/form/post-creation-form";
	}
	
	
	@PostMapping("/modify")
	public String processPostModificationForm(@ModelAttribute("post")PostCreationDto postCreationDto ,@RequestParam("postId")long postId) {
		
		Post post = postRepo.findById(postId).get();
		
		post.setAnonymousUsername(postCreationDto.getAnonymousUsername());
		post.setAnonymousUserPwd(postCreationDto.getAnonymousUserPwd());
		post.setTitle(postCreationDto.getTitle());
		post.setContent(postCreationDto.getContent());
		
		
		postRepo.save(post);

		return "redirect:/";
	}
	
	
	@PostMapping("/modify/auth")
	public String processPostModificationForm(@RequestParam("postId")long postId,
			@ModelAttribute("password")String pwd, HttpSession session) {
		
		Post post = postRepo.findById(postId).get();
		
		
		if(post.getAnonymousUserPwd().equals(pwd)) {
			session.setAttribute("post-modification-auth-" + postId , true);
			return "redirect:/post/modify?postId="+postId;
		}

		return "redirect:/";
	}

}

package bookcafe.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import bookcafe.data.dto.BookMeetingDto;
import bookcafe.service.BookMeetingService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/book-meeting")
public class BookMeetingController {
	
	private BookMeetingService bookMeetingService;
	
	@GetMapping("/create")
	public String getBookMeetingCreationForm(Model model) {
		model.addAttribute("bookMeeting", new BookMeetingDto());
		return "/form/book-meeting-creation-form";
	}
	
	
	@PostMapping("/create")
	public String processBookMeetingCreationForm(BookMeetingDto bookMeetingDto) throws IOException {
//		System.out.print(bookMeetingDto);
		bookMeetingService.createNewBookMeeting(bookMeetingDto);
		return "redirect:/";
	}
}

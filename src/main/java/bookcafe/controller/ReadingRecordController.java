package bookcafe.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import bookcafe.data.entity.Book;
import bookcafe.data.entity.ReadingRecord;
import bookcafe.data.repository.BookRepository;
import bookcafe.data.repository.ReadingRecordRepository;
import bookcafe.data.valueobject.BookInfo;
import bookcafe.security.CustomUserDetails;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/reading-record")
@AllArgsConstructor
public class ReadingRecordController {
	
	BookRepository bookRepo;
	
	ReadingRecordRepository readingRecordRepo;
	
	
	@GetMapping("/create")
	public String createReadingRecord(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		model.addAttribute("readingRecord", new ReadingRecord());
		model.addAttribute("books", bookRepo.findByUserId(userDetails.getId()));
		return "/form/reading-record-creation-form";
	}
	
	@PostMapping("/create")
	public String createBook(@ModelAttribute("readingRecord")ReadingRecord readingRecord, @ModelAttribute("book-select")Long bookId) {
		
		readingRecordRepo.save(ReadingRecord.builder().
				content(readingRecord.getContent()).
				book(bookRepo.getReferenceById(bookId)).build());
		return "redirect:/";
	}

}

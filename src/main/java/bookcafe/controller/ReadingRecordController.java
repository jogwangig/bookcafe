package bookcafe.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bookcafe.data.entity.ReadingRecord;
import bookcafe.data.repository.BookRepository;
import bookcafe.data.repository.ReadingRecordRepository;
import bookcafe.security.CustomUserDetails;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/reading-record")
@AllArgsConstructor
public class ReadingRecordController {
	
	BookRepository bookRepo;
	
	ReadingRecordRepository readingRecordRepo;
	
	
	
	@GetMapping(params = "bookId")
	public String displayReadingRecordsByBookId(Model model, @RequestParam("bookId")long bookId) {
		model.addAttribute("readingRecords", readingRecordRepo.findByBookId(bookId));
		return "/reading-record-list";
	}
	
	
	
	@GetMapping
	public String displayAllReadingRecords(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		model.addAttribute("readingRecords", readingRecordRepo.findByUserId(userDetails.getId()));
		return "/reading-record-list";
	}
	
	
	
	@GetMapping("/create")
	public String getReadingRecordCreationForm(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		model.addAttribute("readingRecord", new ReadingRecord());
		model.addAttribute("books", bookRepo.findByUserId(userDetails.getId()));
		return "/form/reading-record-creation-form";
	}
	
	
	
	@PostMapping("/create")
	public String processReadingRecordCreationForm(@ModelAttribute("readingRecord")ReadingRecord readingRecord, @ModelAttribute("book-select")Long bookId) {
		
		readingRecordRepo.save(ReadingRecord.builder().
				content(readingRecord.getContent()).
				book(bookRepo.getReferenceById(bookId)).build());
		return "redirect:/";
	}

}

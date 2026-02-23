package bookcafe.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bookcafe.data.dto.ReadingRecordDetailDto;
import bookcafe.data.dto.creation.ReadingRecordCreationDto;
import bookcafe.data.dto.display.BookDisplayDto;
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
	public String displayReadingRecordsOfBook(Model model, @RequestParam("bookId")long bookId) {
		PageRequest pageRequest = PageRequest.of(0 ,20 , Sort.by("cratedAt").descending());
		
		
		Page<ReadingRecordDetailDto> readingRecordPage = readingRecordRepo.findByBookId(bookId, pageRequest);

		model.addAttribute("readingRecordPage", readingRecordPage);
		return "/reading-record-list";
	}
	
	
	
	@GetMapping
	public String displayAllReadingRecords(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		PageRequest pageRequest = PageRequest.of(0 ,20 , Sort.by("cratedAt").descending());
		
		Page<ReadingRecordDetailDto> readingRecordPage = readingRecordRepo.findByUserId(userDetails.getId(), pageRequest);

		model.addAttribute("readingRecordPage", readingRecordPage);

		return "/reading-record-list";
	}
	
	
	
	@GetMapping("/create")
	public String getReadingRecordCreationForm(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		List<BookDisplayDto> books = bookRepo.findDisplayDtosByUserId(userDetails.getId());
				
		model.addAttribute("readingRecord", new ReadingRecordCreationDto());
		model.addAttribute("books", books);
		return "/form/reading-record-creation-form";
	}
	
	
	
	@PostMapping("/create")
	public String processReadingRecordCreationForm(@ModelAttribute("readingRecord")ReadingRecordCreationDto readingRecordCreationDto, 
			@ModelAttribute("bookId")Long bookId) {
		
		ReadingRecord r = ReadingRecord.builder().content(readingRecordCreationDto.getContent())
												.book(bookRepo.getReferenceById(readingRecordCreationDto.getBookId())).build();
		
		readingRecordRepo.save(r);
		
		return "redirect:/reading-record?bookId=" + bookId;
	}
	
	
	@GetMapping("/modify")
	public String getReadingRecordModificationForm(Model model, @AuthenticationPrincipal CustomUserDetails userDetails,
			@RequestParam("readingRecordId")Long readingRecordId) {
		
		
		List<BookDisplayDto> books = bookRepo.findDisplayDtosByUserId(userDetails.getId());
		
		ReadingRecordCreationDto readingRecord = readingRecordRepo.findCreationDtoById(readingRecordId);
				
		model.addAttribute("readingRecord", readingRecord);
		model.addAttribute("readingRecordId", readingRecordId);
		model.addAttribute("books", books);
		return "/form/reading-record-creation-form";
	}
	
	@PostMapping("/modify")
	public String processReadingRecordModificationForm(@ModelAttribute("readingRecord")ReadingRecordCreationDto readingRecordCreationDto, 
			@RequestParam("readingRecordId")Long readingRecordId) {
		
		ReadingRecord r = readingRecordRepo.findById(readingRecordId).get();
		
		r.setContent(readingRecordCreationDto.getContent());
		r.setBook(bookRepo.getReferenceById(readingRecordCreationDto.getBookId()));
		
		readingRecordRepo.save(r);
		
		return "redirect:/reading-record?bookId=" + readingRecordCreationDto.getBookId();
	}
	
	@ResponseBody
	@DeleteMapping("/delete")
	public String delete(@RequestParam("readingRecordId")Long readingRecordId) {
		readingRecordRepo.deleteById(readingRecordId);
		
		return "삭제 성공";
	}

}

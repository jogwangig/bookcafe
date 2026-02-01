package bookcafe.data.valueobject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookInfoTest {
	
	private BookInfo bookInfo;
	
	@BeforeEach
	void setUp() {
		bookInfo = new BookInfo();
		bookInfo.setTitle("How To Read A Book");
		bookInfo.setISBN("0671212095");
	}
	
	@Test
	void getterTest() {
		assertEquals("How To Read A Book", bookInfo.getTitle());
		assertEquals("0671212095", bookInfo.getISBN());
	}

}

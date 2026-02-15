package bookcafe.service;


import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenLibraryApiService {
	private RestTemplate restTemplate = new RestTemplate();
	
	private final String coverImgUrl = "https://covers.openlibrary.org/b/id/";
	
	public byte[] fetchCoverImgByCoverId(String coverId) {
		
		String coverImgUri = coverImgUrl + coverId + "-M.jpg";
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setAccept(List.of(MediaType.IMAGE_JPEG));
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<byte[]> res = restTemplate.exchange(coverImgUri, HttpMethod.GET, entity, byte[].class);
		
		if(res.getStatusCode() == HttpStatus.OK && res.getBody()!=null) {
			System.out.println("cover img 수신 성공");
			return res.getBody();
		}
		System.out.println("cover img 수신 실패");
		return new byte[0];
		
		
	}
}

package bookcafe.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import bookcafe.config.SecurityConfig;
import bookcafe.data.repository.BookShelfRepository;
import bookcafe.data.repository.SiteUserAuthorityRepository;
import bookcafe.data.repository.SiteUserRepository;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
public class UserControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockitoBean
	SiteUserRepository userRepo;
	
	@MockitoBean
	SiteUserAuthorityRepository userAuthorityRepo;
	
	@MockitoBean
	PasswordEncoder passwordEncoder;
	
	@MockitoBean
	BookShelfRepository bookShelfRepo;
	
	@Test
	@WithAnonymousUser
	void getUserCreateFormTest() throws Exception{
		mvc.perform(get("/user/create"))
			.andExpect(status().isOk())
			.andExpect(model().size(1));
	}

}

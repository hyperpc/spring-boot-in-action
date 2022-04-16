package org.myApp.study;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;

//import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
public class MockMvcWebTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@BeforeEach
	public void setupMockMvc(){
		mockMvc = MockMvcBuilders
		.webAppContextSetup(context)
		.apply(springSecurity())
		.build();
	}

	@Test
	public void homePage_unauthenticatedUser() throws Exception {
	  mockMvc.perform(get("/"))
		  .andExpect(status().is3xxRedirection())
		  .andExpect(header().string("Location", "http://localhost/login"));
	}
	
	@Test
	//@WithUserDetails("craig")
	@WithMockUser(username="craig", password="password", roles={"READER"})
	public void homePage_authenticatedUser() throws Exception {
	  Reader expectedReader = new Reader();
	  expectedReader.setUsername("craig");
	  expectedReader.setPassword("password");
	  expectedReader.setFullname("Craig Walls");

	  MockHttpServletRequest request = new MockHttpServletRequest();
	  request.setAttribute("reader", expectedReader);
	  request.setAttribute("books", hasSize(0));
	  request.setAttribute("amazonID", "habuma-20");
	  
	  mockMvc.perform(get("/")
	  					.requestAttr("reader", expectedReader)
						.requestAttr("books", hasSize(0))
						.requestAttr("amazonID", "habuma-20"))
		  .andExpect(model().attribute("reader", samePropertyValuesAs(expectedReader)))
		  .andExpect(model().attribute("books", hasSize(0)))
		  .andExpect(model().attribute("amazonID", "habuma-20"))
		  .andExpect(status().isOk())
		  .andExpect(view().name("readingList"));
	}
}

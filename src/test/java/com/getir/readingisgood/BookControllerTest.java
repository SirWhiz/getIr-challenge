package com.getir.readingisgood;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ComponentScan(basePackages = {"com.getir.readingisgood"})
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Test
	public void testForbiddenRequest() throws Exception {
		mockMvc.perform(get("/api/books")).andExpect(status().isUnauthorized());
	}
	
	@Test
	public void testFindAllBooks() throws Exception {
		String accessToken = obtainAccessToken("Test Account", "superSafePassword");
		
		mockMvc.perform(get("/api/books").header("Authorization", "Bearer " + accessToken))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"));
	}
	
	@Test
	public void testSaveBook() throws Exception {
		String accessToken = obtainAccessToken("Test Account", "superSafePassword");
		
		mockMvc.perform(post("/api/books")
	            .contentType(MediaType.APPLICATION_JSON)
	            .header("Authorization", "Bearer " + accessToken)
	            .content("{\"name\":\"The Great Gatsby\",\"pages\":\"218\",\"stock\":19}"))
	            .andExpect(status().isOk())
	            .andExpect(content().contentType("application/json"));
	}
	
	@Test
	public void testUpdateBookStock() throws Exception {
		String accessToken = obtainAccessToken("Test Account", "superSafePassword");
		
		mockMvc.perform(post("/api/books/stock/update")
	            .contentType(MediaType.APPLICATION_JSON)
	            .header("Authorization", "Bearer " + accessToken)
	            .content("{\"bookId\":\"12341245\",\"stock\":25}"))
	            .andExpect(status().isOk())
	            .andExpect(content().contentType("application/json"));
	}
	
	private String obtainAccessToken(String username, String password) throws Exception {
		ResultActions result
		= mockMvc.perform(post("/api/auth/login")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("{\"name\":\""+username+"\",\"password\":\""+password+"\"}"))
	            .andExpect(status().isOk());
		
		String resultString = result.andReturn().getResponse().getContentAsString();
		
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		return jsonParser.parseMap(resultString).get("data").toString();
	}
	
}

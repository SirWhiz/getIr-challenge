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
public class OrderControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Test
	public void testFindAllOrders() throws Exception {
		String accessToken = obtainAccessToken("Test Account", "superSafePassword");
		
		mockMvc.perform(get("/api/orders").header("Authorization", "Bearer " + accessToken))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"));
	}
	
	@Test
	public void testCreateOrder() throws Exception {
		String accessToken = obtainAccessToken("Test Account", "superSafePassword");
		
		mockMvc.perform(post("/api/orders")
	            .contentType(MediaType.APPLICATION_JSON)
	            .header("Authorization", "Bearer " + accessToken)
	            .content("{\"date\":\"2022-01-09\",\"customerId\":\"12345\",\"books\":[{\"id\":\"12345\",\"name\":\"The Great Gatsby\",\"pages\":\"218\",\"stock\":25}],\"amount\":35}"))
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

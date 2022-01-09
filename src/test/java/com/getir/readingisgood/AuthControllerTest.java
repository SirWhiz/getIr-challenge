package com.getir.readingisgood;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class AuthControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	public void testRegisterCustomer() throws Exception {
		mockMvc.perform(post("/api/auth/register")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("{\"name\":\"Test Account\",\"email\":\"test@getir.com\",\"password\":\"superSafePassword\"}"))
	            .andExpect(status().isOk());
	}
	
	@Test
	public void testRegisterExistingCustomer() throws Exception {
		ResultActions result
		= mockMvc.perform(post("/api/auth/register")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("{\"name\":\"Test Account\",\"email\":\"test@getir.com\",\"password\":\"superSafePassword\"}"))
	            .andExpect(status().isOk());
		
		String resultString = result.andReturn().getResponse().getContentAsString();
		
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		String errorMsg = jsonParser.parseMap(resultString).get("error").toString();
		String status = jsonParser.parseMap(resultString).get("status").toString();
		
		assertEquals("Error: Username is already taken!", errorMsg);
		assertEquals("400 BAD_REQUEST", status);
	}
	
	@Test
	public void testLoginCustomer() throws Exception {
		mockMvc.perform(post("/api/auth/login")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("{\"name\":\"Test Account\",\"password\":\"superSafePassword\"}"))
	            .andExpect(status().isOk());
	}

}

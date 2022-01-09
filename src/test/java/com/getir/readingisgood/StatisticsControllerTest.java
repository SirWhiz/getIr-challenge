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
public class StatisticsControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	public void testGetMonthlyStatistics() throws Exception {
		String accessToken = obtainAccessToken("Test Account", "superSafePassword");
		
		mockMvc.perform(get("/api/statistics/123").header("Authorization", "Bearer " + accessToken))
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

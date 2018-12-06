package com.banry.pscm.tenant.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestAuthenticationTenantController {

	private static Logger log = LoggerFactory.getLogger(TestAuthenticationTenantController.class);

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
	
    @Before
    public void setupMockMvc() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
    @After
	public void tearDown() throws Exception {
		mvc = null;
	}

	@Test
	public void AuthenticationTenant() {
		try {
			MvcResult result = mvc.perform(post("/AuthenticationTenant")
					.contentType(MediaType.APPLICATION_JSON_UTF8).param("account", "aaaa"))  
			        .andExpect(status().isOk()) 
			        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))   
			        .andReturn();
			System.out.println("结果是："+result.getResponse().getContentAsString());
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException异常：", e);
		} catch (Exception e) {
			log.error("Exception异常：", e);
		}
	}
	
}

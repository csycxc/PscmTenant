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

import com.banry.pscm.tenant.biz.TenantController;
import com.banry.pscm.tenant.service.biz.Tenant;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestTenantController {
	private static Logger log = LoggerFactory.getLogger(TestTenantController.class);

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


	//@Test
	public void getTenantList() {
		try {
			MvcResult result = mvc.perform(post("/tenant/getTenantList"))  
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

	//@Test
	public void findTenantByCode() {
		try {
			MvcResult result = mvc.perform(post("/tenant/findTenantByCode")
					.contentType(MediaType.APPLICATION_JSON_UTF8).param("code", "2"))  
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
	
	//@Test
	public void findTenantTree(){
		try {
			MvcResult result = mvc.perform(post("/tenant/findTenantTree")
					.contentType(MediaType.APPLICATION_JSON_UTF8))  
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
	
	//@Test
	public void deleteTenantByCode(){
		try {
			MvcResult result = mvc.perform(post("/tenant/deleteTenantByCode")
					.contentType(MediaType.APPLICATION_JSON_UTF8).param("code", "2"))  
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
	
	//@Test
	public void addTenant(){
		try {
			Tenant ten = new Tenant();
			ten.setTenantCode("2");
			ten.setAccount("bbb");
			ten.setPassword("123");
			ten.setParentCode("222");
			
			ObjectMapper mapper = new ObjectMapper();
			
			MvcResult result = mvc.perform(post("/tenant/addOrUpdateTenant")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
	                .content(mapper.writeValueAsString(ten)).param("flag", "I"))
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
	
	//@Test
	public void updateTenant(){
		try {
			Tenant ten = new Tenant();
			ten.setTenantCode("4");
			ten.setAccount("fff");
			ten.setPassword("123");
			ten.setParentCode("555");
			
			ObjectMapper mapper = new ObjectMapper();
			
			MvcResult result = mvc.perform(post("/tenant/addOrUpdateTenant")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
	                .content(mapper.writeValueAsString(ten)).param("flag", "U"))
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

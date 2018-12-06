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
import com.banry.pscm.tenant.service.biz.DynamicSource;
import com.banry.pscm.tenant.service.biz.TenantRealWithBLOBs;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestDynamicSourceController {
	private static Logger log = LoggerFactory.getLogger(TestDynamicSourceController.class);

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
	public void getDynamicSourceList() {
		try {
			MvcResult result = mvc.perform(post("/dynamicSource/getDynamicSourceList")).andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();
			System.out.println("结果是：" + result.getResponse().getContentAsString());
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException异常：", e);
		} catch (Exception e) {
			log.error("Exception异常：", e);
		}
	}

	//@Test
	public void getDynamicSourceByCode() {
		try {
			MvcResult result = mvc
					.perform(post("/dynamicSource/getDynamicSourceByCode").contentType(MediaType.APPLICATION_JSON_UTF8)
							.param("code", "s1"))
					.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andReturn();
			System.out.println("结果是：" + result.getResponse().getContentAsString());
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException异常：", e);
		} catch (Exception e) {
			log.error("Exception异常：", e);
		}
	}

	@Test
	public void deleteDynamicSource() {
		try {
			MvcResult result = mvc
					.perform(post("/dynamicSource/deleteDynamicSource").contentType(MediaType.APPLICATION_JSON_UTF8)
							.param("code", "s3"))
					.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andReturn();
			System.out.println("结果是：" + result.getResponse().getContentAsString());
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException异常：", e);
		} catch (Exception e) {
			log.error("Exception异常：", e);
		}
	}

	//@Test
	public void addDynamicSource() {
		try {
			DynamicSource ds = new DynamicSource();
			ds.setSourceCode("s2");
			ds.setSourceName("ooo");

			ObjectMapper mapper = new ObjectMapper();

			MvcResult result = mvc
					.perform(post("/dynamicSource/addOrUpdateDynamicSource").contentType(MediaType.APPLICATION_JSON_UTF8)
							.content(mapper.writeValueAsString(ds)).param("flag", "I"))
					.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andReturn();
			System.out.println("结果是：" + result.getResponse().getContentAsString());
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException异常：", e);
		} catch (Exception e) {
			log.error("Exception异常：", e);
		}
	}

	//@Test
	public void updateDynamicSource() {
		try {
			DynamicSource ds = new DynamicSource();
			ds.setSourceCode("s2");
			ds.setSourceName("uuu");

			ObjectMapper mapper = new ObjectMapper();

			MvcResult result = mvc
					.perform(post("/dynamicSource/addOrUpdateDynamicSource").contentType(MediaType.APPLICATION_JSON_UTF8)
							.content(mapper.writeValueAsString(ds)).param("flag", "U"))
					.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andReturn();
			System.out.println("结果是：" + result.getResponse().getContentAsString());
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException异常：", e);
		} catch (Exception e) {
			log.error("Exception异常：", e);
		}
	}
}

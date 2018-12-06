package com.banry.pscm.tenant.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
import com.banry.pscm.tenant.service.biz.BizAuthority;
import com.banry.pscm.tenant.service.biz.PscmBusiness;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestBizAuthorityController {
	private static Logger log = LoggerFactory.getLogger(TestBizAuthorityController.class);

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

	// @Test
	public void getBizAuthList() {
		try {
			MvcResult result = mvc.perform(post("/bizAuthority/getBizAuthList")).andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();
			System.out.println("结果是：" + result.getResponse().getContentAsString());
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException异常：", e);
		} catch (Exception e) {
			log.error("Exception异常：", e);
		}
	}

	// @Test
	public void getBizAuthByCode() {
		try {
			MvcResult result = mvc
					.perform(post("/bizAuthority/getBizAuthByCode").contentType(MediaType.APPLICATION_JSON_UTF8)
							.param("code", "b1"))
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
	public void getBizAuthByTenantCode() {
		try {
			MvcResult result = mvc
					.perform(post("/bizAuthority/getBizAuthByTenantCode").contentType(MediaType.APPLICATION_JSON_UTF8)
							.param("tenantCode", "1001"))
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
	public void deleteBusiness() {
		try {
			MvcResult result = mvc
					.perform(post("/bizAuthority/deleteBusiness").contentType(MediaType.APPLICATION_JSON_UTF8)
							.param("bizCode", "b2"))
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
	public void addBusiness() {
		try {
			PscmBusiness pb = new PscmBusiness();
			pb.setBizCode("b2");
			pb.setBizChineseName("添加");

			ObjectMapper mapper = new ObjectMapper();

			MvcResult result = mvc
					.perform(post("/bizAuthority/addOrUpdateBusiness").contentType(MediaType.APPLICATION_JSON_UTF8)
							.content(mapper.writeValueAsString(pb)).param("flag", "I"))
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
	public void updateBusiness() {
		try {
			PscmBusiness pb = new PscmBusiness();
			pb.setBizCode("b2");
			pb.setBizChineseName("修改");

			ObjectMapper mapper = new ObjectMapper();

			MvcResult result = mvc
					.perform(post("/bizAuthority/addOrUpdateBusiness").contentType(MediaType.APPLICATION_JSON_UTF8)
							.content(mapper.writeValueAsString(pb)).param("flag", "U"))
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
	public void joinBizAuthority(){
		try{
			List<BizAuthority> list = new ArrayList<>();
			BizAuthority biz = new BizAuthority();
			biz.setTenantCode("2");
			biz.setBizCode("b2");
			biz.setBizChineseName("添加");
			list.add(0, biz);

			ObjectMapper mapper = new ObjectMapper();

			MvcResult result = mvc
					.perform(post("/bizAuthority/joinBizAuthority").contentType(MediaType.APPLICATION_JSON_UTF8)
							.content(mapper.writeValueAsString(list)))
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
	public void deleteJoinBizAuthority(){
		try{
			List<BizAuthority> list = new ArrayList<>();
			BizAuthority biz = new BizAuthority();
			biz.setTenantCode("2");
			biz.setBizCode("b2");
			biz.setBizChineseName("添加");
			list.add(0, biz);

			ObjectMapper mapper = new ObjectMapper();

			MvcResult result = mvc
					.perform(post("/bizAuthority/deleteJoinBizAuthority").contentType(MediaType.APPLICATION_JSON_UTF8)
							.content(mapper.writeValueAsString(list)))
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

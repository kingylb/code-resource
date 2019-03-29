package com.wsmtec.jfm.ta.controller;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.wsmtec.jfm.ta.conf.DocConf;
import com.wsmtec.jfm.ta.conf.StatusConf;
import com.wsmtec.jfm.ta.dto.DocInput;
import com.wsmtec.jfm.ta.dto.StatusInput;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("debug")
public class CallbackControllerTests
{
	static StatusInput statusInput;
	static DocInput input;
	static String okJson = "{\"code\":\"0\",\"message\":\"成功\",\"attach\":null}";

	// REST测试模板，Spring Boot自动提供
	@Autowired
	private TestRestTemplate restTemplate = null;

	@BeforeClass
	public static void init()
	{
		statusInput = new StatusInput();
		statusInput.setCaseId("arbi123456");
		statusInput.setCurrentMainNode(StatusConf.CASE_REPLY);
		statusInput.setCaseNo("高亮测试用案号#1");
		statusInput.setTimestamp("1550574818762");
		statusInput.setSign("27c9d0a1bb642d67257d59d6be26ce1d");
		
		input = new DocInput();
		input.setBase64SignFile("base64==");
		input.setCaseId("case-id#123");
		input.setContent("content123abc");
		input.setDefendIdCard("220103198202280437");
		input.setDocName("裁决书");
		input.setDocType(DocConf.ARBITRAL_AWARD_BOOK);
		input.setSignFileId("201901281126650001449264.pdf");
		input.setSubject("subject123abc");
		input.setTimestamp("1550574818762");
		input.setSign("acec8c1b2bc804723966e570bb1f5bee");
	}

	@Test
	public void test_status_controller_ok()
	{
		String _jsonParam = JSON.toJSONString(statusInput);
		HttpHeaders _headers = new HttpHeaders();
		MediaType _type = MediaType.parseMediaType("application/json; charset=UTF-8");
		_headers.setContentType(_type);
		HttpEntity<String> _entity = new HttpEntity<String>(_jsonParam, _headers);
		String _result = this.restTemplate.postForObject("/tacb/status", _entity, String.class);
		assertTrue(okJson.equals(_result));
	}
	
	@Test
	public void test_doc_controller_ok()
	{
		String _jsonParam = JSON.toJSONString(input);
		HttpHeaders _headers = new HttpHeaders();
		MediaType _type = MediaType.parseMediaType("application/json; charset=UTF-8");
		_headers.setContentType(_type);
		HttpEntity<String> _entity = new HttpEntity<String>(_jsonParam, _headers);
		String _result = this.restTemplate.postForObject("/tacb/doc", _entity, String.class);
		assertTrue(okJson.equals(_result));
	}
}

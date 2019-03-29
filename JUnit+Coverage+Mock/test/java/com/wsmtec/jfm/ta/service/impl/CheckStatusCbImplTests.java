package com.wsmtec.jfm.ta.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.wsmtec.jfm.common.enums.SuccessCode;
import com.wsmtec.jfm.common.vo.ApiResultVO;
import com.wsmtec.jfm.ta.conf.StatusConf;
import com.wsmtec.jfm.ta.dto.StatusInput;
import com.wsmtec.jfm.ta.service.CheckStatusCb;
import com.wsmtec.jfm.ta.service.impl.CheckStatusCbImpl;
import com.wsmtec.jfm.ta.util.CheckSign;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("debug")
public class CheckStatusCbImplTests
{
	@Autowired
	CheckStatusCb target;
	static StatusInput statusInput;
	
	@BeforeClass
	public static void init() {
		statusInput = new StatusInput();
		statusInput.setCaseId("id1");
		statusInput.setCurrentMainNode("1");
		statusInput.setTimestamp("1550574818762");
		statusInput.setSign("68a84157c99681ccc19879b687045e52");
	}
	
	@Test
	public void check_ok()
	{
		CheckSign.chkSignDebug = true;
		ApiResultVO<String> _ret = target.check(statusInput);
		CheckSign.chkSignDebug = false;
		assertTrue(SuccessCode.SUCCESS.getCode().equals(_ret.getCode()));
	}

	@Test
	public void isArbiStatus_CASE_CHECK()
	{
		assertTrue(CheckStatusCbImpl.isArbiStatus(StatusConf.CASE_CHECK));
	}
	
	@Test
	public void isArbiStatus_false()
	{
		assertFalse(CheckStatusCbImpl.isArbiStatus("ng"));
	}
}

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
import com.wsmtec.jfm.ta.conf.DocConf;
import com.wsmtec.jfm.ta.dto.DocInput;
import com.wsmtec.jfm.ta.service.CheckDocCb;
import com.wsmtec.jfm.ta.util.CheckIdCard;
import com.wsmtec.jfm.ta.util.CheckSign;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("debug")
public class CheckDocCbImplTests
{
	@Autowired
	CheckDocCb target;
	
	private static DocInput input;
	
	@BeforeClass
	public static void init() {
		input = new DocInput();
		input.setBase64SignFile("base64==");
		input.setCaseId("case-id");
		input.setContent("content123abc");
		input.setDefendIdCard("220103198202280437");
		input.setDocName("裁决书");
		input.setDocType(DocConf.ARBITRAL_AWARD_BOOK);
		input.setSignFileId("201901281126650001449264.pdf");
		input.setSubject("subject123abc");
		input.setTimestamp("1550574818762");
		input.setSign("803d8245506a9666b2d0508ca1ce199c");
	}
	
	@Test
	public void check_ok()
	{
		CheckSign.chkSignDebug = true;
		ApiResultVO<String> _ret = target.check(input);
		CheckSign.chkSignDebug = false;
		assertTrue(SuccessCode.SUCCESS.getCode().equals(_ret.getCode()));
	}

	@Test
	public void isArbiDocType_0_ok()
	{
		assertTrue(CheckDocCbImpl.isArbiDocType("0"));
	}
	
	@Test
	public void isArbiStatus_44_ok()
	{
		assertTrue(CheckDocCbImpl.isArbiDocType("44"));
	}
	
	@Test
	public void isArbiStatus_45_ng()
	{
		assertFalse(CheckDocCbImpl.isArbiDocType("45"));
	}
	
	@Test
	public void test_id_card_num()
	{
		assertFalse(CheckIdCard.isValidIdCard("22010319820228043"));
	}
}

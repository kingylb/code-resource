package com.wsmtec.jfm.ta.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.wsmtec.jfm.common.enums.SuccessCode;
import com.wsmtec.jfm.common.enums.TaError;
import com.wsmtec.jfm.common.vo.ApiResultVO;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("debug")
public class CheckSignTests
{
	@Autowired
	CheckSign checkSign;
	
	@Test
	public void check_timestamp_ng()
	{
		checkSign.setChkSignDebug(false);
		ApiResultVO<String> _rslt = checkSign.check("caseID", "1550546900556", "sign");
		assertTrue(TaError.TA_CB_TIMESTAMP_OVER.getCode().equals(_rslt.getCode()));
		checkSign.setChkSignDebug(true);
	}
	
	@Test
	public void check_sign_ng()
	{
		ApiResultVO<String> _rslt = checkSign.check("caseID", Long.toString(System.currentTimeMillis()), "sign");
		assertTrue(TaError.TA_CB_SIGN_ERR.getCode().equals(_rslt.getCode()));
	}
	
	@Test
	public void check_sign_ok()
	{
		checkSign.setChkSignDebug(true);
		ApiResultVO<String> _rslt = checkSign.check("caseID", "1550574818762", "c5d914f1c99442684c11b2296af5d0bd");
		assertTrue(SuccessCode.SUCCESS.getCode().equals(_rslt.getCode()));
		checkSign.setChkSignDebug(false);
	}
}

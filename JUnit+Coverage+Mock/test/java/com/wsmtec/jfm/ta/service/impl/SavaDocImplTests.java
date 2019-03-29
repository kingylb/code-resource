package com.wsmtec.jfm.ta.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.wsmtec.jfm.common.enums.SuccessCode;
import com.wsmtec.jfm.common.mapper.CaseInfoMapper;
import com.wsmtec.jfm.common.model.CaseInfo;
import com.wsmtec.jfm.common.model.CaseInfoExample;
import com.wsmtec.jfm.common.model.CaseInfoWithBLOBs;
import com.wsmtec.jfm.common.vo.ApiResultVO;
import com.wsmtec.jfm.ta.conf.DocConf;
import com.wsmtec.jfm.ta.dto.DocInput;
import com.wsmtec.jfm.ta.mapper.JfmMailMapper;
import com.wsmtec.jfm.ta.model.JfmMail;
import com.wsmtec.jfm.ta.model.JfmMailExample;
import com.wsmtec.jfm.ta.service.SaveDocMail;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("debug")
public class SavaDocImplTests
{

	static int initFlag = 0;
	static DocInput input;
	static String caseId = "case-id#123";
	static String caseNum = "高亮测试用案号#1-文书邮件";

	@Autowired
	CaseInfoMapper caseInfoMapper;
	
	@Autowired
	JfmMailMapper jfmMailMapper;
	
	@Autowired
	SaveDocMail saveDocMail;

	@BeforeClass
	public static void init()
	{
		input = new DocInput();
		input.setBase64SignFile("base64==");
		input.setCaseId(caseId);
		input.setContent("content123abc");
		input.setDefendIdCard("220103198202280437");
		input.setDocName("裁决书");
		input.setDocType(DocConf.ARBITRAL_AWARD_BOOK);
		input.setSignFileId("201901281126650001449264.pdf");
		input.setSubject("subject123abc");
	}

	@Before
	public void newCase()
	{
		if (0 == initFlag)
		{
			CaseInfoExample _caseInfoExample = new CaseInfoExample();
			_caseInfoExample.createCriteria().andArbiCaseIdEqualTo(caseId);
			List<CaseInfo> _cases = caseInfoMapper.selectByExample(_caseInfoExample);
			if (null == _cases || 0 == _cases.size())
			{
				CaseInfoWithBLOBs _CaseInfo1 = new CaseInfoWithBLOBs();
				_CaseInfo1.setArbiCaseId(caseId);
				_CaseInfo1.setJfmNum("JfmNum#1");
				_CaseInfo1.setStageStatus(1);
				_CaseInfo1.setArbiCaseNum("高亮测试用案号#1-文书邮件");
				int _ret = caseInfoMapper.insertSelective(_CaseInfo1);
				assertTrue(1 == _ret);
			}
			// Set Flag
			initFlag = 1;
		} else
		{
			CaseInfoWithBLOBs _caseInfoWithBLOBs = new CaseInfoWithBLOBs();
			_caseInfoWithBLOBs.setArbiCaseId(caseId);
			CaseInfoExample _caseInfoExample = new CaseInfoExample();
			_caseInfoExample.createCriteria().andArbiCaseNumEqualTo(caseNum);
			caseInfoMapper.updateByExampleSelective(_caseInfoWithBLOBs, _caseInfoExample);
		}
		
		saveDocMail.setDebug(true);
	}
	
	@After
	public void resetInput()
	{
		input.setDocType(DocConf.ARBITRAL_AWARD_BOOK);
		input.setCaseId(caseId);
	}

	@Test
	public void save_ok()
	{
		// DO
		ApiResultVO<String> _ret = saveDocMail.save(input, "1.2.3.4");
		assertTrue(SuccessCode.SUCCESS.getCode().equals(_ret.getCode()));
		// check DB data
		JfmMailExample _jfmMailExample = new JfmMailExample();
		_jfmMailExample.createCriteria().andArbiCaseIdEqualTo(caseId);
		List<JfmMail> _mails = jfmMailMapper.selectByExample(_jfmMailExample);
		assertTrue(_mails.get(0).getStatus().equals(DocConf.MAIL_STATUS_UNREAD));
	}

	@Test
	public void save_ok_unread()
	{
		// prepare DB data
		CaseInfoWithBLOBs _caseInfoWithBLOBs = new CaseInfoWithBLOBs();
		_caseInfoWithBLOBs.setArbiCaseId(caseId+"#1");
		CaseInfoExample _caseInfoExample = new CaseInfoExample();
		_caseInfoExample.createCriteria().andArbiCaseNumEqualTo(caseNum);
		caseInfoMapper.updateByExampleSelective(_caseInfoWithBLOBs, _caseInfoExample);
		// DO
		input.setDocType(DocConf.APPLICATION_ARBITRATION_BOOK);
		input.setCaseId(caseId+"#1");
		ApiResultVO<String> _ret = saveDocMail.save(input, "1.2.3.4");
		assertTrue(SuccessCode.SUCCESS.getCode().equals(_ret.getCode()));
		// check DB data
		JfmMailExample _jfmMailExample = new JfmMailExample();
		_jfmMailExample.createCriteria().andArbiCaseIdEqualTo(caseId+"#1");
		List<JfmMail> _mails = jfmMailMapper.selectByExample(_jfmMailExample);
		assertTrue(_mails.get(0).getStatus().equals(DocConf.MAIL_STATUS_PREPARE));
	}
}

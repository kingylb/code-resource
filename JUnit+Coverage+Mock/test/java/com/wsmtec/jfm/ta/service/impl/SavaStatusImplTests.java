package com.wsmtec.jfm.ta.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.wsmtec.jfm.common.mapper.CaseInfoMapper;
import com.wsmtec.jfm.common.model.CaseInfo;
import com.wsmtec.jfm.common.model.CaseInfoExample;
import com.wsmtec.jfm.common.model.CaseInfoWithBLOBs;
import com.wsmtec.jfm.common.util.RedisUtil;
import com.wsmtec.jfm.common.vo.ApiResultVO;
import com.wsmtec.jfm.ta.TaApplication;
import com.wsmtec.jfm.ta.conf.StatusConf;
import com.wsmtec.jfm.ta.dto.StatusInput;
import com.wsmtec.jfm.ta.service.SaveStatus;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TaApplication.class})
@ActiveProfiles("debug")
public class SavaStatusImplTests
{

	static StatusInput statusInput;
	static int initFlag = 0;
	static String assetNum = "ZC00006";
	static long serviceCost = 1;
	static long arbiCost = 2;

	@Autowired
	SaveStatus savaStatus;

	@Autowired
	CaseInfoMapper caseInfoMapper;
	
	@Autowired
	RedisUtil redisUtil;
	
	@Value("${callback}")
	String REDIS_KEY_CALLBACK;
	
	@Value("${balancePrefix}")
	String REDIS_KEY_BALANCE;
	
	@BeforeClass
	public static void init()
	{
		statusInput = new StatusInput();
		statusInput.setCaseId("arbi123456");
		statusInput.setCurrentMainNode(StatusConf.CASE_REPLY);
		statusInput.setCaseNo("高亮测试用案号#1");
	}

	@Before
	public void newCase()
	{
		// common data
		CaseInfoExample _caseInfoExample = new CaseInfoExample();
		_caseInfoExample.createCriteria().andArbiCaseIdEqualTo("arbi123456");
		// init
		if (0 == initFlag)
		{
			List<CaseInfo> _cases = caseInfoMapper.selectByExample(_caseInfoExample);
			if (null == _cases || 0 == _cases.size())
			{
				CaseInfoWithBLOBs _caseInfo1 = new CaseInfoWithBLOBs();
				_caseInfo1.setArbiCaseId("arbi123456");
				_caseInfo1.setStageStatus(1);
				_caseInfo1.setArbiCaseNum("高亮测试用案号#1-old");
				_caseInfo1.setAssetNum("1");
				_caseInfo1.setJfmNum("jfmNum123456");
				_caseInfo1.setServiceReceivableCost(BigDecimal.valueOf(1.01));
				_caseInfo1.setArbiReceivableCost(BigDecimal.valueOf(2.1));
				int _ret = caseInfoMapper.insertSelective(_caseInfo1);
				assertTrue(1 == _ret);
			}
			// set flag
			initFlag = 1;
		}
		// reset stage status
		CaseInfoWithBLOBs _caseInfo = new CaseInfoWithBLOBs();
		_caseInfo.setStageStatus(1);
		_caseInfo.setServiceReceivableCost(BigDecimal.valueOf(serviceCost));
		_caseInfo.setArbiReceivableCost(BigDecimal.valueOf(arbiCost));
		_caseInfo.setAssetNum(assetNum);
		_caseInfo.setUpdateTime(new Date());
		int _ret = caseInfoMapper.updateByExampleSelective(_caseInfo, _caseInfoExample);
		assertTrue(1 == _ret);
		// reset input data
		statusInput.setCurrentMainNode(StatusConf.CASE_REPLY);
	}

	@Test
	public void update_withCaseNo_ok()
	{
		String _ip = "8.8.8.8";
		ApiResultVO<String> _ret = savaStatus.update(statusInput, _ip);
		// ok
		assertTrue("0".equals(_ret.getCode()));
		CaseInfoExample _caseInfoExample = new CaseInfoExample();
		_caseInfoExample.createCriteria().andArbiCaseIdEqualTo(statusInput.getCaseId());
		List<CaseInfo> _caseInfos = caseInfoMapper.selectByExample(_caseInfoExample);
		// CurrentMainNode
		assertTrue(StatusConf.SS_CASE_REPLY == _caseInfos.get(0).getStageStatus());
		// CaseNo
		assertTrue(statusInput.getCaseNo().equals(_caseInfos.get(0).getArbiCaseNum()));
		// IP
		assertTrue(_ip.equals(_caseInfos.get(0).getLastLoginIp()));
	}
	
	@Test
	public void update_withdraw_money_at_redis()
	{
		//prepare DB
		statusInput.setCurrentMainNode(StatusConf.CASE_CHECK_NOT_PASS);
		// record Redis balance
		String _beforeBalance = (String) redisUtil.get(REDIS_KEY_BALANCE+assetNum);
		System.out.println("_beforeBalance: "+_beforeBalance);
		// DO
		String _ip = "8.8.8.8";
		ApiResultVO<String> _ret = savaStatus.update(statusInput, _ip);
		// ok
		assertTrue("0".equals(_ret.getCode()));
		// check Redis
		String _afterBalance = (String) redisUtil.get(REDIS_KEY_BALANCE+assetNum);
		System.out.println("_afterBalance: "+_afterBalance);
		assertEquals(Long.valueOf(_beforeBalance).longValue() + serviceCost + arbiCost, Long.valueOf(_afterBalance).longValue());
	}

}

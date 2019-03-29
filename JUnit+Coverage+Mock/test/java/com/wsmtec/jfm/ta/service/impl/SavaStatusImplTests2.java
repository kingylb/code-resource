package com.wsmtec.jfm.ta.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.wsmtec.jfm.common.model.CaseInfo;
import com.wsmtec.jfm.common.util.RedisUtil;
import com.wsmtec.jfm.ta.TaApplication;
import com.wsmtec.jfm.ta.service.SaveStatus;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TaApplication.class})
@ActiveProfiles("debug")
public class SavaStatusImplTests2
{

	@Autowired
	SaveStatus savaStatus;
	
	@Test
	public void delSHDDH_mock_test() {
		// mock
		RedisUtil redisUtilMock = Mockito.mock(RedisUtil.class);
		// prepare
		CaseInfo caseInfo = new CaseInfo();
		caseInfo.setAssetNum("an1");
		caseInfo.setMerchartNum("mn2");
		SavaStatusImpl savaStatusImpl = (SavaStatusImpl) savaStatus;
		savaStatusImpl.setRedisUtil(redisUtilMock);
		String expShddh = "SHDDH:" + caseInfo.getAssetNum() + ":" + caseInfo.getMerchartNum();
		// do
		savaStatusImpl.delSHDDH(caseInfo);
		// check
		Mockito.verify(redisUtilMock).del(expShddh);
	}

	//Mockito.when(mockObject.targetMethod(args)).thenReturn(desiredReturnValue);
}

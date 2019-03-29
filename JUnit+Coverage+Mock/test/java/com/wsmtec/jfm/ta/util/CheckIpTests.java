package com.wsmtec.jfm.ta.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("debug")
public class CheckIpTests
{
	@Test
	public void check_isWhiteIp_ip1_true()
	{
		boolean rslt = CheckIp.isWhiteIp("127.0.0.1");
		assertTrue(rslt);
	}
	
	@Test
	public void check_isWhiteIp_ip2_true()
	{
		boolean rslt = CheckIp.isWhiteIp("10.1.194.2");
		assertTrue(rslt);
	}
	
	@Test
	public void check_isWhiteIp_false()
	{
		boolean rslt = CheckIp.isWhiteIp("1.2.3.4");
		assertFalse(rslt);
	}
	
}

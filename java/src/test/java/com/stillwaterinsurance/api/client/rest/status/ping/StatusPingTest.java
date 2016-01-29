package com.stillwaterinsurance.api.client.rest.status.ping;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.stillwaterinsurance.api.client.HttpClient;
import com.stillwaterinsurance.api.client.HttpClient.ResponseVO;


public class StatusPingTest {

	private static final String STATUS_PING = "/status/ping/v1.0";
	
	
	@Test
	public void statusPingTest() {
		
		HttpClient client = new HttpClient();
		ResponseVO responseVO = client.doGet(STATUS_PING);
		
		assertEquals("incorrect status code returned.", new Integer(200), responseVO.getCode());
		
		assertEquals("result body should be empty.", "", responseVO.getBody());
	}

}

package com.stillwaterinsurance.api.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpClient {

	private String baseUrl;
	private String accountName;
	private String token;
	private String authScope;
	
	public HttpClient(){
		
		Properties props = Utils.getProps();
		baseUrl = props.getProperty("BASE_URL");
		accountName = props.getProperty("ACCOUNT_NAME");
		token = props.getProperty("TOKEN");
		authScope = props.getProperty("AUTH_SCOPE");
		
	}
	
	public ResponseVO doGet(String service){
		
		ResponseVO responseVO = new ResponseVO();
		
		//service== "/status/ping/v1.0"
		String url =  baseUrl + service;
		System.out.println("HttpClient.doGet() service url=" + url + "\n");
		
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(authScope, 443),
                new UsernamePasswordCredentials(accountName, token));
		
		CloseableHttpClient client = HttpClientBuilder.create().
			setDefaultCredentialsProvider(credsProvider).build();
		
		HttpGet getRequest = new HttpGet(url);
		try {
			getRequest.addHeader(HttpHeaders.CONTENT_TYPE, "text/xml");
			HttpResponse response = client.execute(getRequest);
			responseVO.setBody(getResponseText(response));
			responseVO.setCode(response.getStatusLine().getStatusCode());
			client.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return responseVO;
	}
	
	
	public ResponseVO doPost(String service, String body){
		ResponseVO responseVO = new ResponseVO();
		
		//service== "/status/ping/v1.0"
		String url =  baseUrl + service;
		System.out.println("HttpClient.doPost() service url=" + url);
		
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(authScope, 443),
                new UsernamePasswordCredentials(accountName, token));
		
		CloseableHttpClient client = HttpClientBuilder.create().
			setDefaultCredentialsProvider(credsProvider).
			build();
		
		HttpPost postRequest = new HttpPost(url);
		try {
			postRequest.setEntity(new StringEntity(body));
			postRequest.addHeader(HttpHeaders.CONTENT_TYPE, "text/xml");
			HttpResponse response = client.execute(postRequest);
			responseVO.setBody(getResponseText(response));
			responseVO.setCode(response.getStatusLine().getStatusCode());
			client.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return responseVO;
	}
	
	
	private String getResponseText(HttpResponse response){
		
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(
			        new InputStreamReader((response.getEntity().getContent())));
			
			String output;
			if(br != null){
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
			}
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
		
	}
	
	public class ResponseVO {
	
		private String body;
		private Integer code;
		
		public String getBody() {
			return body;
		}
		public void setBody(String body) {
			this.body = body;
		}
		public Integer getCode() {
			return code;
		}
		public void setCode(Integer code) {
			this.code = code;
		}	
	
	}
	
}

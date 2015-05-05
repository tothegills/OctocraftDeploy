package com.octopusdeploy;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class WebClient {
	
	private static String API_KEY_HEADER = "X-Octopus-ApiKey";
	
	private String url;
	private String apiKey;
	
	public WebClient(String url, String apiKey) {
		this.url = url;
		this.apiKey = apiKey;
	}
	
	public HttpResponse Get(String path) throws ClientProtocolException, IOException {
				
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url + path);
		get.addHeader(API_KEY_HEADER, apiKey);
		
		HttpResponse response;
		response = client.execute(get);

		return response;
	}
	
	public HttpResponse Post(String path, String json) throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url + path);
		post.setHeader(API_KEY_HEADER, apiKey);

		post.setEntity(new StringEntity(json, ContentType.TEXT_PLAIN));
		return client.execute(post);
	}
	

}

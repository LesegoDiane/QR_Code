package dev.simplesolution.generateqr.rest.client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RestClient {

	
	private static final Logger log = LoggerFactory.getLogger(RestClient.class);

	private int responseCode = 0;
	
	protected static final int DEFAULT_TIMEOUT = 5000;
	
	protected static final int HTTP_SUCCESS = 200;

	private static final String CHANNEL_SESSION_KEY = "channelSessionKey";

	private List<HttpCookie> httpCookies = new ArrayList<HttpCookie>();
	
	protected URL buildURL(String baseUrl, String path) throws MalformedURLException {
		return new URL(baseUrl.concat(path));
	}

	protected RestClientResponse GET(URL u, int timeout, Map<String, String> headers) throws Exception {
		log.info("-----------get request--------------------------------------");
		
		String userPassword = "fnbbotswana:FnB33B@tswana";
		//String userPassword = username + ":" + password;
		log.info("-----------url--------------------------------------"+ u );
		String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());
		

		try {
			
			CookieManager manager = new CookieManager();
			
			manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
			
			CookieHandler.setDefault(manager);
			
			HttpURLConnection connection = (HttpURLConnection) u.openConnection();
						
			connection.setRequestProperty("Authorization", "Basic " + encoding);
			connection.setConnectTimeout(timeout);
			
			connection.setReadTimeout(timeout);

			connection.setRequestMethod("GET");
			
			connection.setRequestProperty("Content-Type", "application/json");
			
			
			
			if(httpCookies != null && httpCookies.size() > 0) {
				
				System.out.println("Cookie: " + StringUtils.join(httpCookies.toArray(), ";") + System.lineSeparator());
				
				connection.setRequestProperty("Cookie", StringUtils.join(httpCookies.toArray(), ";"));
			}
			
			// We add headers if any
			if (headers != null && headers.size() > 0) {
				
				Iterator<Entry<String, String>> iterator = headers.entrySet().iterator();
				
				while (iterator.hasNext()) {
					
					Map.Entry<String, String> mapEntry = (Map.Entry<String, String>) iterator.next();
					
					connection.setRequestProperty(mapEntry.getKey().toString(), mapEntry.getValue().toString());
				}
			}

			String line;
			
			StringBuilder builder = new StringBuilder();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

		

			setResponseCode(connection.getResponseCode());
			
			String response = builder.toString();
			
			
			
			if (response.length() < 700) {
				log.info("REST Response Body: " + response);
			} else {
				log.info("REST Response Body:  TOO BIG FOR LOGS " + response.substring(0,699));
			}
			
			
			return buildResponse(response.toString());
			
		} finally {
		}

	}
	
	protected RestClientResponse POST(URL url, String json) throws MalformedURLException, IOException {
		return(POST(url, json, DEFAULT_TIMEOUT, null) );
	}
	
	protected RestClientResponse POST(URL u, String json, int timeout, Map<String, String> headers) throws MalformedURLException, IOException {
		
		try {
			
			CookieManager manager = new CookieManager();
			
			manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
			
			CookieHandler.setDefault(manager);
			
			log.info("REST Url: " + u.toString());
			log.info("REST Request Body: " + json);
			
			HttpURLConnection connection = (HttpURLConnection) u.openConnection();
		
			connection.setConnectTimeout(timeout);
			
			connection.setReadTimeout(timeout);

			connection.setRequestMethod("POST");
			
			connection.setRequestProperty("Content-Type", "application/json");
			
			connection.setDoOutput(true);
			
			connection.setInstanceFollowRedirects(false);
			
			if(httpCookies != null && httpCookies.size() > 0) {
				
				System.out.println("Cookie: " + StringUtils.join(httpCookies.toArray(), ";") + System.lineSeparator());
				
				connection.setRequestProperty("Cookie", StringUtils.join(httpCookies.toArray(), ";"));
			}
			
			// We add headers if any
			if (headers != null && headers.size() > 0) {
				
				Iterator<Entry<String, String>> iterator = headers.entrySet().iterator();
				
				while (iterator.hasNext()) {
					
					Map.Entry<String, String> mapEntry = (Map.Entry<String, String>) iterator.next();
					
					connection.setRequestProperty(mapEntry.getKey().toString(), mapEntry.getValue().toString());
				}
			}
		
			if (json != null) {
				
				OutputStream os = connection.getOutputStream();
				
				os.write(json.getBytes());
			
				os.flush();
			}

			String line;
			
			StringBuilder builder = new StringBuilder();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			setResponseCode(connection.getResponseCode());
			
			String response = builder.toString();
			
			log.info("REST Response Code: " + responseCode);
			log.info("REST Response Body: " + response);
			
			CookieStore cookieJar = manager.getCookieStore();
			
			List<HttpCookie> cookies = cookieJar.getCookies(); 

			if (cookies.size() > 0)
				httpCookies.addAll(cookies);
			
			return buildResponse(response);
			
		} finally {
		}
	}
	
	protected String PUT(URL u, String json, int timeout, Map<String, String> headers) throws MalformedURLException, IOException {
		
		try {
			
			CookieManager manager = new CookieManager();
			
			manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
			
			CookieHandler.setDefault(manager);
			
			HttpURLConnection connection = (HttpURLConnection) u.openConnection();
		
			connection.setConnectTimeout(timeout);
			
			connection.setReadTimeout(timeout);

			connection.setRequestMethod("PUT");
			
			connection.setRequestProperty("Content-Type", "application/json");
			
			connection.setDoOutput(true);
			
			connection.setInstanceFollowRedirects(false);
			
			if(httpCookies != null && httpCookies.size() > 0) {
				
				System.out.println("Cookie: " + StringUtils.join(httpCookies.toArray(), ";") + System.lineSeparator());
				
				connection.setRequestProperty("Cookie", StringUtils.join(httpCookies.toArray(), ";"));
			}
			
			// We add headers if any
			if (headers != null && headers.size() > 0) {
				
				Iterator<Entry<String, String>> iterator = headers.entrySet().iterator();
				
				while (iterator.hasNext()) {
					
					Map.Entry<String, String> mapEntry = (Map.Entry<String, String>) iterator.next();
					
					connection.setRequestProperty(mapEntry.getKey().toString(), mapEntry.getValue().toString());
				}
			}
		
			if (json != null) {
				
				OutputStream os = connection.getOutputStream();
				
				os.write(json.getBytes());
			
				os.flush();
			}

			String line;
			
			StringBuilder builder = new StringBuilder();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			setResponseCode(connection.getResponseCode());
			
			String response = builder.toString();
			
			CookieStore cookieJar = manager.getCookieStore();
			
			List<HttpCookie> cookies = cookieJar.getCookies(); 

			if (cookies.size() > 0)
				httpCookies.addAll(cookies);
			
			return (response);
			
		} finally {
		}
	}

	protected void DELETE(URL u, int timeout, Map<String, String> headers) throws MalformedURLException, IOException {
		
		try {
		
			CookieManager manager = new CookieManager();
			
			manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
			
			CookieHandler.setDefault(manager);
						
			HttpURLConnection connection = (HttpURLConnection) u.openConnection();
			
			connection.setConnectTimeout(timeout);
			
			connection.setReadTimeout(timeout);

			connection.setRequestMethod("DELETE");
			
			connection.setRequestProperty("Content-Type", "application/json");
			
			connection.setDoOutput(true);
			
			connection.setInstanceFollowRedirects(false);
			
			if(httpCookies != null && httpCookies.size() > 0) {
				
				System.out.println("Cookie: " + StringUtils.join(httpCookies.toArray(), ";") + System.lineSeparator());
				
				connection.setRequestProperty("Cookie", StringUtils.join(httpCookies.toArray(), ";"));
			}

			// We add headers if any
			if (headers != null && headers.size() > 0) {
				
				Iterator<Entry<String, String>> iterator = headers.entrySet().iterator();
				
				while (iterator.hasNext()) {
					
					Map.Entry<String, String> mapEntry = (Map.Entry<String, String>) iterator.next();
					
					connection.setRequestProperty(mapEntry.getKey().toString(), mapEntry.getValue().toString());
				}
			}

			connection.connect();

			setResponseCode(connection.getResponseCode());
			
		} finally {
		}
	}
	
	protected RestClientResponse buildResponse(String responseBody) {
		RestClientResponse response = new RestClientResponse();
		
		if(HTTP_SUCCESS == getResponseCode()) {
			response.setSuccess(true);
			response.setResponseBody(responseBody);
		}
		
		return response;
	}

	protected int getResponseCode() {
		return responseCode;
	}

	protected void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	protected Map<String, String> getChannelSessionMap(String channelSessionKey) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put(CHANNEL_SESSION_KEY, channelSessionKey);
		return headers;
	}
	
	protected Map<String, String> getStandardHeaders() {		
		return new HashMap<>();
	}

}

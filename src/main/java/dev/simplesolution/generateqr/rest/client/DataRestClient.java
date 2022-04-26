package dev.simplesolution.generateqr.rest.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import dev.simplesolution.generateqr.doa.TrackedEntity;
import dev.simplesolution.generateqr.doa.VaccinationEntity;
import dev.simplesolution.generateqr.rest.client.RestClient;
import dev.simplesolution.generateqr.rest.client.RestClientResponse;
import dev.simplesolution.generateqr.rest.exceptions.RestClientException;
import org.json.*;

@Component
public class DataRestClient extends RestClient {
	
	private static final Logger LOG = Logger.getLogger(DataRestClient.class);
	
	//private static final String TfullPath = "https://fenyacovid.gov.bw/dhis/api/trackedEntityInstances.json?filter=Ewi7FUfcHAD:EQ:332810916&&filter=ciCR6BBvIT4:EQ:26777451996&ou=zwts3rCXPDU&ouMode=DESCENDANTS&program=yDuAzyqYABS&includeDescendants=true%22;";
	private static final String TbaseURL =  "https://fenyacovid.gov.bw/dhis/api/trackedEntityInstances.json?";	
	//332810916 /26777451996
	
	//private static final String VfullPath = "https://fenyacovid.gov.bw/dhis/api/33/events.json?&ou=zwts3rCXPDU&ouMode=DESCENDANTS&program=yDuAzyqYABS&trackedEntityInstance=ItJ9TctvWaS";
	private static final String VbaseURL =  "https://fenyacovid.gov.bw/dhis/api/33/events.json?&ou=zwts3rCXPDU&ouMode=DESCENDANTS&program=yDuAzyqYABS&";
	
	
	private URL buildURL(String path) throws  MalformedURLException {
		
		return buildURL(TbaseURL,path);
	}

	
	public Map getTrackedEntity(String id, String cellNumber) throws RestClientException, IOException {
		
		String Tpath = "filter=Ewi7FUfcHAD:EQ:"+id+"&&filter=ciCR6BBvIT4:EQ:"+cellNumber+"&ou=zwts3rCXPDU&ouMode=DESCENDANTS&program=yDuAzyqYABS&includeDescendants=true%22";
		
		
		
		LOG.info("Webservice invoke Endpoint: " + TbaseURL +""+Tpath );
		
		RestClientResponse response = null;
		try {
			response = GET(buildURL(TbaseURL, Tpath),0,null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		if(response.isSuccess()) {
			

			Map<String, String> map = new HashMap<String, String>();
			
			JSONObject obj = new JSONObject(response.getResponseBody());

			JSONArray pageName = obj.getJSONArray("trackedEntityInstances");
			JSONArray arr = pageName.getJSONObject(0).getJSONArray("attributes");
			 
			String uid  = pageName.optJSONObject(0).getString("trackedEntityInstance");
			
			map.put("uid", uid);
			//JSONArray arr = obj.getJSONArray("posts"); 
			
			for (int i = 0; i < arr.length(); i++)
			{
			    String displayName = arr.getJSONObject(i).getString("displayName");
			    String value = arr.optJSONObject(i).getString("value");
			    map.put(displayName, value);
				//System.out.println(displayName +": "+value);
				
			}
			
			System.out.println(map);
			//return new Gson().fromJson(response.getResponseBody(),TrackedEntityResponse.class);
				return map;
		} 
		
		throw new RestClientException("Unsuccessful Response from  fenya covid api /dhis/api/33");
			
			
		}
	

	
	public  JSONArray getVaccinationData(String uid) throws RestClientException, IOException {	

		String Vpath = "trackedEntityInstance="+ uid;
		Map<String, String> map = new HashMap<String, String>();
		LOG.info("Webservice invoke Endpoint: " + VbaseURL+""+Vpath);
		
		RestClientResponse response = null;
		try {
			response = GET(buildURL(VbaseURL, Vpath),0,null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
		if(response.isSuccess()) {
			String str = "";
			
			JSONObject obj = new JSONObject(response.getResponseBody());			
			JSONArray pageName = obj.getJSONArray("events"); //one to many
			JSONArray jsonArray = new JSONArray(); 
		
			for (int i = 0; i < pageName.length(); i++)
			{
				
			
			System.out.println("EVENT NUMBER: "+ i);
			String orgUnitName  =  pageName.getJSONObject(i).getString("orgUnitName");
			map.put(orgUnitName, orgUnitName);
			System.out.println("orgUnitName :"+  orgUnitName);
			str += "orgUnitName="+ orgUnitName +","; 
			String trackedEntityInstance  =  pageName.getJSONObject(i).getString("trackedEntityInstance");
			map.put(trackedEntityInstance, trackedEntityInstance);
			System.out.println("trackedEntityInstance :"+  trackedEntityInstance);
			str += "trackedEntityInstance="+ trackedEntityInstance +","; 
			
			String eventDate  =  pageName.getJSONObject(i).getString("eventDate");
			map.put("eventDate", eventDate);
			str += "eventDate="+eventDate+",";
			System.out.println("eventDate :"+  eventDate);
			System.out.println("Number of Events:"+  pageName.length());
			
			
			 JSONArray arr = pageName.getJSONObject(i).getJSONArray("dataValues");
			//JSONArray arr = obj.getJSONArray("posts"); 
			
				for (int n = 0; n < arr.length(); n++)
				{
			
				    String displayName = arr.getJSONObject(n).getString("dataElement");//name
				    String value = arr.optJSONObject(n).getString("value");
				    
				    str += displayName+"="+value +","; 
				    //LUIsbsm3okG =  dose number
				    //Yp1F4txx8tm = batch number
				    map.put(displayName, value);
					System.out.println(displayName +": "+value);
					
				}
				
				
				jsonArray.put(map);
			}
			
			
			//return new Gson().fromJson(response.getResponseBody(),VaccinationEntity.class);
			System.out.println(str);

			return jsonArray;
		} 
		
		throw new RestClientException("Unsuccessful Response from  Vaccinantion fenya covid api /dhis/api/33");
			
			
		}
	
	
	
	
	
	
	
	
	
	
	
	
	

}

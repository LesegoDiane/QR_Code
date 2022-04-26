package dev.simplesolution.generateqr.services.component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.simplesolution.generateqr.doa.TrackedEntity;
import dev.simplesolution.generateqr.rest.client.DataRestClient;


@Component
public class TrackedEntityDataComponent {
	
	
	@Autowired
	private DataRestClient dataRestClient;
	
	public Map<String, List<TrackedEntity>> loadAttributes() throws Exception {
	
		Map<String, List<TrackedEntity>>  listAttributed = new LinkedHashMap<>();
		
		//dataRestClient.getTrackedEntity().getResultsMap().get(listAttributed);
		
		return listAttributed;
		
		
	}
}

package dev.simplesolution.generateqr.rest.client;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dev.simplesolution.generateqr.doa.TrackedEntity;



public class TrackedEntityResponse {

	private Map<String, List<TrackedEntity>> resultsMap = new LinkedHashMap<>();

	public Map<String, List<TrackedEntity>> getResultsMap() {
		return resultsMap;
	}

	public void setResultsMap(Map<String, List<TrackedEntity>> resultsMap) {
		this.resultsMap = resultsMap;
	}
}

package dev.simplesolution.generateqr.rest.client;

public class RestClientResponse {

	private boolean success;
	
	private String responseBody;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

}

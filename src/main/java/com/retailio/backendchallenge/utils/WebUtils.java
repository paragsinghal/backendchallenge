package com.retailio.backendchallenge.utils;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Service("WebUtils")
public class WebUtils {

	private Client client;
	public static final WebUtils INSTANCE = new WebUtils();

	public WebUtils() {
		setClient(Client.create());
		getClient().setConnectTimeout(20000);
		getClient().setReadTimeout(60000);
	}

	public ClientResponse doCall(String serverUrl, HttpMethod requestType,
			String query) {

		WebResource webResource = getClient().resource(serverUrl);

		ClientResponse response = null;
		switch (requestType) {
		case POST:
			response = webResource.type("application/json").post(
					ClientResponse.class, query);
			break;
		case PUT:
			response = webResource.type("application/json").put(
					ClientResponse.class, query);
			break;
		case GET:
			response = webResource.type("application/json").get(
					ClientResponse.class);
			break;
		case DELETE:
			response = webResource.type("application/json").delete(
					ClientResponse.class, query);
			break;
		default:
			return null;
		}

		return response;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}

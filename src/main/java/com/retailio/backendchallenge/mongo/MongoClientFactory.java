package com.retailio.backendchallenge.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.retailio.backendchallenge.utils.ConfigUtils;
import com.retailio.backendchallenge.utils.LogFactory;

/**
 * @author parag
 *
 *         This is used to setup mongo client. Configurable mongo host/port
 *         based on environment. Connection pooling. Authentication is handled
 *         here.
 */
public class MongoClientFactory {

	@Autowired
	private LogFactory logFactory;

	@SuppressWarnings("static-access")
	private Logger logger = logFactory.getLogger(MongoClientFactory.class);

	private static ConfigUtils configUtils = new ConfigUtils();

	private MongoClient client = null;

	public static final MongoClientFactory INSTANCE = new MongoClientFactory();

	private MongoClientFactory() {
		logger.info("MongoClientFactory is inialized");
		logger.error("test error message");
		MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
		MongoClientOptions options = builder.connectionsPerHost(10).build();
		try {

			List<ServerAddress> seeds = new ArrayList<ServerAddress>();
			String hosts = configUtils.getStringValue("MONGO_HOST");
			String PORT = configUtils.getStringValue("MONGO_PORT");

			if (hosts != null && !hosts.isEmpty() && PORT != null
					&& !PORT.isEmpty()) {
				String[] hostArray = hosts.split(",");
				for (String host : hostArray) {
					seeds.add(new ServerAddress(host, Integer.parseInt(PORT)));
				}
			}
			Boolean useAuthentication = configUtils
					.getBooleanValue("useAuthentication");

			if (useAuthentication != null && useAuthentication) {
				List<MongoCredential> credentials = new ArrayList<MongoCredential>();
				credentials.add(MongoCredential.createScramSha1Credential(
						configUtils.getStringValue("MONGO_USERNAME"),
						configUtils.getStringValue("MONGO_DB_NAME"),
						configUtils.getStringValue("MONGO_PASSWD")
								.toCharArray()));
				client = new MongoClient(seeds, credentials, options);
			} else {
				client = new MongoClient(seeds, options);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public MongoClient getClient() {
		logger.info("MongoClientFactory get client called");
		return client;
	}

	public void setClient(MongoClient client) {
		this.client = client;
	}

}

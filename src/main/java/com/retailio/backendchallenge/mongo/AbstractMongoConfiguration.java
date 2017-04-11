package com.retailio.backendchallenge.mongo;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClient;
import com.retailio.backendchallenge.utils.ConfigUtils;

/**
 * @author parag
 *
 *         Service to get Mongo opertions. Spring MongoDb operations are set up
 *         here.
 */
@Service
public class AbstractMongoConfiguration {

	private MongoClientFactory mongoClientFactory = MongoClientFactory.INSTANCE;

	private static ConfigUtils configUtils = new ConfigUtils();
	private static final String DATABASE_NAME = configUtils
			.getStringValue("MONGO_DB_NAME");
	private static MongoOperations mongoOperations;
	private static String COLLECTION;

	public AbstractMongoConfiguration() {
		setMongoOperations(mongoClientFactory.getClient(), DATABASE_NAME);
	}

	public String getCOLLECTION() {
		return COLLECTION;
	}

	public void setCOLLECTION(String cOLLECTION) {
		COLLECTION = cOLLECTION;
	}

	public MongoOperations getMongoOperations() {
		return mongoOperations;
	}

	public static void setMongoOperations(MongoClient client,
			String databaseName) {
		try {

			mongoOperations = (MongoOperations) new MongoTemplate(client,
					databaseName);
		} catch (Exception ex) {
			// Throw error here
		}
	}

	public String getDATABASE_NAME() {
		return DATABASE_NAME;
	}
}

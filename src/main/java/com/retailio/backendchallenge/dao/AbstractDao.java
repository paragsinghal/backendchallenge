package com.retailio.backendchallenge.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mongodb.WriteResult;
import com.retailio.backendchallenge.entity.AbstractEntity;
import com.retailio.backendchallenge.mongo.AbstractMongoConfiguration;

/**
 * @author parag
 *
 *Abstract Dao for Mongo related operations. Only basic operations supported.
 */
@Service
public class AbstractDao {
	
	@Autowired
	AbstractMongoConfiguration abstractMongoConfiguration;

	public static final String ORDER_DESC = "desc";
	public static final String ORDER_ASC = "asc";
	public static final String QUERY_AND_OPERATOR = "&&";

	public static final long NO_START = 0;
	public static final long NO_LIMIT = -1;
	public static final long UNINITIALIZED = -1L;

	public AbstractDao() {
		super();
	}

	protected <E extends AbstractEntity> void saveEntity(E entity) {
		setEntityDefaultProperties(entity);
		abstractMongoConfiguration.getMongoOperations().save(entity, entity.getClass().getSimpleName());
	}

	protected <E extends AbstractEntity> void saveAllEntities(
			Collection<E> entities, String collectionName) {
		setEntityDefaultProperties(entities);
		if (entities != null && !entities.isEmpty()) {
			for (E entity : entities) {
				abstractMongoConfiguration.getMongoOperations().save(entity, collectionName);
			}
		}
	}

	protected <E extends AbstractEntity> void updateEntity(E entity, Query q,
			Update u) {
		setEntityDefaultProperties(entity);
		abstractMongoConfiguration.getMongoOperations().upsert(q, u, entity.getClass().getSimpleName());
	}

	public <T extends AbstractEntity> T findAndModifyEntity(Query q, Update u,
			FindAndModifyOptions o, Class<T> calzz) {
		return abstractMongoConfiguration.getMongoOperations().findAndModify(q, u, o, calzz,
				calzz.getSimpleName());
	}

	public <T extends AbstractEntity> List<T> getEntities(Class<T> calzz) {
		return abstractMongoConfiguration.getMongoOperations().findAll(calzz, calzz.getSimpleName());
	}

	public <T extends AbstractEntity> T getEntityById(String id, Class<T> calzz) {
		Query query = new Query(Criteria.where("_id").is(id));
		query.addCriteria(Criteria.where("enabled").is(true));
		return abstractMongoConfiguration.getMongoOperations()
				.findOne(query, calzz, calzz.getSimpleName());
	}

	public <T extends AbstractEntity> List<T> runQuery(Query query,
			Class<T> calzz) {
		return abstractMongoConfiguration.getMongoOperations().find(query, calzz, calzz.getSimpleName());
	}

	public <T extends AbstractEntity> int queryCount(Query query, Class<T> calzz) {
		return abstractMongoConfiguration.getMongoOperations().find(query, calzz, calzz.getSimpleName())
				.size();
	}

	public <T> int deleteEntityById(String id, Class<T> calzz) {
		Query query = new Query(Criteria.where("_id").is(id));
		WriteResult result = abstractMongoConfiguration.getMongoOperations().remove(query, calzz,
				calzz.getSimpleName());
		return result.getN();
	}

	public static <E extends AbstractEntity> void setEntityDefaultProperties(
			Collection<E> entities) {
		if (entities != null && !entities.isEmpty()) {
			for (E entity : entities) {
				setEntityDefaultProperties(entity);
			}
		}
	}

	public static <E extends AbstractEntity> void setEntityDefaultProperties(
			E entity) {
		if (StringUtils.isEmpty(entity.getId())) {
			entity.setCreationTime(System.currentTimeMillis());
			entity.setCreatedBy(entity.getCreatedBy());
		}
		entity.setLastUpdatedTime(System.currentTimeMillis());
	}
}

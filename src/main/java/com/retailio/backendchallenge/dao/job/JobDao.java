package com.retailio.backendchallenge.dao.job;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.retailio.backendchallenge.dao.AbstractDao;
import com.retailio.backendchallenge.entity.AbstractEntity;
import com.retailio.backendchallenge.entity.githubjob.JobFrequency;
import com.retailio.backendchallenge.entity.githubjob.JobPojo;
import com.retailio.backendchallenge.entity.githubjob.JobType;

/**
 * @author parag
 *
 *         Job Dao Implementation.
 */
@Service
public class JobDao extends AbstractDao {

	public JobDao() {
		super();
	}

	public void create(JobPojo p) throws Exception {
		p.setCreationTime(System.currentTimeMillis());
		p.setLastUpdatedTime(System.currentTimeMillis());
		try {
			if (p != null) {
				saveEntity(p);
			}
		} catch (Exception ex) {
			throw ex;
		}
	}

	public void update(JobPojo p) {
		p.setLastUpdatedTime(System.currentTimeMillis());
		try {
			if (p != null) {
				saveEntity(p);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void updateAll(List<JobPojo> p) {
		try {
			if (p != null && !p.isEmpty()) {
				saveAllEntities(p, JobPojo.class.getSimpleName());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public JobPojo getById(String id) {
		JobPojo feature = null;
		try {
			if (!StringUtils.isEmpty(id)) {
				feature = (JobPojo) getEntityById(id, JobPojo.class);
			}
		} catch (Exception ex) {
			// log Exception
			feature = null;
		}
		return feature;
	}

	public void update(JobPojo p, Query q, Update u) {
		try {
			if (p != null) {
				updateEntity(p, q, u);
			}
		} catch (Exception ex) {
		}
	}

	public int deleteById(String id) {
		int result = 0;
		try {
			result = deleteEntityById(id, JobPojo.class);
		} catch (Exception ex) {
			// throw Exception;
		}

		return result;
	}

	public List<JobPojo> getJobs(String id, JobType jobType,
			JobFrequency jobFrequency, Long fromTime, Long toTime,
			Integer start, Integer limit) throws Exception {

		try {
			Query query = new Query();
			if (id != null) {
				query.addCriteria(Criteria.where("id").is(id));
			} else {
				if (jobType != null) {
					query.addCriteria(Criteria
							.where(JobPojo.Constants.JOB_TYPE).is(jobType));
				}

				if (jobFrequency != null) {
					query.addCriteria(Criteria.where(
							JobPojo.Constants.JOB_FREQUENCY).is(jobFrequency));
				}

				if (fromTime != null && toTime != null) {
					query.addCriteria(Criteria
							.where(AbstractEntity.Constants.CREATION_TIME)
							.lt(toTime).gte(fromTime));
				} else {
					if (toTime != null) {
						query.addCriteria(Criteria.where(
								AbstractEntity.Constants.CREATION_TIME).lt(
								toTime));
					}
					if (fromTime != null) {
						query.addCriteria(Criteria.where(
								AbstractEntity.Constants.CREATION_TIME).gte(
								fromTime));
					}
				}
			}

			if (limit != null) {
				query.limit(limit);
			}

			if (start != null) {
				query.skip(start);
			} else {
				query.skip(0);
			}

			List<JobPojo> result = runQuery(query, JobPojo.class);

			return result;
		} catch (Exception e) {
			throw e;
		}
	}

}

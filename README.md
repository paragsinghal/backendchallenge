# backendtest
Retailio Backend Challenge App

Assumptions:

1) All the queries pertaining to date will have long argument.
2) Repo Owner name will be provided with repo name.
3) Users need to login first to perform any post/delete/get operation.\
4) /job/** are secured and need authentication. Only admin role has access.
5) Mongo server is running with following properties 
MONGO_DB_NAME=retailiotest
MONGO_HOST=localhost
MONGO_PORT=27017

APIS

1) List Jobs Api:   (Path: GET /job?inputs as query params)
    
    inputs:  
     1) id : Mongo id of the record
	 2) jobType : jobType eg: GITHUB_POLL (ENUM)
	 3) jobFrequency : Frequency of the job like DAILY (ENUM)
	 4) fromTime : Date after which we want the jobs created (Long)
	 5) toTime : Date before which we want the jobs by creation time (Long)
	 6) start : start of the result set (Integer) for pagination
	 7) limit : size of the result set (Integer) for pagination
     
* Nothing is mandatory. Could be extended to use with user authentication.
* Rest all inputs can be null and if provided will give the intersection of results by given parameters.
* Will like to extend this to searching inside the jobs for repo name etc.


2)Add Job API: (POST : /job/) content-type should be application json

    inputs: 
    
    QueryParams: JobType - Mandatory
    			JobFrequency - optional(DAILY default)
    
   Post Params should be sent as body
   	String repositoryName;
	String repositoryOwner;
	List<Filter> filters;
	Filter - FilterType - TITLE/USER and value
								
    
    Eg Input:
    
    {
  		"repositoryName" : "corefx",
		"repositoryOwner" : "dotnet",
		"filters" : [{"filterType" : "USER", "value" : "joshfree"}]
	}
     
     * Mandatory fields : JobType, repositoryName, repositoryOwner, 1 filter
     * Response is returned with the id of the object saved.

3) DELETE JOB API  (DELETE:  /job) 

    inputs: id of the job
    
CRONS

1) Running for Daily jobs
2) Running for Hourly Jobs

* these will execute and log on the console and add to the file the new issues if found based on the criteria.

Extras

1) Abstraction at every layer

Mongo Client Factory
Mongo Configuration
Abstract Dao


2) Logging using log4j.

3) profile specific configuration using properties. Properties are detached from the code.

4) Global exception handlers for spring. 

5) Maven is used for dependency management and build.
6) Swagger is used for easy access to APIs.

etc.


Authentication

/job will prompt a session based login page. If you are already present in the database then based on role access.

logging out:  /login?logout

Db entry for User:
use retailiotest
db.BasicUserDetails.insert({ "username" : "parag1", "password" : "hello",  "grantedAuthorities" : ["ROLE_ADMIN"]})
db.BasicUserDetails.insert({ "username" : "parag2", "password" : "hello",  "grantedAuthorities" : ["ROLE_USER"]})

Only Admin has access to /job/** portal


/backendtest/swagger-ui.html can be used for easy access to api's.

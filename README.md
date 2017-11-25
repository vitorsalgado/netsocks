# NetSocks
[![Build Status](https://travis-ci.org/vitorsalgado/netsocks.svg?branch=master)](https://travis-ci.org/vitorsalgado/netsocks)  

Technical test for hiring process.  
These projects where developed using different languages and platforms. Except for the Campaign Update Receiver, which uses Node.js, all 
projects uses Gradle as the build system.

## Content
- [Requirements](#requirements);  
- [Tech](#tech);  
- [Running](#running);  
- [Projects](#projects);    
	- [Campaign ( Campanhas )](#campaign-api);  
	- [Associate Fan ( Sócio Torcedor )](#associate-fan-api);
	- [Stream](#stream);  
	- [Load Tests](#load-tests);  
	- [Unit Tests](#unit-tests);  
	- [E2E Tests](#e2e-tests).
- [Dead Lock](DEAD_LOCK.md);
- [Parallel Stream](PARALLEL_STREAM.md).

## Requirements
- Java 8;
- Docker ( a version which already contains [multi stage](https://docs.docker.com/engine/userguide/eng-image/multistage-build/) available );
- Docker Compose;
- Make.

## Tech
- Java 8;
- Scala ( load and e2e tests );
- Swagger documentation;
- Mongodb as the database solution;
- Redis;
- Spring Boot;
- RxJava;
- Hystrix;
- Log4j for logging;
- WireMock for API stubbing test;
- Gatling for load tests;
- Rest Assured for API e2e tests;
- Node.js for simple Redis channel subscriber;
- Gradle.

## Running
There's a `makefile` on project root which contains all commands to start, test and clean all projects. It's the easiest way to do all tasks
To start all APIs with Mongodb and Redis instances, simple type on a console: 
```
make
```

To clear all, run: `make down`

Refer to **Projects** section below for more details about each project.


## Projects

### Campaign API
The campaigns API was develop with Java and uses a simple N-tier design as it does not contains complex business rules.  
There's no structure for *Teams*. Each campaign only contains a field, *favoriteTeamId*, which is a simple identifier.  
Every time a campaign already registered changes, the API publishes it to a **Redis** channel. **Hystrix** is used to control failures on channel publish;    
To run the API, navigate to repository root and type on a console:
```
make campaign-api
or
make campaign-api-docker
```

**Default location**: [http://localhost:8080](http://localhost:8080)  
**Swagger**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) 

---



### Associate Fan API
The stack is the same as the other api, but it is design in a reactive way with RxJava.  
I wanted to test this implementation approach with RxJava in a Rest API.  
To run the API, navigate to repository root and type on a console:
```
make fan-api
or
make fan-api-docker
```

**Default location**: [http://localhost:8081](http://localhost:8081)  
**Swagger**: [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html) 

---



### Stream 
To run the stream test, go to repository root and type on a console:  
```
make stream input=YOUR_INPUT_HERE
or
make stream-docker input=YOUR_INPUT_HERE
```

---



### Load Tests
The load tests uses [Gatling](https://gatling.io/) and are written in Scala.  
They attempt to simulate more than 100 users using the APIs.  
To run the tests, first you need to start all APIs:
```
# start apis first!
make

# to run campaign load tests
make load-tests-campaign

# to run support fan load tests
make load-tests-fan
```

---



### Unit Tests
To run all unit tests of all projects with docker compose:
```
make test
```

---



### E2E Tests
```
make e2es
```

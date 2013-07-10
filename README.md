Guiberest
============

(RESTEasy + Guice + Hibernate) - A basic Mavenized REST Web Service with Hibernate, Guice, RESTEasy, Jacksonate, and CasHmac (HMAC/CAS Authentication)

Description
---------------
This sample application gets you up and running very quickly with a RESTful Web service that includes the following features:

1. Shared library to provide domain objects to both client and server (service and consumver)
2. Fully functional RESTful (JSON output) service (guiberest-server) with sample consumer (guiberest-client)
3. Support for CAS and HMAC-based authentication using our CasHmac library
4. Serialization and Deserialization of object arrays using the JSON Table Schema recommended by the dataprotocols project (https://github.com/dataprotocols/dataprotocols) and implemented by our Jacksonate project
5. Guice dependency injection (to set up Servlets, RESTEasy, Serialization)
6. Hibernate (uses H2 sample database by default)
7. Sample integration tests for RESTful Web service methods using Cucumber (in guiberest-client)

Getting Started
---------------

You should first clone the CasHmac and Jacksonate repositories, compile them, and install them:

Download the libraries
```bash
git clone https://github.com/QBRC/CasHmac.git
git clone https://github.com/QBRC/Jacksonate.git
```

Compile and install the CasHmac library
```bash
cd CasHmac/
mvn clean install  
```

Compile and install the Jacksonate library
```bash
cd Jacksonate/
mvn clean install  
```

Then clone this repository and run `mvn clean install` in the base directory of the repository. This will compile, package, and install all 4 modules after downloading any necessary dependencies.
By default, the project uses an auto-generated H2 database.  The "guiberest-server" project's pom.xml includes a sample MySQL configuration, as well.

Download the Guiberest Sample Application
```bash
git clone https://github.com/QBRC/Guiberest.git
```

Compile the Guiberest library
```bash
cd Guiberest/
mvn clean install  # May Fail
```

Start the sample RESTful Web service (leave this one up and running)
```bash
cd guiberest-server
mvn jetty:run
```

Once the `Server` module is deployed and running, you should be able to run the Client.

```
cd guiberest-client
mvn exec:java
```

You should see this output from the sample application (you may also see some CasHmac messages regarding HMAC).
```bash
Customer: 21 - Maurice Mango<br/>
Customer: 23 - Chloe Stanley<br/>
Sale: 32 - 22/11 - 59.65<br/>
Sale: 33 - 23/12 - 8.95<br/>
Sale: 37 - 25/11 - 69.65<br/>
Sale: 38 - 25/12 - 9.95<br/>
```

Run Sample Web application with CAS Integration Tests
```bash
# open new console window
cd guiberest-client/
mvn jetty:run
```

Browse to https://<your_host_name>:9091, and you should see information that walks you through running integration tests with CAS authentication. In short, you log in to the test Guiberest client website with CAS, and the site in turn proxies your CAS credientials to the Guiberest server for authentication.

You can also run the comprehensive integration tests (with HMAC authentication) from the client:
```bash
cd guiberest-client
mvn integration-test
```

Above the "BUILD SUCCESS" message, you should see some output like:
```bash
Tests run: 150, Failures: 0, Errors: 0, Skipped: 0
```

Modules
-------

###Shared

This module includes packages which should be shared between the server and the client. It includes all Domain objects as well as the annotated interfaces describing how the RESTEasy web services should function. This is a very simple package which just wraps up the shared classes as a `jar` file.

###Server

This module depends on the `Shared` module and implements the server-side code necessary for the web-service. It uses Guice for DI and Hibernate for ORM. It implements the interface(s) described in the `Shared` module to serve the REST services. It will be packaged as a `war` file and must be deployed to some Servlet container (Tomcat, Jetty, etc.).

###Client and Web-Client

These modules offer no new functionality and likely would be excluded from any forks of this project deployed in production, but merely provide examples of how to use the web service "remotely".

Authentication
--------------

If you wish to authenticate users to your Guiberest-based service, be sure to check out CasHmac at https://github.com/QBRC/CasHmac/.  CasHmac is a library that provides both client and server support for CAS and HMAC authentication for RESTEasy RESTful Web services.


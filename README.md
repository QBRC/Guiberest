CasHmac-Sample Application
===============

About
-----
This is a sample application intended to demonstrate a RESTful Web service that implements the CasHmac library for authentication.  Please see https://github.com/QBRC/CasHmac for all the details on CasHmac configuration.

Project References
---------------------------
This sample application is based on Resprirnate: (RESTEasy + Spring + Hibernate) - A basic Mavenized REST Web Service with Hibernate, Spring, and RESTEasy.  See https://github.com/QBRC/Resprirnate for details.

Getting Started
---------------

Follow the steps below to download, compile and run the sample projects.

Download the CasHmac library and the Sample Application
```bash
git clone https://github.com/QBRC/CasHmac.git
git clone https://github.com/QBRC/CasHmac-Sample.git
```

Compile the CasHmac library
```bash
cd CasHmac/
mvn clean install  
```

Compiles sample applications
```bash
cd ../CasHmac-Sample/
mvn clean install  
```

Start the sample RESTful Web service (leave this one up and running)
```bash
cd server
mvn jetty:run
```

Run Sample Console Application
```bash
# open new console window
cd CasHmac-Sample/client/
mvn exec:java
```

You should see this output from the sample application
```bash
Hello, anonymous user from QBRC : Anonymous Message
Hello from QBRC : Authenticated Message
User [id=thomas, password=??, secret=123456789]
```

Run Sample Web application
```bash
# open new console window
cd CasHmac-Sample/web-client/
mvn jetty:run
```

Browse to http://127.0.0.1:9091, and you should see the following output
```bash
Hello, anonymous user from QBRC : Anonymous Message
Hello from QBRC : Authenticated Message
User [id=thomas, password=??, secret=123456789]
```

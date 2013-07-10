package edu.swmed.qbrc.guiberest.webapp.guice;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.reflections.Reflections;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class MyServlet extends HttpServlet {
	
	@Inject
	ClientIdentification clientIdentification;

	private static final long serialVersionUID = -5697849610754976017L;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		StringBuilder out = new StringBuilder();
		String userName = "";
		if (req.getUserPrincipal() != null && req.getUserPrincipal().getName() != null) {
			userName = req.getUserPrincipal().getName();
			clientIdentification.setLoggedInClientId(userName);
		}
		
		out.append("<html><head><title>Integration Testing Client</title>" +
				"<style type='text/css'>" +
				"	pre {" +
				"		background-color: #dfdfdf;" +
				"	}" +
				"</style>" +
				"</head><body>" +
				"<a href=\"/logout/?logoutRequest=true\">Log Out</a> - Logged in as " + userName +
				"<h2>Instructions</h2><p>This sample applications doubles as an integration testing launchpad for integration testing with CAS-based authentication." +
				"Since there's no easy way to implement integration tests using CAS authentication, we've chosen to let you run the integration " +
				"tests interactively.  To run the same integration tests with HMAC authentication, simply enter <strong><em>mvn integration-test</em></strong> " +
				"while in the <em>probemapper-client</em> directory.</p>" +
				"<p>To run the integration tests with CAS authentication, follow these steps:" +
				"<ol>" +
				"<li>If you're logged in as another user, use the <em>Log Out</em> link to log out.</li>" +
				"<li>Log in as the <em>" + getProperty("clientid-thomas") + "</em> CAS user with <em><strong>" + getProperty("cas-password-thomas") + "</strong></em> as the password.  The tests for <em>" + getProperty("clientid-thomas") + "</em> will be run.</li>" +
				"<li>Log out and log in again as the <em>" + getProperty("clientid-roger") + "</em> CAS user with <em><strong>" + getProperty("cas-password-roger") + "</strong></em> as the password.  The tests for <em>" + getProperty("clientid-roger") + "</em> will be run.</li>" +
				"<li>Log out and log in again as the <em>" + getProperty("clientid-sean") + "</em> CAS user with <em><strong>" + getProperty("cas-password-sean") + "</strong></em> as the password.  The tests for <em>" + getProperty("clientid-sean") + "</em> will be run.</li>" +
				"<li>Log out and log in again as the <em>" + getProperty("clientid-irsauditer") + "</em> CAS user with <em><strong>" + getProperty("cas-password-irsauditer") + "</strong></em> as the password.  The tests for <em>" + getProperty("clientid-irsauditer") + "</em> will be run.</li>" +
				"<li>Log out and log in again as the <em>" + getProperty("clientid-guest") + "</em> CAS user with <em><strong>" + getProperty("cas-password-guest") + "</strong></em> as the password.  The tests for <em>" + getProperty("clientid-guest") + "</em> will be run.</li>" +
				"<li>Log out and log in again as the <em>" + getProperty("clientid-cook") + "</em> CAS user with <em><strong>" + getProperty("cas-password-cook") + "</strong></em> as the password.  The tests for <em>" + getProperty("clientid-cook") + "</em> will be run.</li>" +
				"</ol>" +
				"</p>");
		
		
		out.append("<h2>Test Output</h2><pre>");

		if ("thomas-test,roger-test,sean-test,irsauditer-test,guest-test,cook-test".contains(clientIdentification.getLoggedInClientId())) {
		
			JUnitCore junit = new JUnitCore();
			Reflections reflections = new Reflections("edu.swmed.qbrc.guiberest.webapp.integration.tests");
			for (Class<?> clazz : reflections.getTypesAnnotatedWith(RunWith.class)) {
				out.append("<p>Running Integration Tests for " + clazz.getName());
				Result result = junit.run(clazz);
				out.append(" ..... success? <strong>" + ((Boolean)result.wasSuccessful()).toString().toUpperCase() + "</strong>");
				out.append("<br/>Run: " + result.getRunCount() + "; Failures: " + result.getFailureCount() + "; Ignored: " + result.getIgnoreCount() + "</p>");
				if (! result.wasSuccessful()) {
					out.append("<p>Failures:</p><ul>");
					for (Failure failure : result.getFailures()) {
						out.append("<li>" + failure.getMessage() + "<p>" + failure.getTrace() + "</p></li>");
					}
					out.append("</ul>");
				}
			}
		} else {
			out.append("<p>Incorrect User for Integration Testing</p>");
		}
		
		// Send to browser
		out.append("</pre>");
		out.append("</body></html>");
		resp.getOutputStream().print(out.toString());
	}
	
    private String getProperty(String resource) {
        InputStream inputStream = getClass().getResourceAsStream("/integration-test.properties");

        if (inputStream == null) {
            throw new RuntimeException();
        }

        try {
            Properties properties = new Properties();
            properties.load(inputStream);

            return properties.getProperty(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }	
	
}
package edu.swmed.qbrc.guiberest.webapp.guice;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.inject.Singleton;

@Singleton
public class LogoutServlet extends HttpServlet {
	
	private static final long serialVersionUID = -4563713556497313510L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getSession().invalidate();
		resp.sendRedirect(getProperty("casServerUrlPrefix") + "logout?service=" + getProperty("serverName"));
	}
	
	private String getProperty(String resource) {
	    InputStream inputStream = getClass().getResourceAsStream("/cas.properties");

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


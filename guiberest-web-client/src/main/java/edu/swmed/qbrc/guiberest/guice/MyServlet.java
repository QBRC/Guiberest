package edu.swmed.qbrc.guiberest.guice;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.resteasy.client.ClientRequestFactory;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Role;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.User;
import edu.swmed.qbrc.guiberest.shared.rest.GuiberestRestService;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.TableJSONContainer;
import edu.swmed.qbrc.guiberest.shared.rest.util.StringArray;

@Singleton
public class MyServlet extends HttpServlet {

	@Inject
	private ClientRequestFactory clientRequestFactory;
	
	@Inject
	GuiberestRestService guibRestService;

	private static final long serialVersionUID = -5697849610754976017L;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		
		StringBuilder out = new StringBuilder();
		out.append("<p>");
		
		StringArray userids = new StringArray();
		userids.getList().add("thomas");
		userids.getList().add("roger");
		TableJSONContainer<User> tblexp = guibRestService.getUsers(userids);
		List<User> users = tblexp.getData();
		if (users != null) {
			for (User user : users) {
				out.append("User: " + user.getId() + "<br/>");
			}
		}

		StringArray useridsforrole = new StringArray();
		useridsforrole.getList().add("thomas");
		List<Role> roles = guibRestService.getRoles(useridsforrole);
		for (Role role : roles) {
			out.append("Role: " + role.getRole() + "<br/>");
		}
		
		// Send to browser
		out.append("</p>");
		resp.getOutputStream().print(out.toString());
	}
	
}
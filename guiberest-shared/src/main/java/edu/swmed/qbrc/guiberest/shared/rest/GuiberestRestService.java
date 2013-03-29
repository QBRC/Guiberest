package edu.swmed.qbrc.guiberest.shared.rest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.jboss.resteasy.annotations.StringParameterUnmarshallerBinder;
import edu.swmed.qbrc.auth.cashmac.shared.util.Securable;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Role;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.User;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.TableJSONContainer;
import edu.swmed.qbrc.guiberest.shared.rest.util.CharArrayUnmarshaller;
import edu.swmed.qbrc.guiberest.shared.rest.util.StringArray;
import edu.swmed.qbrc.guiberest.shared.rest.util.UserIDArrayUnmarshaller;
import edu.swmed.qbrc.guiberest.shared.rest.util.IntegerArrayUnmarshaller;
import edu.swmed.qbrc.guiberest.shared.rest.util.StringArrayUnmarshaller;
 
@Path("/")
public interface GuiberestRestService {
 
	@Retention(RetentionPolicy.RUNTIME)
	@StringParameterUnmarshallerBinder(IntegerArrayUnmarshaller.class)
	public @interface IntegerArrayAnnot {
	}

	@Retention(RetentionPolicy.RUNTIME)
	@StringParameterUnmarshallerBinder(StringArrayUnmarshaller.class)
	public @interface StringArrayAnnot {
	}

	@Retention(RetentionPolicy.RUNTIME)
	@StringParameterUnmarshallerBinder(CharArrayUnmarshaller.class)
	public @interface CharArrayAnnot {
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@StringParameterUnmarshallerBinder(UserIDArrayUnmarshaller.class)
	public @interface UserIDArrayAnnot {
	}

	@Securable()
	@GET
	@Path("/user")
	@Produces("application/json")
	public TableJSONContainer<User> getUsers();

	@Securable()
	@GET
	@Path("/user/{param}")
	@Produces("application/json")
	public TableJSONContainer<User> getUsers(@PathParam("param") @StringArrayAnnot StringArray ids);

	@Securable()
	@RolesAllowed({"ProbeMapper-Reader"})
	@GET
	@Path("/role/{param}")
	@Produces("application/json")
	public List<Role> getRoles(@PathParam("param") @StringArrayAnnot StringArray userids);
	
}
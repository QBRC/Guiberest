package edu.swmed.qbrc.guiberest.shared.rest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.annotations.StringParameterUnmarshallerBinder;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Role;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.User;
import edu.swmed.qbrc.jacksonate.rest.datapackage.DataPackage;
import edu.swmed.qbrc.jacksonate.rest.jackson.TableJSONContainer;
import edu.swmed.qbrc.jacksonate.rest.util.CharArrayUnmarshaller;
import edu.swmed.qbrc.jacksonate.rest.util.StringArray;
import edu.swmed.qbrc.guiberest.shared.rest.util.UserIDArrayUnmarshaller;
import edu.swmed.qbrc.jacksonate.rest.util.IntegerArrayUnmarshaller;
import edu.swmed.qbrc.jacksonate.rest.util.StringArrayUnmarshaller;
 
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
	
	@RolesAllowed({})
	@GET
	@Path("/datapackage.json")
	@Produces("application/json")
	public DataPackage getDataPackage();

	@RolesAllowed({})
	@GET
	@Path("/user")
	@Produces("application/json")
	public TableJSONContainer<User> getUsers();

	@RolesAllowed({})
	@GET
	@Path("/user/{param}")
	@Produces("application/json")
	public TableJSONContainer<User> getUsers(@PathParam("param") @StringArrayAnnot StringArray ids);
	
	@RolesAllowed({"Guiberest-Writer"})
	@PUT
	@Path("/user/{param}")
	@Produces("application/json")
	public Response putUser(@PathParam("param") String userName, @QueryParam("password") String password, @QueryParam("secret") String secret);

	@RolesAllowed({"Guiberest-Writer"})
	@POST
	@Path("/user/{param}/delete")
	@Produces("application/json")
	public Response deleteUser(@PathParam("param") String userName);

	@RolesAllowed({"Guiberest-Reader", "Guiberest-Writer"})
	@GET
	@Path("/role/{param}")
	@Produces("application/json")
	public TableJSONContainer<Role> getRoles(@PathParam("param") @StringArrayAnnot StringArray userids);

	@RolesAllowed({"Guiberest-Writer"})
	@POST
	@Path("/role")
	@Produces("application/json")
	public Response putRole(@QueryParam("role_id") Integer roleId, @QueryParam("username") String userName, @QueryParam("role") String role);
	
	@RolesAllowed({"Guiberest-Writer"})
	@POST
	@Path("/role/{param}/delete")
	@Produces("application/json")
	public Response deleteRole(@PathParam("param") Integer roleId);

}
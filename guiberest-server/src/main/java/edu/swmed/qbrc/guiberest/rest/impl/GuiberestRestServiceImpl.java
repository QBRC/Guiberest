package edu.swmed.qbrc.guiberest.rest.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.jboss.resteasy.spi.BadRequestException;
import com.google.inject.Inject;
import edu.swmed.qbrc.guiberest.dao.guiberest.UserDao;
import edu.swmed.qbrc.guiberest.dao.guiberest.RoleDao;
import edu.swmed.qbrc.guiberest.rest.util.EntityIntrospector;
import edu.swmed.qbrc.guiberest.rest.util.EntityLoader;
import edu.swmed.qbrc.guiberest.shared.domain.Constraint;
import edu.swmed.qbrc.guiberest.shared.domain.Constraint.Operator;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Role;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.User;
import edu.swmed.qbrc.guiberest.shared.rest.GuiberestRestService;
import edu.swmed.qbrc.jacksonate.rest.datapackage.DataPackage;
import edu.swmed.qbrc.jacksonate.rest.jackson.RestBaseUrl;
import edu.swmed.qbrc.jacksonate.rest.jackson.TableJSONContainer;
import edu.swmed.qbrc.jacksonate.rest.util.StringArray;
 
public class GuiberestRestServiceImpl implements GuiberestRestService{
 
	@Inject
	UserDao userDao;
	@Inject
	RoleDao roleDao;
	@Inject
	RestBaseUrl restBaseUrl;

	public DataPackage getDataPackage() {
		return new DataPackage("Guiberest Test", "Guiberest Test Title", "Guiberest Test Description");
	}

	@SuppressWarnings("rawtypes")
	public TableJSONContainer<User> getUsers() {
		try {
			List<Constraint> constraints = new ArrayList<Constraint>();
			return new TableJSONContainer<User>(User.class, userDao.findAll(constraints));
		} catch(NoResultException e) {
			throw new BadRequestException("Invalid user query provided -- no such user/s.");
		} catch(PersistenceException e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	@SuppressWarnings("rawtypes")
	public TableJSONContainer<User> getUsers(@PathParam("param") @StringArrayAnnot StringArray ids) {
		try {
			List<Constraint> constraints = new ArrayList<Constraint>();
			constraints.add(new Constraint<StringArray>("id", Operator.EQUAL, ids, StringArray.class));
			return new TableJSONContainer<User>(User.class, userDao.findAll(constraints));
		} catch(NoResultException e) {
			throw new BadRequestException("Invalid user query provided -- no such user/s.");
		} catch(PersistenceException e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	public Response putUser(@PathParam("param") String userName, @QueryParam("password") String password, @QueryParam("secret") String secret) {
		User user = new User();
		user.setId(userName);
		user.setPassword(password);
		user.setSecret(secret);
		return putUser(user);
	}
	
	private Response putUser(User user) {

		// Set up object
		User userFound = new EntityLoader<User>(User.class, userDao).findOrNull(user.getId());

		// Create new object, if not found
		if (userFound == null)
			userFound = new User();

		// Introspect and populate fields of entity with values from object created with @Form
		userFound = new EntityIntrospector<User>(userFound, FormParam.class).populateWith(user);
		
		// Persist object
		User userNew = userDao.put(userFound);

		// Return 201 with location header
		String newUriString = restBaseUrl.getValue() + "/user";
		if (userNew.getId() != null) {
			newUriString += "/" + userNew.getId();
		}
		URI newUri = null;
		try {
			newUri = new URI(newUriString);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return Response.status(Status.CREATED).location(newUri).entity(userNew.getId()).build();
	
	}
	
	
	public Response deleteUser(@PathParam("param") String userName) {
		User user = userDao.find(userName);
		if (user != null)
			userDao.delete(user);
		return Response.status(Status.NO_CONTENT).build();
	}

	public TableJSONContainer<Role> getRoles(@PathParam("param") @StringArrayAnnot StringArray ids) {
		try {
			return new TableJSONContainer<Role>(Role.class, roleDao.getRolesForUser(ids.getList()));
		} catch(NoResultException e) {
			throw new BadRequestException("Invalid role query provided -- no such role/s.");
		} catch(PersistenceException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public Response putRole(@QueryParam("role_id") Integer roleId, @QueryParam("username") String userName, @QueryParam("role") String role) {
		Role newRole = new Role();
		newRole.setId(roleId);
		newRole.setUsername(userName);
		newRole.setRole(role);
		return putRole(newRole);
	}

	private Response putRole(Role role) {

		// Set up object
		Role roleFound = new EntityLoader<Role>(Role.class, roleDao).findOrNull(role.getId());

		// Create new object, if not found
		if (roleFound == null)
			roleFound = new Role();

		// Introspect and populate fields of entity with values from object created with @Form
		roleFound = new EntityIntrospector<Role>(roleFound, FormParam.class).populateWith(role);
		
		// Persist object
		Role roleNew = roleDao.put(roleFound);

		// Return 201 with location header
		String newUriString = restBaseUrl.getValue() + "/role";
		if (roleNew.getId() != null) {
			newUriString += "/" + roleNew.getId();
		}
		URI newUri = null;
		try {
			newUri = new URI(newUriString);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return Response.status(Status.CREATED).location(newUri).entity(roleNew.getId()).build();
	
	}
	
	public Response deleteRole(@PathParam("param") Integer roleId) {
		Role role = roleDao.find(roleId);
		if (role != null) {
			roleDao.delete(role);
		}
		return Response.status(Status.NO_CONTENT).build();
	}

}
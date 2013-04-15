package edu.swmed.qbrc.guiberest.rest.impl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.ws.rs.PathParam;
import org.jboss.resteasy.spi.BadRequestException;
import com.google.inject.Inject;
import edu.swmed.qbrc.guiberest.dao.guiberest.UserDao;
import edu.swmed.qbrc.guiberest.dao.guiberest.RoleDao;
import edu.swmed.qbrc.guiberest.shared.domain.Constraint;
import edu.swmed.qbrc.guiberest.shared.domain.Constraint.Operator;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Role;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.User;
import edu.swmed.qbrc.guiberest.shared.rest.GuiberestRestService;
import edu.swmed.qbrc.jacksonate.rest.datapackage.DataPackage;
import edu.swmed.qbrc.jacksonate.rest.jackson.TableJSONContainer;
import edu.swmed.qbrc.jacksonate.rest.util.StringArray;
 
public class GuiberestRestServiceImpl implements GuiberestRestService{
 
	@Inject
	UserDao userDao;
	@Inject
	RoleDao roleDao;

	public DataPackage getDataPackage() {
		return new DataPackage();
	}

	@SuppressWarnings("rawtypes")
	public TableJSONContainer<User> getUsers() {
		try {
			List<Constraint> constraints = new ArrayList<Constraint>();
			return new TableJSONContainer<User>(userDao.findAll(constraints));
		} catch(NoResultException e) {
			throw new BadRequestException("Invalid smoking status query provided -- no such smoking status/es.");
		}
	}
	@SuppressWarnings("rawtypes")
	public TableJSONContainer<User> getUsers(@PathParam("param") @StringArrayAnnot StringArray ids) {
		try {
			List<Constraint> constraints = new ArrayList<Constraint>();
			constraints.add(new Constraint<StringArray>("id", Operator.EQUAL, ids, StringArray.class));
			return new TableJSONContainer<User>(userDao.findAll(constraints));
		} catch(NoResultException e) {
			throw new BadRequestException("Invalid smoking status query provided -- no such smoking status/es.");
		}
	}
	public TableJSONContainer<Role> getRoles(@PathParam("param") @StringArrayAnnot StringArray ids) {
		try {
			return new TableJSONContainer<Role>(roleDao.getRolesForUser(ids.getList()));
		} catch(NoResultException e) {
			throw new BadRequestException("Invalid smoking status query provided -- no such smoking status/es.");
		}
	}

}
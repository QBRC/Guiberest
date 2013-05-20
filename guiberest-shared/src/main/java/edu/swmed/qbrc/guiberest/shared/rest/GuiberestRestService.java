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
import org.jboss.resteasy.spi.MethodNotAllowedException;
import edu.swmed.qbrc.auth.cashmac.shared.annotations.NoCasAuth;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.ACL;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Customer;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store;
import edu.swmed.qbrc.jacksonate.rest.datapackage.DataPackage;
import edu.swmed.qbrc.jacksonate.rest.jackson.TableJSONContainer;
import edu.swmed.qbrc.jacksonate.rest.util.CharArrayUnmarshaller;
import edu.swmed.qbrc.jacksonate.rest.util.IntegerArray;
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
	
	@RolesAllowed({}) // Allow everyone to see data package.
	@GET
	@Path("/datapackage.json")
	@Produces("application/json")
	public DataPackage getDataPackage();
	
	@RolesAllowed({ "Guiberest-Writer" })
	@GET
	@Path("/acl")
	@Produces("application/json")
	public TableJSONContainer<ACL> getAcls(@QueryParam("class") String className, @QueryParam("pk") String pkValue);

	@RolesAllowed({ "Guiberest-Writer" })
	@PUT
	@Path("/acl")
	@Produces("application/json")
	public Response addAcl(@QueryParam("access") String access, @QueryParam("class") String objectClass, @QueryParam("pk") String pk, @QueryParam("roles") StringArray roles);

	@RolesAllowed({ "Guiberest-Writer" })
	@POST
	@Path("/acl/delete")
	@Produces("application/json")
	public Response deleteAcl(@QueryParam("access") String access, @QueryParam("class") String objectClass, @QueryParam("pk") String pk, @QueryParam("roles") StringArray roles);
	
	@RolesAllowed({}) // Allow everyone to see store information.
	@POST
	@Path("/store")
	@Produces("application/json")
	public TableJSONContainer<Store> getStores();

	@RolesAllowed({}) // Allow everyone to see store information.
	@GET
	@Path("/store/{param}")
	@Produces("application/json")
	public TableJSONContainer<Store> getStores(@PathParam("param") @IntegerArrayAnnot IntegerArray ids);
	
	@RolesAllowed({"Guiberest-Writer"})
	@NoCasAuth
	@PUT
	@Path("/store/{param}")
	@Produces("application/json")
	public Response putStore(@PathParam("param") Integer storeId, @QueryParam("name") String name);

	@RolesAllowed({"Guiberest-Writer"})
	@NoCasAuth
	@POST
	@Path("/store/{param}/delete")
	@Produces("application/json")
	public Response deleteStore(@PathParam("param") Integer storeId);

	
	@RolesAllowed({"Guiberest-Writer", "Guiberest-Reader"})
	@GET
	@Path("/customer")
	@Produces("application/json")
	public TableJSONContainer<Customer> getCustomers();

	@RolesAllowed({"Guiberest-Writer", "Guiberest-Reader"})
	@GET
	@Path("/customer/{param}")
	@Produces("application/json")
	public TableJSONContainer<Customer> getCustomers(@PathParam("param") @IntegerArrayAnnot IntegerArray ids);
	
	@RolesAllowed({"Guiberest-Writer"})
	@NoCasAuth
	@PUT
	@Path("/customer/{param}")
	@Produces("application/json")
	public Response putCustomer(@PathParam("param") Integer customerId, @QueryParam("preferred_store_id") Integer preferredStoreId, @QueryParam("name") String name);

	@RolesAllowed({"Guiberest-Writer"})
	@NoCasAuth
	@POST
	@Path("/customer/{param}/delete")
	@Produces("application/json")
	public Response deleteCustomer(@PathParam("param") Integer customerId);


	@RolesAllowed({"Guiberest-Writer", "Guiberest-Reader"})
	@GET
	@Path("/sale/{param}/preauth")
	@Produces("application/json")
	public TableJSONContainer<Sale> getSalesWithPreAuth(@PathParam("param") @IntegerArrayAnnot IntegerArray ids);

	@RolesAllowed({"Guiberest-Writer", "Guiberest-Reader"})
	@GET
	@Path("/sale/{param}")
	@Produces("application/json")
	public TableJSONContainer<Sale> getSales(@PathParam("param") @IntegerArrayAnnot IntegerArray ids);

	@RolesAllowed({"Guiberest-Writer", "Guiberest-Reader"})
	@GET
	@Path("/sale")
	@Produces("application/json")
	public TableJSONContainer<Sale> getSalesByCustomer(@QueryParam("customer_id") Integer customerId);
	
	@RolesAllowed({"Guiberest-Writer"})
	@NoCasAuth
	@PUT
	@Path("/sale/{param}")
	@Produces("application/json")
	public Response putSale(@PathParam("param") Integer saleId, @QueryParam("storeId") Integer storeId, @QueryParam("customerId") Integer customerId, @QueryParam("total") Float total) throws MethodNotAllowedException;

	@RolesAllowed({"Guiberest-Writer"})
	@NoCasAuth
	@POST
	@Path("/sale/{param}/delete")
	@Produces("application/json")
	public Response deleteSale(@PathParam("param") Integer saleId);

}
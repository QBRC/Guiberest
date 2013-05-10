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
import edu.swmed.qbrc.guiberest.dao.guiberest.CustomerDao;
import edu.swmed.qbrc.guiberest.dao.guiberest.SaleDao;
import edu.swmed.qbrc.guiberest.dao.guiberest.StoreDao;
import edu.swmed.qbrc.guiberest.rest.util.EntityIntrospector;
import edu.swmed.qbrc.guiberest.rest.util.EntityLoader;
import edu.swmed.qbrc.guiberest.shared.domain.Constraint;
import edu.swmed.qbrc.guiberest.shared.domain.Constraint.Operator;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Customer;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store;
import edu.swmed.qbrc.guiberest.shared.rest.GuiberestRestService;
import edu.swmed.qbrc.jacksonate.rest.datapackage.DataPackage;
import edu.swmed.qbrc.jacksonate.rest.jackson.RestBaseUrl;
import edu.swmed.qbrc.jacksonate.rest.jackson.TableJSONContainer;
import edu.swmed.qbrc.jacksonate.rest.util.IntegerArray;
 
public class GuiberestRestServiceImpl implements GuiberestRestService{
 
	@Inject
	StoreDao storeDao;
	@Inject
	CustomerDao customerDao;
	@Inject
	SaleDao saleDao;
	@Inject
	RestBaseUrl restBaseUrl;

	public DataPackage getDataPackage() {
		return new DataPackage("Guiberest Test", "Guiberest Test Title", "Guiberest Test Description");
	}

	@SuppressWarnings("rawtypes")
	public TableJSONContainer<Store> getStores() {
		try {
			List<Constraint> constraints = new ArrayList<Constraint>();
			return new TableJSONContainer<Store>(Store.class, storeDao.findAll(constraints));
		} catch(NoResultException e) {
			throw new BadRequestException("Invalid store query provided -- no such store/s.");
		} catch(PersistenceException e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	@SuppressWarnings("rawtypes")
	public TableJSONContainer<Store> getStores(@PathParam("param") @IntegerArrayAnnot IntegerArray ids) {
		try {
			List<Constraint> constraints = new ArrayList<Constraint>();
			constraints.add(new Constraint<IntegerArray>("id", Operator.EQUAL, ids, IntegerArray.class));
			return new TableJSONContainer<Store>(Store.class, storeDao.findAll(constraints));
		} catch(NoResultException e) {
			throw new BadRequestException("Invalid store query provided -- no such store/s.");
		} catch(PersistenceException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public Response putStore(@PathParam("param") Integer storeId, @QueryParam("name") String name) {
		Store store = new Store();
		store.setId(storeId);
		store.setName(name);
		return putStore(store);
	}
	
	private Response putStore(Store store) {

		// Set up object
		Store storeFound = new EntityLoader<Store>(Store.class, storeDao).findOrNull(store.getId());

		// Create new object, if not found
		if (storeFound == null)
			storeFound = new Store();

		// Introspect and populate fields of entity with values from object created with @Form
		storeFound = new EntityIntrospector<Store>(storeFound, FormParam.class).populateWith(store);
		
		// Persist object
		Store storeNew = storeDao.put(storeFound);

		// Return 201 with location header
		String newUriString = restBaseUrl.getValue() + "/store";
		if (storeNew.getId() != null) {
			newUriString += "/" + storeNew.getId();
		}
		URI newUri = null;
		try {
			newUri = new URI(newUriString);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return Response.status(Status.CREATED).location(newUri).entity(storeNew.getId()).build();
	
	}
	

	public Response deleteStore(@PathParam("param") Integer storeId) {
		Store store = storeDao.find(storeId);
		if (store != null)
			storeDao.delete(store);
		return Response.status(Status.NO_CONTENT).build();
	}

	@SuppressWarnings("rawtypes")
	public TableJSONContainer<Customer> getCustomers() {
		try {
			List<Constraint> constraints = new ArrayList<Constraint>();
			return new TableJSONContainer<Customer>(Customer.class, customerDao.findAll(constraints));
		} catch(NoResultException e) {
			throw new BadRequestException("Invalid customer query provided -- no such customer/s.");
		} catch(PersistenceException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	@SuppressWarnings("rawtypes")
	public TableJSONContainer<Customer> getCustomers(@PathParam("param") @IntegerArrayAnnot IntegerArray ids) {
		try {
			List<Constraint> constraints = new ArrayList<Constraint>();
			constraints.add(new Constraint<IntegerArray>("id", Operator.EQUAL, ids, IntegerArray.class));
			return new TableJSONContainer<Customer>(Customer.class, customerDao.findAll(constraints));
		} catch(NoResultException e) {
			throw new BadRequestException("Invalid customer query provided -- no such customer/s.");
		} catch(PersistenceException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public Response putCustomer(@PathParam("param") Integer customerId,	@QueryParam("preferred_store_id") Integer preferredStoreId, @QueryParam("name") String name) {
		Customer customer = new Customer();
		customer.setId(customerId);
		customer.setPreferredStoreId(preferredStoreId);
		customer.setName(name);
		return putCustomer(customer);
	}

	private Response putCustomer(Customer customer) {

		// Set up object
		Customer customerFound = new EntityLoader<Customer>(Customer.class, customerDao).findOrNull(customer.getId());

		// Create new object, if not found
		if (customerFound == null)
			customerFound = new Customer();

		// Introspect and populate fields of entity with values from object created with @Form
		customerFound = new EntityIntrospector<Customer>(customerFound, FormParam.class).populateWith(customer);
		
		// Persist object
		Customer customerNew = customerDao.put(customerFound);

		// Return 201 with location header
		String newUriString = restBaseUrl.getValue() + "/customer";
		if (customerNew.getId() != null) {
			newUriString += "/" + customerNew.getId();
		}
		URI newUri = null;
		try {
			newUri = new URI(newUriString);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return Response.status(Status.CREATED).location(newUri).entity(customerNew.getId()).build();
	
	}
	
	public Response deleteCustomer(@PathParam("param") Integer customerId) {
		Customer customer = customerDao.find(customerId);
		if (customer != null) {
			customerDao.delete(customer);
		}
		return Response.status(Status.NO_CONTENT).build();
	}

	@SuppressWarnings("rawtypes")
	public TableJSONContainer<Sale> getSales(@PathParam("param") @IntegerArrayAnnot IntegerArray ids) {
		try {
			List<Constraint> constraints = new ArrayList<Constraint>();
			return new TableJSONContainer<Sale>(Sale.class, saleDao.findAll(constraints));
		} catch(NoResultException e) {
			throw new BadRequestException("Invalid sale query provided -- no such sales/s.");
		} catch(PersistenceException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public TableJSONContainer<Sale> getSalesByCustomer(@QueryParam("customer_id") Integer customerId) {
		try {
			return new TableJSONContainer<Sale>(Sale.class, saleDao.findByCustomer(customerId));
		} catch(NoResultException e) {
			throw new BadRequestException("Invalid sale query provided -- no such sales/s.");
		} catch(PersistenceException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public Response putSale(@PathParam("param") Integer saleId,	@QueryParam("storeId") Integer storeId, @QueryParam("customerId") Integer customerId, @QueryParam("total") Float total) {
		Sale sale = new Sale();
		sale.setId(saleId);
		sale.setStoreId(storeId);
		sale.setCustomerId(customerId);
		sale.setTotal(total);
		return putSale(sale);
	}
	
	private Response putSale(Sale sale) {

		// Set up object
		Sale saleFound = new EntityLoader<Sale>(Sale.class, saleDao).findOrNull(sale.getId());

		// Create new object, if not found
		if (saleFound == null)
			saleFound = new Sale();

		// Introspect and populate fields of entity with values from object created with @Form
		saleFound = new EntityIntrospector<Sale>(saleFound, FormParam.class).populateWith(sale);
		
		// Persist object
		Sale saleNew = saleDao.put(saleFound);

		// Return 201 with location header
		String newUriString = restBaseUrl.getValue() + "/sale";
		if (saleNew.getId() != null) {
			newUriString += "/" + saleNew.getId();
		}
		URI newUri = null;
		try {
			newUri = new URI(newUriString);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return Response.status(Status.CREATED).location(newUri).entity(saleNew.getId()).build();
	
	}

	public Response deleteSale(@PathParam("param") Integer saleId) {
		Sale sale = saleDao.find(saleId);
		if (sale != null)
			saleDao.delete(sale);
		return Response.status(Status.NO_CONTENT).build();
	}

		
}
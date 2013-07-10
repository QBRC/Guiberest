package edu.swmed.qbrc.guiberest.webapp.guice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;
import org.reflections.Store;
import com.google.inject.Inject;
import cucumber.table.DataTable;
import edu.swmed.qbrc.auth.cashmac.shared.util.TestableEntity;
import edu.swmed.qbrc.auth.cashmac.shared.util.TestableEntityString;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.ACL;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Customer;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Role;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.RoleUser;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.User;
import edu.swmed.qbrc.guiberest.shared.rest.GuiberestRestService;
import edu.swmed.qbrc.jacksonate.rest.util.IntegerArray;
import edu.swmed.qbrc.jacksonate.rest.util.StringArray;
import gherkin.formatter.model.DataTableRow;

public class TestableEntityTester {

	Map<String, List<TestableEntity<?>>> entityLists = new HashMap<String, List<TestableEntity<?>>>();
	Map<String, List<Integer>> insertLists = new HashMap<String, List<Integer>>();

	private final ClientIdentification clientIdentification;
	private final GuiberestRestService guiberestRestService;
	
	private Boolean threwNoAclException = false;
	private Boolean threwOtherException = false;
	
	@Inject
	public TestableEntityTester(final GuiberestRestService guiberestRestService, final ClientIdentification clientIdentification) {
		this.guiberestRestService = guiberestRestService;
		this.clientIdentification = clientIdentification;
		populateEntityLists();
	}
	
	public Boolean checkForLessThanXresults(String className, Integer limit) {
		return entityLists.get(matchObjectClass(className).getName()).size() < limit;
	}
	public Boolean checkForLessThanOrEqualXresults(String className, Integer limit) {
		return entityLists.get(matchObjectClass(className).getName()).size() <= limit;
	}
	public Boolean checkForGreaterThanXresults(String className, Integer limit) {
		return entityLists.get(matchObjectClass(className).getName()).size() > limit;
	}
	public Boolean checkForGreaterThanOrEqualXresults(String className, Integer limit) {
		return entityLists.get(matchObjectClass(className).getName()).size() >= limit;
	}
	public Boolean checkForEqualsXresults(String className, Integer limit) {
		return entityLists.get(matchObjectClass(className).getName()).size() == limit;
	}
	
	
	public Boolean checkTheFollowingXResults(String className, DataTable data) {
		Boolean retval = true;
		
		for (DataTableRow row : data.getGherkinRows()) {
			Boolean bFound = false;
			for (TestableEntity<?> entity : entityLists.get(matchObjectClass(className).getName())) {
				if (matchLists(entity.testOutput(), row)) {
					bFound = true;
					break;
				}
			}
			
			// Return false if any item is not found.
			if (!bFound) {
				retval = false;
				System.out.println("Couldn't find " + className + " with:\n--------------------------------------------------");
				for (String cell : row.getCells()) {
					System.out.println("             " + cell);
				}
				break;
			}
		}
		
		return retval;
	}
	
	public Boolean iSeeNoAclException() {
		return this.threwNoAclException;
	}
	
	public Boolean iSeeAnotherException() {
		return this.threwOtherException;
	}	
	
	public void requestXforTheLastInsertedXIds(String className, String insertClass, String userName) {
		switchIdentity(userName);
		reset();
		Class<?> myClass = matchObjectClass(className);
		Class<?> myInsertClass = matchObjectClass(insertClass);
		
		IntegerArray intList = new IntegerArray();
		for (Integer item : this.insertLists.get(myInsertClass.getName())) {
			intList.getList().add(item);
		}
		
		this.getWithIntArray(myClass, intList);
	}
	
	public void requestXforThisId(String className, Integer id, String userName) {
		switchIdentity(userName);
		reset();
		Class<?> myClass = matchObjectClass(className);
		
		/* Get a single integer for methods that expect one */
		this.getWithSingleInt(myClass, id);
	}
	
	
	public void requestXforThisIdAndClass(String className, Integer id, String itemClass, String userName) {
		switchIdentity(userName);
		reset();
		Class<?> myClass = matchObjectClass(className);
		
		/* Get a single integer for methods that expect one */
		this.getWithSingleIntPlusClass(myClass, id, itemClass);
	}
	
	public void requestXforTheFollowingIds(String className, DataTable ids, String userName) {
		switchIdentity(userName);
		reset();
		Class<?> myClass = matchObjectClass(className);
		
		IntegerArray intList;

		/* Collect IntegerArrays for requests that include a data table of ids and call a method that expects an IntegerArray */ 
		if (ids != null && ids.getGherkinRows().size() > 0 && (myClass.equals(Store.class) || myClass.equals(Customer.class) || myClass.equals(Sale.class))) {
			intList = collectIntIds(ids);
			this.getWithIntArray(myClass, intList);
		}

		/* Requests that include a single item for a single integer parameter */ /*
		else if (ids != null && ids.getGherkinRows().size() == 1 && (myClass.equals(Authority.class) || myClass.equals(Platform.class)) ) {
			this.getWithSingleInt(myClass, Integer.parseInt(ids.getGherkinRows().get(0).getCells().get(0).trim()), alt);
		}*/

		/* For methods that don't take any input values */
		else {
			this.getWithNoParams(myClass);
		}
	}
	
	public void requestSalesWithPreAuth(DataTable ids, String userName) {
		switchIdentity(userName);
		reset();
		
		final IntegerArray idArray = collectIntIds(ids);
		this.entityLists.get(Sale.class.getName()).clear();
		this.entityLists.get(Sale.class.getName()).addAll(guiberestRestService.getSalesWithPreAuth(idArray).getData());		
	}
	
	public void putXfortheFollowingX(String className, DataTable inserts, String userName) {
		switchIdentity(userName);
		reset();
		Class<?> myClass = matchObjectClass(className);

		// Clone insert cache, in case we need to access cached values
		List<Integer> oldInserts = new ArrayList<Integer>();
		oldInserts.addAll(insertLists.get(myClass.getName()));

		// Clear insert cache
		List<Integer> cache = insertLists.get(myClass.getName());
		cache.clear();
		
		// Track count in case we need to insert/update based on a previous insert list
		//Integer oldInsertsIndex = 0;

		for (DataTableRow row : inserts.getGherkinRows()) {
			Response response = null;
			if (myClass.equals(Customer.class)) {
	    		response = guiberestRestService.putCustomer(
	    				Integer.parseInt(row.getCells().get(0).trim()),
	    				Integer.parseInt(row.getCells().get(1).trim()),
	    				row.getCells().get(2).trim()
	    		);
			}
			
			else if (myClass.equals(Store.class)) {
	    		response = guiberestRestService.putStore(
	    				Integer.parseInt(row.getCells().get(0).trim()),
	    				row.getCells().get(1).trim()
	    		);
			}

			else if (myClass.equals(Sale.class)) {
	    		response = guiberestRestService.putSale(
	    				Integer.parseInt(row.getCells().get(0).trim()),
	    				Integer.parseInt(row.getCells().get(1).trim()),
	    				Integer.parseInt(row.getCells().get(2).trim()),
	    				Float.parseFloat(row.getCells().get(3).trim())
	    		);
			}
			
			else if (myClass.equals(ACL.class)) {
				StringArray roles = new StringArray(row.getCells().get(0).trim());
				response = guiberestRestService.addAcl(
						row.getCells().get(1).trim(),
						row.getCells().get(2).trim(),
						row.getCells().get(3).trim(),
						roles
				);
			}

			if (response != null) {
				if (response.getStatus() == 200) {
		    		Integer id = response.readEntity(Integer.class);
		    		System.out.println("Inserted " + className + " with ID: " + id);
		    		cache.add(id);
				} else if (response.getStatus() == 405) {
					this.threwNoAclException = true;
		    		response.close();
					break;
				}
	    		response.close();
			}

		}
	}
	
	public void deleteXfortheFollowingX(String className, DataTable inserts, String userName) {
		switchIdentity(userName);
		reset();
		Class<?> myClass = matchObjectClass(className);

		for (DataTableRow row : inserts.getGherkinRows()) {
			Response response = null;
			if (myClass.equals(Customer.class)) {
	    		response = guiberestRestService.deleteCustomer(
	    				Integer.parseInt(row.getCells().get(0).trim())
	    		);
			}
			
			else if (myClass.equals(Store.class)) {
	    		response = guiberestRestService.deleteStore(
	    				Integer.parseInt(row.getCells().get(0).trim())
	    		);
			}

			else if (myClass.equals(Sale.class)) {
	    		response = guiberestRestService.deleteSale(
	    				Integer.parseInt(row.getCells().get(0).trim())
	    		);
			}

			else if (myClass.equals(ACL.class)) {
				StringArray roles = new StringArray(row.getCells().get(0).trim());
				response = guiberestRestService.deleteAcl(
						row.getCells().get(1).trim(),
						row.getCells().get(2).trim(),
						row.getCells().get(3).trim(),
						roles
				);
			}

			if (response != null) {
				if (response.getStatus() == 405 || response.getStatus() == 403) {
					this.threwNoAclException = true;
					response.close();
					break;
				}
				response.close();
			}

		}
	}

	
	public void deleteXfortheLastInsertedIds(String className, String userName) {
		switchIdentity(userName);
		reset();
		Class<?> myClass = matchObjectClass(className);

		for (Integer item : this.insertLists.get(myClass.getName())) {
			Response response = null;
			if (myClass.equals(Customer.class)) {
	    		response = guiberestRestService.deleteCustomer(item);
			}
			
			else if (myClass.equals(Store.class)) {
	    		response = guiberestRestService.deleteStore(item);
			}

			else if (myClass.equals(Sale.class)) {
	    		response = guiberestRestService.deleteSale(item);
			}

			if (response != null) {
				if (response.getStatus() == 405 || response.getStatus() == 403) {
					this.threwNoAclException = true;
					response.close();
					break;
				}
				response.close();
			}

		}
	}

	/**
	 * Rest NoAclException flags
	 */
	private void reset() {
		this.threwNoAclException = false;
		this.threwOtherException = false;
	}
	
	private void populateEntityLists() {
		entityLists.put(ACL.class.getName(), new ArrayList<TestableEntity<?>>());
		entityLists.put(Customer.class.getName(), new ArrayList<TestableEntity<?>>());
		entityLists.put(Role.class.getName(), new ArrayList<TestableEntity<?>>());
		entityLists.put(RoleUser.class.getName(), new ArrayList<TestableEntity<?>>());
		entityLists.put(Sale.class.getName(), new ArrayList<TestableEntity<?>>());
		entityLists.put(Store.class.getName(), new ArrayList<TestableEntity<?>>());
		entityLists.put(User.class.getName(), new ArrayList<TestableEntity<?>>());
		insertLists.put(ACL.class.getName(), new ArrayList<Integer>());
		insertLists.put(Customer.class.getName(), new ArrayList<Integer>());
		insertLists.put(Role.class.getName(), new ArrayList<Integer>());
		insertLists.put(RoleUser.class.getName(), new ArrayList<Integer>());
		insertLists.put(Sale.class.getName(), new ArrayList<Integer>());
		insertLists.put(Store.class.getName(), new ArrayList<Integer>());
		insertLists.put(User.class.getName(), new ArrayList<Integer>());
	}

	private Boolean matchLists(List<TestableEntityString> entity, DataTableRow row) {

		// Make sure columns in test match entity value list size.
		if (entity.size() != row.getCells().size()) {
			return false;
		}

		// Check all values, returning false if any don't match.
		Integer i = 0;
		for (TestableEntityString entityValue : entity) {
			String testValue = row.getCells().get(i++).trim();
			//System.out.println("Comparing " + entityValue.getStringOrNull() + " with " + testValue);
			if (! (testValue.equals(entityValue.getString()) || testValue.equals("<any>") )) {
				return false;
			}
		}
			
		return true;
	}

	private void getWithIntArray(Class<?> myClass, IntegerArray intList) {
		entityLists.get(myClass.getName()).clear();

		try {
		
			if (myClass.equals(Store.class)) {
				entityLists.get(myClass.getName()).addAll(guiberestRestService.getStores(intList).getData());
			}
			
			else if (myClass.equals(Customer.class)) {
				entityLists.get(myClass.getName()).addAll(guiberestRestService.getCustomers(intList).getData());
			}
			
			else if (myClass.equals(Sale.class)) {
				entityLists.get(myClass.getName()).addAll(guiberestRestService.getSales(intList).getData());
			}
			
    	} catch (NotAllowedException e) {
    		if (e.getResponse().getStatus() == 405 || e.getResponse().getStatus() == 403)
    			this.threwNoAclException = true;
    		else
    			throw e;
    		e.getResponse().close();
		} catch (BadRequestException e) {
			this.threwOtherException = true;
			e.getResponse().close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
	}
	
	private void getWithSingleInt(Class<?> myClass, Integer singleInt) {
		entityLists.get(myClass.getName()).clear();

		try {
			if (myClass.equals(Sale.class)) {
				entityLists.get(myClass.getName()).addAll(guiberestRestService.getSalesByCustomer(singleInt).getData());
			}
    	} catch (NotAllowedException e) {
    		if (e.getResponse().getStatus() == 405 || e.getResponse().getStatus() == 403)
    			this.threwNoAclException = true;
    		else
    			throw e;
    		e.getResponse().close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
	}

		
	private void getWithSingleIntPlusClass(Class<?> myClass, Integer singleInt, String itemClass) {
		entityLists.get(myClass.getName()).clear();

		try {
			if (myClass.equals(ACL.class)) {
				entityLists.get(myClass.getName()).addAll(guiberestRestService.getAcls(matchObjectClass(itemClass).getName(), singleInt.toString()).getData());
			}
    	} catch (NotAllowedException e) {
    		if (e.getResponse().getStatus() == 405 || e.getResponse().getStatus() == 403)
    			this.threwNoAclException = true;
    		else
    			throw e;
    		e.getResponse().close();
		} catch (BadRequestException e) {
			this.threwOtherException = true;
			e.getResponse().close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
	}
	
	private void getWithNoParams(Class<?> myClass) {
		entityLists.get(myClass.getName()).clear();

		try {
			if (myClass.equals(Store.class)) {
				entityLists.get(myClass.getName()).addAll(guiberestRestService.getStores().getData());
			}
			
			if (myClass.equals(Customer.class)) {
				entityLists.get(myClass.getName()).addAll(guiberestRestService.getCustomers().getData());
			}
			
    	} catch (NotAllowedException e) {
    		if (e.getResponse().getStatus() == 405 || e.getResponse().getStatus() == 403)
    			this.threwNoAclException = true;
    		else
    			throw e;
    		e.getResponse().close();
		} catch (BadRequestException e) {
			this.threwOtherException = true;
			e.getResponse().close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
	}
	
	private IntegerArray collectIntIds(DataTable ids) {
		IntegerArray list = new IntegerArray();
		for (DataTableRow row : ids.getGherkinRows()) {
			list.getList().add((new TestableEntityString(row.getCells().get(0))).getInt());
		}
		return list;
	}

	@SuppressWarnings("unused")
	private StringArray collectStringIds(DataTable ids) {
		StringArray list = new StringArray();
		for (DataTableRow row : ids.getGherkinRows()) {
			list.getList().add((new TestableEntityString(row.getCells().get(0))).getStringOrNull());
		}
		return list;
	}
	
	public void switchIdentity(String userName) {
		userName = userName.trim().toLowerCase();
		if (userName.equals("guest"))
			clientIdentification.setGuest();
		else if (userName.equals("irsauditer"))
			clientIdentification.setIrsAuditer();
		else if (userName.equals("roger"))
			clientIdentification.setRoger();
		else if (userName.equals("sean"))
			clientIdentification.setSean();
		else if (userName.equals("thomas"))
			clientIdentification.setThomas();
		else if (userName.equals("cook")) {
			clientIdentification.setCook();
		}
		else
			clientIdentification.setThomas();
	}
	
	private Class<?> matchObjectClass(String shortName) {
		shortName = shortName.trim().toLowerCase();
		if (shortName.equals("acl"))
			return ACL.class;
		else if (shortName.equals("customer"))
			return Customer.class;
		else if (shortName.equals("role"))
			return Role.class;
		else if (shortName.equals("roleuser"))
			return RoleUser.class;
		else if (shortName.equals("sale"))
			return Sale.class;
		else if (shortName.equals("store"))
			return Store.class;
		else if (shortName.equals("user"))
			return User.class;
		else
			return null;
	}
	
}

package edu.swmed.qbrc.guiberest.stepdefs;

import javax.ws.rs.core.Response;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ClientResponseFailure;
import org.junit.Assert;
import com.google.inject.Inject;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;
import cucumber.table.DataTable;
import edu.swmed.qbrc.guiberest.guice.ClientIdentification;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.ACL;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale;
import edu.swmed.qbrc.guiberest.shared.rest.GuiberestRestService;
import edu.swmed.qbrc.jacksonate.rest.jackson.TableJSONContainer;
import edu.swmed.qbrc.jacksonate.rest.util.IntegerArray;
import gherkin.formatter.model.DataTableRow;

public class SaleStepdefs {
    
	final GuiberestRestService guiberestRestService;
	final ClientIdentification clientIdentification;
	
	final IntegerArray insertIdCache = new IntegerArray();
	TableJSONContainer<Sale> resultsCache;
	
    @Inject
    public SaleStepdefs(final GuiberestRestService guiberestRestService, final ClientIdentification clientIdentification) {
    	this.guiberestRestService = guiberestRestService;
    	this.clientIdentification = clientIdentification;
    }
	
    @When("^I request sales for the following sale ids:$")
    public void I_request_sales_with_the_following_sale_ids(DataTable ids) throws Throwable {
    	clientIdentification.setThomas();
    	final IntegerArray idArray = new IntegerArray();
    	for (DataTableRow row : ids.getGherkinRows()) {
    		idArray.getList().add(Integer.parseInt(row.getCells().get(0)));
    	}
    	resultsCache = guiberestRestService.getSales(idArray);
    }

    @When("^I request sales for the (\\d+) customer$")
    public void I_request_sales_with_the_following_customer(Integer customerId) throws Throwable {
    	clientIdentification.setThomas();
    	resultsCache = guiberestRestService.getSalesByCustomer(customerId);
    }

    @Then("^I see the following full sale results:$")
    public void I_see_the_following_full_sale_results(DataTable results) throws Throwable {
    	clientIdentification.setThomas();
    	for (DataTableRow row : results.getGherkinRows()) {
    		Boolean bFound = false;
    		for (Sale sale : resultsCache.getData()) {
    			//System.out.println("Looking at: " + sale.getCustomerId() + " | " + sale.getId() + " | " +  sale.getStoreId() + " | " + sale.getTotal());
    			if (checkSale(row, sale)) {
    				bFound = true;
    				continue;
    			}
    		}
    		if (!bFound) {
    			System.out.println("Not Found: " + row.getCells().get(0) + " | " + row.getCells().get(1) + " | " + row.getCells().get(2) + " | " + row.getCells().get(3));
    		}
    		Assert.assertTrue(bFound);
    	}
    }
    
    private Boolean checkSale(DataTableRow row, Sale sale) {
    	return	sale.getCustomerId().toString().trim().equals(row.getCells().get(0)) &&
				sale.getId().toString().trim().equals(row.getCells().get(1)) &&
				sale.getStoreId().toString().trim().equals(row.getCells().get(2)) &&
				sale.getTotal().toString().trim().equals(row.getCells().get(3));
    }
    
    @SuppressWarnings("unchecked")
    @When("^I insert the following sales:$")
    public void I_insert_the_following_sales(DataTable inserts) throws Throwable {
		System.out.println("Will now insert sales...");
    	clientIdentification.setThomas();
    	insertIdCache.getList().clear();
    	for (DataTableRow row : inserts.getGherkinRows()) {
    		ClientResponse<String> response = (ClientResponse<String>)guiberestRestService.putSale(
    				Integer.parseInt(row.getCells().get(1).trim()),
    				Integer.parseInt(row.getCells().get(2).trim()),
    				Integer.parseInt(row.getCells().get(0).trim()),
    				Float.parseFloat(row.getCells().get(3).trim())
    		);
    		Integer id = response.getEntity(Integer.class);
    		System.out.println("Inserted Sale with ID: " + id);
    		insertIdCache.getList().add(id);
    	}
		System.out.println("Done inserting sales...");
    }

    @SuppressWarnings("unchecked")
    @When("^I update the following sales:$")
    public void I_update_the_following_sales(DataTable inserts) throws Throwable {
    	clientIdentification.setThomas();
    	insertIdCache.getList().clear();
    	for (DataTableRow row : inserts.getGherkinRows()) {
    		ClientResponse<String> response = (ClientResponse<String>)guiberestRestService.putSale(
    				Integer.parseInt(row.getCells().get(1).trim()),
    				Integer.parseInt(row.getCells().get(2).trim()),
    				Integer.parseInt(row.getCells().get(0).trim()),
    				Float.parseFloat(row.getCells().get(3).trim())
    		);
    		Integer id = response.getEntity(Integer.class);
    		System.out.println("Updated Sale with ID: " + id);
    		insertIdCache.getList().add(id);
    	}
    }

    @SuppressWarnings("unused")
	@When("^I delete the following sales:$")
    public void I_delete_the_following_sales(DataTable deletes) throws Throwable {
    	clientIdentification.setThomas();
    	insertIdCache.getList().clear();
    	for (DataTableRow row : deletes.getGherkinRows()) {
    		Response response = guiberestRestService.deleteSale(
    				Integer.parseInt(row.getCells().get(0).trim())
    		);
    	}
    }

    @Then("^I see no more than (\\d+) sale results$")
    public void I_see_no_more_than_sale_results(int results) throws Throwable {
    	System.out.println("Row Count: " + resultsCache.getData().size());
        Assert.assertTrue(resultsCache.getData().size() <= results);
    }
    
    @When("^I request sales for the following sale ids I receive a NoAclException:$")
    public void I_request_sales_for_the_following_sale_ids_I_receive_a_NoAclException(DataTable ids) throws Throwable {
    	clientIdentification.setThomas();
    	Boolean error = false;
    	final IntegerArray idArray = new IntegerArray();
    	for (DataTableRow row : ids.getGherkinRows()) {
    		idArray.getList().add(Integer.parseInt(row.getCells().get(0)));
    	}
    	try {
    		resultsCache = guiberestRestService.getSales(idArray);
    	} catch (ClientResponseFailure e) {
    		error = true;
    	}
    	if (! error && resultsCache != null) {
    		System.out.println("Found sales in error:");
    	}
    	Assert.assertTrue(error);
    }

	@When("^I update the following sales I receive a NoAclException:$")
    public void I_update_the_following_sales_I_receive_a_NoAclException(DataTable inserts) throws Throwable {
    	clientIdentification.setThomas();
		insertIdCache.getList().clear();
    	for (DataTableRow row : inserts.getGherkinRows()) {
    		ClientResponse<?> response = (ClientResponse<?>)guiberestRestService.putSale(
    				Integer.parseInt(row.getCells().get(1).trim()),
    				Integer.parseInt(row.getCells().get(2).trim()),
    				Integer.parseInt(row.getCells().get(0).trim()),
    				Float.parseFloat(row.getCells().get(3).trim())
    		);
	    	Assert.assertTrue(response.getStatus() == 401); // Forbidden
	    	response.releaseConnection();
    	}
    }

	@When("^I request sales with preauthorization for the following sale ids:$")
	public void I_request_sales_with_preauthorization_for_the_following_sale_ids(DataTable ids) throws Throwable {
    	clientIdentification.setThomas();
    	final IntegerArray idArray = new IntegerArray();
    	for (DataTableRow row : ids.getGherkinRows()) {
    		idArray.getList().add(Integer.parseInt(row.getCells().get(0)));
    	}
   		resultsCache = guiberestRestService.getSalesWithPreAuth(idArray);
	}

	@Then("^if I check ACLs for sale (\\d+) I see the following ACLs:$")
	public void if_I_check_ACLs_for_sale_I_see_the_following_ACLs(Integer sale, DataTable acls) throws Throwable {
    	clientIdentification.setThomas();
		TableJSONContainer<ACL> aclstable = guiberestRestService.getAcls(Sale.class.getName(), sale.toString());
    	for (DataTableRow row : acls.getGherkinRows()) {
    		Boolean bFound = false;
    		for (ACL acl : aclstable.getData()) {
    			if (checkAcl(row, acl)) {
    				bFound = true;
    				continue;
    			}
    		}
    		if (!bFound) {
    			System.out.println("Not Found: " + row.getCells().get(0) + " | " + row.getCells().get(1) + " | " + row.getCells().get(2));
    		}
    		Assert.assertTrue(bFound);
    	}
	}
    private Boolean checkAcl(DataTableRow row, ACL acl) {
    	return	acl.getAccess().equals(row.getCells().get(0)) &&
    			((acl.getUsername() != null && acl.getUsername().equals(row.getCells().get(1))) || (acl.getUsername() == null && row.getCells().get(1).equals("NULL"))) &&
    			((acl.getRoleId() != null && acl.getRoleId().toString().equals(row.getCells().get(2))) || (acl.getRoleId() == null && row.getCells().get(2).equals("NULL")));
    }
    
	@Then("^if I check ACLs for sale (\\d+) I see no more than (\\d+) ACL results.$")
	public void if_I_check_ACLs_for_sale_I_see_no_more_than_ACL_results(Integer sale, Integer results) throws Throwable {
    	clientIdentification.setThomas();
		TableJSONContainer<ACL> aclstable = guiberestRestService.getAcls(Sale.class.getName(), sale.toString());
		Assert.assertTrue(aclstable.getData().size() <= results);
	}	
	
	@When("^I request sales as user irsauditer for the following sale ids:$")
	public void I_request_sales_as_user_irsauditer_for_the_following_sale_ids(DataTable ids) throws Throwable {
    	clientIdentification.setIrsAuditer();
    	final IntegerArray idArray = new IntegerArray();
    	for (DataTableRow row : ids.getGherkinRows()) {
    		idArray.getList().add(Integer.parseInt(row.getCells().get(0)));
    	}
    	resultsCache = guiberestRestService.getSales(idArray);
	}

	@When("^I update the following sales as the roger user I receive a NoAclException:$")
	public void I_update_the_following_sales_as_the_roger_user(DataTable inserts) throws Throwable {
    	insertIdCache.getList().clear();
    	clientIdentification.setRoger();
    	for (DataTableRow row : inserts.getGherkinRows()) {
    		ClientResponse<?> response = (ClientResponse<?>)guiberestRestService.putSale(
    				Integer.parseInt(row.getCells().get(1).trim()),
    				Integer.parseInt(row.getCells().get(2).trim()),
    				Integer.parseInt(row.getCells().get(0).trim()),
    				Float.parseFloat(row.getCells().get(3).trim())
    		);
    		System.out.println("Response from updating sale as Roger: " + response.getStatus());
    		Assert.assertTrue(response.getStatus() == 401);
    		response.releaseConnection();
    	}
	}

	@SuppressWarnings("unchecked")
	@When("^I update the following sales as the sean user:$")
	public void I_update_the_following_sales_as_the_sean_user(DataTable inserts) throws Throwable {
    	insertIdCache.getList().clear();
    	clientIdentification.setSean();
    	for (DataTableRow row : inserts.getGherkinRows()) {
    		ClientResponse<String> response = (ClientResponse<String>)guiberestRestService.putSale(
    				Integer.parseInt(row.getCells().get(1).trim()),
    				Integer.parseInt(row.getCells().get(2).trim()),
    				Integer.parseInt(row.getCells().get(0).trim()),
    				Float.parseFloat(row.getCells().get(3).trim())
    		);
    		Integer id = response.getEntity(Integer.class);
    		System.out.println("Updated Sale with ID: " + id);
    		insertIdCache.getList().add(id);
    	}
	}

	@SuppressWarnings("unused")
	@When("^I delete the following sales as the sean user:$")
	public void I_delete_the_following_sales_as_the_sean_user(DataTable deletes) throws Throwable {
    	insertIdCache.getList().clear();
    	clientIdentification.setSean();
    	for (DataTableRow row : deletes.getGherkinRows()) {
    		Response response = guiberestRestService.deleteSale(
    				Integer.parseInt(row.getCells().get(0).trim())
    		);
    	}
	}

	@When("^I insert the following sales I receive a NoAclException:$")
	public void I_insert_the_following_sales_I_receive_a_NoAclException(DataTable inserts) throws Throwable {
    	clientIdentification.setThomas();
    	insertIdCache.getList().clear();
    	for (DataTableRow row : inserts.getGherkinRows()) {
    		ClientResponse<?> response = (ClientResponse<?>)guiberestRestService.putSale(
    				Integer.parseInt(row.getCells().get(1).trim()),
    				Integer.parseInt(row.getCells().get(2).trim()),
    				Integer.parseInt(row.getCells().get(0).trim()),
    				Float.parseFloat(row.getCells().get(3).trim())
    		);
    		System.out.println("Response from inserting sale: " + response.getStatus());
    		Assert.assertTrue(response.getStatus() == 401);
    		response.releaseConnection();
    	}
	}

	@When("^I request sales as user roger for the following sale ids I receive a NoAclException:$")
	public void I_request_sales_as_user_roger_for_the_following_sale_ids_I_receive_a_NoAclException(DataTable ids) throws Throwable {
		System.out.println("Will now be running as roger...");
		clientIdentification.setRoger();
    	Boolean error = false;
    	final IntegerArray idArray = new IntegerArray();
    	for (DataTableRow row : ids.getGherkinRows()) {
    		idArray.getList().add(Integer.parseInt(row.getCells().get(0)));
    	}
    	try {
    		resultsCache = guiberestRestService.getSales(idArray);
    	} catch (ClientResponseFailure e) {
    		error = true;
    	}
    	if (! error && resultsCache != null) {
    		System.out.println("Found sales in error:");
    	}
    	Assert.assertTrue(error);
    	System.out.println("Done running as roger...");
	}

	@When("^I delete the following sales as the roger user I receive a NoAclException:$")
	public void I_delete_the_following_sales_as_the_roger_user_I_receive_a_NoAclException(DataTable deletes) throws Throwable {
		clientIdentification.setRoger();
    	insertIdCache.getList().clear();
    	for (DataTableRow row : deletes.getGherkinRows()) {
    		ClientResponse<?> response = (ClientResponse<?>)guiberestRestService.deleteSale(
    				Integer.parseInt(row.getCells().get(0).trim())
    		);
    		Assert.assertTrue(response.getStatus() == 401);
    		response.releaseConnection();
    	}
	}	
	
}

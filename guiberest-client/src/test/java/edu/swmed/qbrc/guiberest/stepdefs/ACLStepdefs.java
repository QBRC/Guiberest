package edu.swmed.qbrc.guiberest.stepdefs;

import javax.ws.rs.core.Response;
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
import edu.swmed.qbrc.jacksonate.rest.util.StringArray;
import gherkin.formatter.model.DataTableRow;

public class ACLStepdefs {
    
	final GuiberestRestService guiberestRestService;
	final ClientIdentification clientIdentification;
	
    @Inject
    public ACLStepdefs(final GuiberestRestService guiberestRestService, final ClientIdentification clientIdentification) {
    	this.guiberestRestService = guiberestRestService;
    	this.clientIdentification = clientIdentification;
    }
	
	@When("^I insert the following sale acls:$")
    public void I_insert_the_following_sale_acls(DataTable inserts) throws Throwable {
    	clientIdentification.setThomas();
    	for (DataTableRow row : inserts.getGherkinRows()) {
    		StringArray roles = new StringArray(row.getCells().get(1).trim());
    		Response response = guiberestRestService.addAcl(
    				row.getCells().get(0).trim(),
    				Sale.class.getName(),
    				row.getCells().get(2).trim(),
    				roles
    		);
    		response.close();
    	}
    }

	@When("^I delete the following sale acls:$")
    public void I_delete_the_following_sale_acls(DataTable deletes) throws Throwable {
    	clientIdentification.setThomas();
    	for (DataTableRow row : deletes.getGherkinRows()) {
    		StringArray roles = new StringArray(row.getCells().get(1).trim());
    		Response response = guiberestRestService.deleteAcl(
    				row.getCells().get(0).trim(),
    				Sale.class.getName(),
    				row.getCells().get(2).trim(),
    				roles
    		);
    		response.close();
    	}
    }

	@Then("^if I check ACLs for sale (\\d+) I see the following ACLs:$")
	public void if_I_check_ACLs_for_sale_I_see_the_following_ACLs(Integer sale, DataTable acls) throws Throwable {
    	clientIdentification.setThomas();
    	TableJSONContainer<ACL> aclstable = guiberestRestService.getAcls(Sale.class.getName(), sale.toString());
    	for (DataTableRow row : acls.getGherkinRows()) {
    		Boolean bFound = false;
    		for (ACL acl : aclstable.getData()) {
    			System.out.println("Searching..." + acl.getAccess() + " | " + acl.getUsername() + " | " + acl.getRoleId());
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
		for (ACL acl : aclstable.getData()) {
			System.out.println("Found ACL: " + acl.getAccess() + "; " + acl.getObjectClass() + "; " + acl.getObjectPK() + "; " + acl.getUsername() + "; " + acl.getRoleId());
		}
		Assert.assertTrue(aclstable.getData().size() <= results);
	}	
	
	
}

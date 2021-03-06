package edu.swmed.qbrc.guiberest.shared.domain.guiberest;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;
import edu.swmed.qbrc.auth.cashmac.shared.constants.CasHmacAccessLevels;
import edu.swmed.qbrc.auth.cashmac.shared.util.TestableEntity;
import edu.swmed.qbrc.auth.cashmac.shared.util.TestableEntityString;
import edu.swmed.qbrc.auth.cashmac.shared.annotations.*;
import edu.swmed.qbrc.guiberest.shared.domain.BaseEntity;
import edu.swmed.qbrc.jacksonate.rest.datapackage.DataPackage.DataPackageClass;
import edu.swmed.qbrc.guiberest.shared.guice.datasources.GuiberestDataSource;

@CasHmacObjectAcl
@CasHmacObjectRead(accessLevel=CasHmacAccessLevels.READ, objectClass=Sale.class)
@CasHmacObjectUpdate(accessLevel=CasHmacAccessLevels.UPDATE, objectClass=Sale.class)
@CasHmacObjectDelete(accessLevel=CasHmacAccessLevels.DELETE, objectClass=Sale.class)
@SuppressWarnings("rawtypes")
@Entity
@Table(name = "sale")
@XmlRootElement(name = "Sale")
@DataPackageClass(url="sale")
public class Sale implements Comparable, BaseEntity, TestableEntity<Sale> {

	private static final long serialVersionUID = -8186720274713597706L;
	
	@Id
	@CasHmacPKField
	@CasHmacWriteAcl({
		@CasHmacWriteAclParameter(access=CasHmacAccessLevels.READ, roles={ "SELF", "audit", "manager", "decreaser" }),
		@CasHmacWriteAclParameter(access=CasHmacAccessLevels.UPDATE, roles={}),
		@CasHmacWriteAclParameter(access=CasHmacAccessLevels.UPDATE, roles={ "manager", "decreaser" }),
		@CasHmacWriteAclParameter(access=CasHmacAccessLevels.DELETE, roles={ "SELF", "manager" }),
		@CasHmacWriteAclParameter(access="DECREASE", roles={ "decreaser" })
	})
    @Column(name="sale_id")
    private Integer id;
	
	@CasHmacForeignFieldRead(accessLevel=CasHmacAccessLevels.READ, objectClass=Store.class, foreignEntityManager=GuiberestDataSource.class)
	@CasHmacForeignFieldUpdate(accessLevel=CasHmacAccessLevels.READ, objectClass=Store.class, foreignEntityManager=GuiberestDataSource.class)
	@CasHmacForeignFieldCreate(accessLevel=CasHmacAccessLevels.READ, objectClass=Store.class, foreignEntityManager=GuiberestDataSource.class)
	@Column(name="store_id")
	private Integer storeId;

	@CasHmacForeignFieldUpdate(accessLevel=CasHmacAccessLevels.UPDATE, objectClass=Customer.class, foreignEntityManager=GuiberestDataSource.class)
	@CasHmacForeignFieldCreate(accessLevel=CasHmacAccessLevels.UPDATE, objectClass=Customer.class, foreignEntityManager=GuiberestDataSource.class)
	@Column(name="customer_id")
    private Integer customerId;
    
	@Column(name="total")
    private Float total;
    
    public Sale() {
    }
    
	public Sale(Integer id, Integer storeId, Integer customerId, Float total) {
		super();		
		this.setId(id);
		this.setStoreId(storeId);
		this.setCustomerId(customerId);
		this.setTotal(total);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sale other = (Sale) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	@FormParam("sale_id")
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int compareTo(Object obj) {
		if (this == obj)
			return 1;
		if (obj == null)
			return 0;
		if (getClass() != obj.getClass())
			return 0;
		Sale other = (Sale) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return 0;
		} else if (!getId().equals(other.getId()))
			return 0;
		return 1;
	}

	public Float getTotal() {
		return total;
	}

	@FormParam("total")
	public void setTotal(Float total) {
		this.total = total;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	@FormParam("customer_id")
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getStoreId() {
		return storeId;
	}

	@FormParam("store_id")
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	
	@Override
	public void testInput(List<TestableEntityString> input) {
		if (input.size() == 4) {
			this.setId(input.get(0).getInt());
			this.setStoreId(input.get(1).getInt());
			this.setCustomerId(input.get(2).getInt());
			this.setTotal(input.get(3).getFloat());
		}
	}

	@Override
	public List<TestableEntityString> testOutput() {
		List<TestableEntityString> output = new ArrayList<TestableEntityString>();
		output.add(new TestableEntityString(this.getId()));
		output.add(new TestableEntityString(this.getStoreId()));
		output.add(new TestableEntityString(this.getCustomerId()));
		output.add(new TestableEntityString(this.getTotal()));
		return output;
	}
}

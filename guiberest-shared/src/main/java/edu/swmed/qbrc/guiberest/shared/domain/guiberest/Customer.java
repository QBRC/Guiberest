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
import edu.swmed.qbrc.guiberest.shared.guice.datasources.GuiberestDataSource;
import edu.swmed.qbrc.jacksonate.rest.datapackage.DataPackage.DataPackageClass;

@SuppressWarnings("rawtypes")
@Entity
@Table(name = "customer")
@XmlRootElement(name = "Customer")
@DataPackageClass(url="customer")
public class Customer implements Comparable, BaseEntity, TestableEntity<Customer> {

	private static final long serialVersionUID = 7386748923294189168L;

	@Id
	@CasHmacPKField
    @Column(name="customer_id")
    private Integer id;
	
	@CasHmacForeignFieldRead(accessLevel=CasHmacAccessLevels.READ, objectClass=Store.class, foreignEntityManager=GuiberestDataSource.class)
	@CasHmacForeignFieldUpdate(accessLevel=CasHmacAccessLevels.UPDATE, objectClass=Store.class, foreignEntityManager=GuiberestDataSource.class)
	@Column(name="preferred_store_id")
	private Integer preferredStoreId;

	@Column(name="name")
    private String name;
    
    public Customer() {
    }
    
	public Customer(Integer id, Integer storeId, String name) {
		super();		
		this.setId(id);
		this.setPreferredStoreId(storeId);
		this.setName(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
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

	@FormParam("customer_id")
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
		Customer other = (Customer) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return 0;
		} else if (!getId().equals(other.getId()))
			return 0;
		return 1;
	}

	public Integer getPreferredStoreId() {
		return preferredStoreId;
	}

	@FormParam("preferred_store_id")
	public void setPreferredStoreId(Integer preferredStoreId) {
		this.preferredStoreId = preferredStoreId;
	}

	public String getName() {
		return name;
	}

	@FormParam("name")
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void testInput(List<TestableEntityString> input) {
		if (input.size() == 3) {
			this.setId(input.get(0).getInt());
			this.setPreferredStoreId(input.get(1).getInt());
			this.setName(input.get(2).getStringOrNull());
		}
	}

	@Override
	public List<TestableEntityString> testOutput() {
		List<TestableEntityString> output = new ArrayList<TestableEntityString>();
		output.add(new TestableEntityString(this.getId()));
		output.add(new TestableEntityString(this.getPreferredStoreId()));
		output.add(new TestableEntityString(this.getName()));
		return output;
	}
	
}

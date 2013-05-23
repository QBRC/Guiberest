package edu.swmed.qbrc.guiberest.shared.domain.guiberest;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;
import edu.swmed.qbrc.auth.cashmac.shared.annotations.*;
import edu.swmed.qbrc.auth.cashmac.shared.constants.CasHmacAccessLevels;
import edu.swmed.qbrc.auth.cashmac.shared.util.TestableEntity;
import edu.swmed.qbrc.auth.cashmac.shared.util.TestableEntityString;
import edu.swmed.qbrc.guiberest.shared.domain.BaseEntity;
import edu.swmed.qbrc.jacksonate.rest.datapackage.DataPackage.DataPackageClass;

@SuppressWarnings("rawtypes")
@Entity
@Table(name = "store")
@XmlRootElement(name = "Store")
@DataPackageClass(url="store")
@CasHmacObjectAcl
@CasHmacObjectRead(accessLevel=CasHmacAccessLevels.READ, objectClass=Store.class)
@CasHmacObjectUpdate(accessLevel=CasHmacAccessLevels.UPDATE, objectClass=Store.class)
@CasHmacObjectDelete(accessLevel=CasHmacAccessLevels.DELETE, objectClass=Store.class)
public class Store implements Comparable, BaseEntity, TestableEntity<Store> {

	private static final long serialVersionUID = -3044099150624572000L;

	@Id
	@CasHmacPKField
	@CasHmacWriteAcl({
		@CasHmacWriteAclParameter(access=CasHmacAccessLevels.READ, roles={}),
		@CasHmacWriteAclParameter(access=CasHmacAccessLevels.UPDATE, roles={}),
		@CasHmacWriteAclParameter(access=CasHmacAccessLevels.DELETE, roles={ "SELF", "manager" })
	})
    @Column(name="store_id")
    private Integer id;
	
	@Column(name="name")
    private String name;
    
    public Store() {
    }
    
	public Store(Integer id, String name) {
		super();		
		this.setId(id);
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
		Store other = (Store) obj;
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

	@FormParam("store_id")
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
		Store other = (Store) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return 0;
		} else if (!getId().equals(other.getId()))
			return 0;
		return 1;
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
		if (input.size() == 2) {
			this.setId(input.get(0).getInt());
			this.setName(input.get(1).getStringOrNull());
		}
	}

	@Override
	public List<TestableEntityString> testOutput() {
		List<TestableEntityString> output = new ArrayList<TestableEntityString>();
		output.add(new TestableEntityString(this.getId()));
		output.add(new TestableEntityString(this.getName()));
		return output;
	}
}

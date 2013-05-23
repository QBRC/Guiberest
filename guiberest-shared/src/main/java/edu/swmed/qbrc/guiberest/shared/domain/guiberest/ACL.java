package edu.swmed.qbrc.guiberest.shared.domain.guiberest;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import edu.swmed.qbrc.auth.cashmac.shared.util.TestableEntity;
import edu.swmed.qbrc.auth.cashmac.shared.util.TestableEntityString;
import edu.swmed.qbrc.guiberest.shared.domain.BaseEntity;

@Entity
@Table(name = "acl")
@XmlRootElement(name = "ACL")
public class ACL implements BaseEntity, TestableEntity<ACL> {
	
	private static final long serialVersionUID = -2387392838496421804L;
	
	@Id
    @Column(name="id")
	@GeneratedValue
    private Integer id;    
    @Column(name="username", nullable=true)
    private String username;
    @Column(name="role_id", nullable=true)
    private Integer roleId;
    @Column(name="access")
    private String access;
    @Column(name="class")
    private String objectClass;
    @Column(name="pk")
    private String objectPK;
    
    public ACL() {    	
		this.setId(null);
		this.setUsername(null);
		this.setRoleId(null);
    }
    
	public ACL(Integer id, String username, Integer roleId, String access, String objectClass, String objectPK) {
		super();
		this.setId(id);
		this.setUsername(username);
		this.setRoleId(roleId);
		this.setAccess(access);
		this.setObjectClass(objectClass);
		this.setObjectPK(objectPK);
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getObjectClass() {
		return objectClass;
	}

	public void setObjectClass(String objectClass) {
		this.objectClass = objectClass;
	}

	public String getObjectPK() {
		return objectPK;
	}

	public void setObjectPK(String objectPK) {
		this.objectPK = objectPK;
	}

	@Override
	public void testInput(List<TestableEntityString> input) {
		if (input.size() == 5) {
			this.setId(input.get(0).getInt());
			this.setUsername(input.get(1).getStringOrNull());
			this.setRoleId(input.get(2).getInt());
			this.setAccess(input.get(3).getStringOrNull());
			this.setObjectClass(input.get(4).getStringOrNull());
			this.setObjectPK(input.get(5).getStringOrNull());
		}
	}

	@Override
	public List<TestableEntityString> testOutput() {
		List<TestableEntityString> output = new ArrayList<TestableEntityString>();
		output.add(new TestableEntityString(this.getId()));
		output.add(new TestableEntityString(this.getUsername()));
		output.add(new TestableEntityString(this.getRoleId()));
		output.add(new TestableEntityString(this.getAccess()));
		output.add(new TestableEntityString(this.getObjectClass()));
		output.add(new TestableEntityString(this.getObjectPK()));
		return output;
	}
}
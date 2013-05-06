package edu.swmed.qbrc.guiberest.shared.domain.guiberest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import edu.swmed.qbrc.guiberest.shared.domain.BaseEntity;
import edu.swmed.qbrc.jacksonate.rest.datapackage.DataPackage.DataPackageClass;

@Entity
@Table(name = "acl")
@XmlRootElement(name = "ACL")
@DataPackageClass(url="acl")
public class ACL implements BaseEntity {
	
	private static final long serialVersionUID = -2387392838496421804L;
	
	@Id
    @Column(name="id")
    private Integer id;    
    @Column(name="username")
    private String username;
    @Column(name="role_id")
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
	
}
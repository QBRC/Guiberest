package edu.swmed.qbrc.guiberest.shared.domain.guiberest;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import edu.swmed.qbrc.guiberest.shared.domain.BaseEntity;

@Entity
@Table(name = "roleusers")
@XmlRootElement(name = "RoleUser")
public class RoleUser implements BaseEntity {

	private static final long serialVersionUID = 6137907913097470256L;
	
	@EmbeddedId
	private RoleUserPK roleUserPK;

	@Column(name="roleid", insertable=false, updatable=false)
    private Integer roleId;    
    @Column(name="username", insertable=false, updatable=false)
    private String username;
    
    public RoleUser() {    	
		this.setRoleId(null);
		this.setUsername("");
    }
    
	public RoleUser(RoleUserPK roleUserPK, Integer roleId, String username) {
		super();
		this.setRoleUserPK(roleUserPK);
		this.setRoleId(roleId);
		this.setUsername(username);
	}
	
	public RoleUserPK getRoleUserPK() {
		return this.roleUserPK;
	}
	public void setRoleUserPK(RoleUserPK roleUserPK) {
		this.roleUserPK = roleUserPK;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public Object getId() {
		return username + ":" + roleId.toString();
	}
	
}
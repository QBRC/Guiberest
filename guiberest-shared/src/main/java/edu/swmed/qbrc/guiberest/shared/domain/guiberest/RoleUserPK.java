package edu.swmed.qbrc.guiberest.shared.domain.guiberest;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RoleUserPK implements Serializable {

	private static final long serialVersionUID = 6137907913097470256L;
	
    @Column(name="roleid")
    private Integer roleId;    
    @Column(name="username")
    private String username;
    
    public RoleUserPK() {    	
		this.setRoleId(null);
		this.setUsername("");
    }
    
	public RoleUserPK(Integer roleId, String username) {
		super();
		this.setRoleId(roleId);
		this.setUsername(username);
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
	
}
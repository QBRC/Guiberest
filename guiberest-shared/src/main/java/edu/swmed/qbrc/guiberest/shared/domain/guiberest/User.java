package edu.swmed.qbrc.guiberest.shared.domain.guiberest;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;
import edu.swmed.qbrc.auth.cashmac.shared.constants.CasHmacAccessLevels;
import edu.swmed.qbrc.auth.cashmac.shared.annotations.*;
import edu.swmed.qbrc.guiberest.shared.domain.BaseEntity;
import edu.swmed.qbrc.jacksonate.rest.datapackage.DataPackage.DataPackageClass;

@CasHmacObjectAcl
@CasHmacObjectCreate(accessLevel=CasHmacAccessLevels.CREATE, objectClass=User.class) 
@CasHmacObjectRead(accessLevel=CasHmacAccessLevels.READ, objectClass=User.class)
@CasHmacObjectUpdate(accessLevel=CasHmacAccessLevels.UPDATE, objectClass=User.class)
@CasHmacObjectDelete(accessLevel=CasHmacAccessLevels.DELETE, objectClass=User.class)
@SuppressWarnings("rawtypes")
@Entity
@Table(name = "users")
@XmlRootElement(name = "User")
@DataPackageClass(url="user")
public class User implements BaseEntity, Comparable {
	private static final long serialVersionUID = 5489594906310275717L;
	
	@Id
	@CasHmacPKField
	@CasHmacWriteAcl({
		@CasHmacWriteAclParameter(access=CasHmacAccessLevels.READ, roles={}),
		@CasHmacWriteAclParameter(access=CasHmacAccessLevels.UPDATE, roles={}),
		@CasHmacWriteAclParameter(access=CasHmacAccessLevels.READ, roles={ "audit" }),
		@CasHmacWriteAclParameter(access=CasHmacAccessLevels.DELETE, roles={ "SELF", "manager" })
	})
    @Column(name="id")
    private String id;    
    @Column(name="password")
    private String password;
    @Column(name="secret")
    private String secret;
    
    @Transient
    private List<String> normalization;

    public User() {
    	this.setId("");
    	this.setPassword("");
    	this.setSecret("");
    }
    
	public User(String id, String password, String secret) {
		super();		
		this.setId(id);
		this.setPassword(password);
		this.setSecret(secret);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@FormParam("user_id")
	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	@FormParam("password")
	public void setPassword(String password) {
		this.password = password;
	}

	public String getSecret() {
		return secret;
	}

	@FormParam("secret")
	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Override
	public int compareTo(Object obj) {
		if (this == obj)
			return 1;
		if (obj == null)
			return 0;
		if (getClass() != obj.getClass())
			return 0;
		User other = (User) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return 0;
		} else if (!getId().equals(other.getId()))
			return 0;
		return 1;
	}
	
}

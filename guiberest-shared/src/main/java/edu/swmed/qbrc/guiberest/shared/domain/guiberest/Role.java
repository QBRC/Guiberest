package edu.swmed.qbrc.guiberest.shared.domain.guiberest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import edu.swmed.qbrc.guiberest.shared.domain.BaseEntity;
import edu.swmed.qbrc.jacksonate.rest.datapackage.DataPackage.DataPackageClass;

@Entity
@Table(name = "roles")
@XmlRootElement(name = "Role")
@DataPackageClass(url="role")
public class Role implements BaseEntity {
	private static final long serialVersionUID = 3587913229532814544L;
	
	@Id
    @Column(name="id")
    private Integer id;    
    @Column(name="username")
    private String username;
    @Column(name="role")
    private String role;
    
    public Role() {    	
		this.setId(null);
		this.setUsername("");
		this.setRole("");
    }
    
	public Role(Integer id, String username, String role) {
		super();
		this.setId(id);
		this.setUsername(username);
		this.setRole(role);
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
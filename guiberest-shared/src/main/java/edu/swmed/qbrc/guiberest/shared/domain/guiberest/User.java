package edu.swmed.qbrc.guiberest.shared.domain.guiberest;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import edu.swmed.qbrc.guiberest.shared.domain.BaseEntity;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.TableJSONField;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.TableJSONSerializable;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.TableJSONField.JSONFieldTypes;

@SuppressWarnings("rawtypes")
@Entity
@Table(name = "users")
@XmlRootElement(name = "User")
public class User extends TableJSONSerializable implements BaseEntity, Comparable {
	private static final long serialVersionUID = 5489594906310275717L;
	
	@Id
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
	public Object getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSecret() {
		return secret;
	}

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

	@Override
	public List<TableJSONField> getFields() {
		List<TableJSONField> fields = new ArrayList<TableJSONField>();
		fields.add(new TableJSONField("A", JSONFieldTypes.STRING, "UserID", "User ID"));
		fields.add(new TableJSONField("B", JSONFieldTypes.STRING, "Password", "User Password"));
		fields.add(new TableJSONField("C", JSONFieldTypes.STRING, "Secret", "User Secret for HMAC"));
		return fields;
	}
	
}

package edu.swmed.qbrc.guiberest.shared.domain.guiberest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;
import edu.swmed.qbrc.auth.cashmac.shared.annotations.*;
import edu.swmed.qbrc.guiberest.shared.domain.BaseEntity;
import edu.swmed.qbrc.jacksonate.rest.datapackage.DataPackage.DataPackageClass;

@SuppressWarnings("rawtypes")
@Entity
@Table(name = "store")
@XmlRootElement(name = "Store")
@DataPackageClass(url="store")
@CasHmacObjectAcl
public class Store implements BaseEntity, Comparable {

	private static final long serialVersionUID = -3044099150624572000L;

	@Id
	@CasHmacPKField
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
	
}

package edu.swmed.qbrc.guiberest.rest.util;

import org.jboss.resteasy.spi.BadRequestException;

import edu.swmed.qbrc.auth.cashmac.shared.exceptions.NoAclException;
import edu.swmed.qbrc.guiberest.dao.BaseDao;
import edu.swmed.qbrc.guiberest.shared.domain.BaseEntity;

public class EntityLoader<T extends BaseEntity> {

	private BaseDao<T> baseDao;
	private Class<T> clazz;
	
	public EntityLoader(Class<T> clazz, BaseDao<T> base) {
		this.baseDao = base;
		this.clazz = clazz;
	}
	
	public T findOrNull(Object object) {
		T out = null;
		if (object != null) {
			try {
				out = (T)baseDao.find(object);
			} catch (NoAclException e) {
				throw e;
			} catch (Exception any) {
				throw new BadRequestException("Exception while loading object of type " + clazz.getName());
			}
		}
		return out;
	}
	
	public T findOrError(Object object) {
		T out = null;
		// Go ahead and return null if the id passed in was null. 
		if (object != null) {
			try {
				out = (T)baseDao.find(object);
			} catch (NoAclException e) {
				throw e;
			} catch (Exception any) {
				throw new BadRequestException("Exception while loading object of type " + clazz.getName());
			}
			if (out == null) {
				throw new BadRequestException("Null object returned when loading object of type " + clazz.getName());
			}
		}
		return out;
	}
	
}
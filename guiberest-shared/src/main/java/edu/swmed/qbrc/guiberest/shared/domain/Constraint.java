package edu.swmed.qbrc.guiberest.shared.domain;

import java.io.Serializable;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.User;
import edu.swmed.qbrc.jacksonate.rest.util.CharArray;
import edu.swmed.qbrc.jacksonate.rest.util.IntegerArray;
import edu.swmed.qbrc.guiberest.shared.rest.util.UserIDArray;
import edu.swmed.qbrc.jacksonate.rest.util.StringArray;

public class Constraint<T> implements Serializable {

	public enum Operator {
		GREATER_THAN,
		GREATER_THAN_EQUAL,
		LESS_THAN,
		LESS_THAN_EQUAL,
		EQUAL,
		NOT_EQUAL
	}
	
	public enum Junction {
		AND,
		OR
	}
	
	private static final long serialVersionUID = -345382605748203133L;
	
	private String fieldName;
	private Operator operator;
	private T value;
	@SuppressWarnings("rawtypes")
	private Class clazz;
	
	public Constraint() {
	}
	
	@SuppressWarnings("rawtypes")
	public Constraint(String fieldName, Operator operator, T value, Class clazz) {
		this.fieldName = fieldName;
		this.operator = operator;
		this.value = value;
		this.clazz = clazz;
	}
	
	@SuppressWarnings("rawtypes")
	public Class getClazz() {
		return this.clazz;
	}
	
	public boolean isNull() {
		return this.value == null;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	
    @SuppressWarnings("rawtypes")
	public Predicate getPredicate(CriteriaBuilder cb, Root root, Predicate pred) {
		
		// If evaluating an array of integers
		if (this.clazz.getSimpleName().equals("IntegerArray")) {
			Predicate thisCrit = cb.disjunction();
			for (Integer val : ((IntegerArray)this.getValue()).getList()) {
				Constraint cons = new Constraint<Integer>(this.getFieldName(), this.getOperator(), val, Integer.class);
				thisCrit = cb.or(thisCrit, getPredicateDetail(cons, cb, root));
			}
			pred = cb.and(thisCrit, pred);
		}
		
		// If evaluating an array of strings
		else if (this.clazz.getSimpleName().equals("StringArray")) {
			Predicate thisCrit = cb.disjunction();
			for (String val : ((StringArray)this.getValue()).getList()) {
				Constraint cons = new Constraint<String>(this.getFieldName(), this.getOperator(), val, String.class);
				thisCrit = cb.or(thisCrit, getPredicateDetail(cons, cb, root));
			}
			pred = cb.and(thisCrit, pred);
		}
		
		// If evaluating an array of chars
		else if (this.clazz.getSimpleName().equals("CharArray")) {
			Predicate thisCrit = cb.disjunction();
			for (Character val : ((CharArray)this.getValue()).getList()) {
				Constraint cons = new Constraint<Character>(this.getFieldName(), this.getOperator(), val, Character.class);
				thisCrit = cb.or(thisCrit, getPredicateDetail(cons, cb, root));
			}
			pred = cb.and(thisCrit, pred);
		}

		// If evaluating an array of User IDs
		else if (this.clazz.getSimpleName().equals("UserIDArray")) {
			Predicate thisCrit = cb.disjunction();
			for (User val : ((UserIDArray)this.getValue()).getList()) {
				Constraint cons = new Constraint<String>(this.getFieldName(), this.getOperator(), val.getId().toString(), String.class);
				thisCrit = cb.or(thisCrit, getPredicateDetail(cons, cb, root));
			}
			pred = cb.and(thisCrit, pred);
		}

		// Otherwise
		else {
			pred = cb.and(pred, getPredicateDetail(this, cb, root));
		}
		
		return pred;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private Predicate getPredicateDetail(Constraint constraint, CriteriaBuilder cb, Root root) {
		Path<Comparable> field = root.get(constraint.getFieldName());
		Predicate pred;
		
		switch (this.getOperator()) {
    		case EQUAL:
    			pred = cb.equal(field, getKey(constraint));
    			break;
    		case NOT_EQUAL:
    			pred = cb.notEqual(field, getKey(constraint));
    			break;
    		case GREATER_THAN:
    			pred = cb.greaterThan(field, getKey(constraint));
    			break;
    		case LESS_THAN:
    			pred = cb.lessThan(field, getKey(constraint));
    			break;
    		case GREATER_THAN_EQUAL:
    			pred = cb.greaterThanOrEqualTo(field, getKey(constraint));
    			break;
    		case LESS_THAN_EQUAL:
    			pred = cb.lessThanOrEqualTo(field, getKey(constraint));
    			break;
    		default:
    			pred = cb.equal(field, getKey(constraint));
    			break;
		}
		return pred;
    }
	
    @SuppressWarnings("rawtypes")
    private Comparable getKey(Constraint constraint) {
    	Comparable key;
    	
    	if (constraint.getClazz().getSimpleName().equals("Integer")) {
    		key = (Integer)constraint.getValue();
    	}
    	else if (constraint.getClazz().getSimpleName().equals("Character")) {
    		key = (Character)constraint.getValue();
    	}
    	else if (constraint.getClazz().getSimpleName().equals("User")) {
    		key = (User)constraint.getValue();
    	}
    	else {
    		key = (String)constraint.getValue();
    	}
    	
    	return key;
    }    
	
}
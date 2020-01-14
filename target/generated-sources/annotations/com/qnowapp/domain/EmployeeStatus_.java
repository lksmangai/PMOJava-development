package com.qnowapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmployeeStatus.class)
public abstract class EmployeeStatus_ {

	public static volatile SingularAttribute<EmployeeStatus, String> statusName;
	public static volatile SingularAttribute<EmployeeStatus, Long> id;
	public static volatile SingularAttribute<EmployeeStatus, String> statusCode;

	public static final String STATUS_NAME = "statusName";
	public static final String ID = "id";
	public static final String STATUS_CODE = "statusCode";

}


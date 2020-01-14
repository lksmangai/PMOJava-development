package com.qnowapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Department.class)
public abstract class Department_ {

	public static volatile SingularAttribute<Department, String> departmentName;
	public static volatile SingularAttribute<Department, String> departmentCode;
	public static volatile SingularAttribute<Department, Long> id;

	public static final String DEPARTMENT_NAME = "departmentName";
	public static final String DEPARTMENT_CODE = "departmentCode";
	public static final String ID = "id";

}


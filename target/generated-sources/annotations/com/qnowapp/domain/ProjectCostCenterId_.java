package com.qnowapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProjectCostCenterId.class)
public abstract class ProjectCostCenterId_ {

	public static volatile SetAttribute<ProjectCostCenterId, Company> companies;
	public static volatile SingularAttribute<ProjectCostCenterId, String> code;
	public static volatile SingularAttribute<ProjectCostCenterId, String> name;
	public static volatile SingularAttribute<ProjectCostCenterId, String> description;
	public static volatile SingularAttribute<ProjectCostCenterId, Long> id;

	public static final String COMPANIES = "companies";
	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";

}


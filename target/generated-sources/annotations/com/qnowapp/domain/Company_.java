package com.qnowapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Company.class)
public abstract class Company_ {

	public static volatile SingularAttribute<Company, String> code;
	public static volatile SingularAttribute<Company, String> name;
	public static volatile SingularAttribute<Company, String> description;
	public static volatile SingularAttribute<Company, Long> id;
	public static volatile SetAttribute<Company, ProjectCostCenterId> projectCostCenterIds;

	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String PROJECT_COST_CENTER_IDS = "projectCostCenterIds";

}


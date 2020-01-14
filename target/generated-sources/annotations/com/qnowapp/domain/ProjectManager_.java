package com.qnowapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProjectManager.class)
public abstract class ProjectManager_ {

	public static volatile SingularAttribute<ProjectManager, String> project_lead_first_name;
	public static volatile SingularAttribute<ProjectManager, String> project_lead_last_name;
	public static volatile SingularAttribute<ProjectManager, Integer> count;
	public static volatile SingularAttribute<ProjectManager, String> project_lead_email;
	public static volatile SingularAttribute<ProjectManager, Long> id;

	public static final String PROJECT_LEAD_FIRST_NAME = "project_lead_first_name";
	public static final String PROJECT_LEAD_LAST_NAME = "project_lead_last_name";
	public static final String COUNT = "count";
	public static final String PROJECT_LEAD_EMAIL = "project_lead_email";
	public static final String ID = "id";

}


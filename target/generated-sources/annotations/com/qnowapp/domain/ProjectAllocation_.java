package com.qnowapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProjectAllocation.class)
public abstract class ProjectAllocation_ {

	public static volatile SingularAttribute<ProjectAllocation, ProjectRoles> projectRoles;
	public static volatile SingularAttribute<ProjectAllocation, Double> percentage;
	public static volatile SingularAttribute<ProjectAllocation, ImProjects> imProjects;
	public static volatile SingularAttribute<ProjectAllocation, Long> id;
	public static volatile SingularAttribute<ProjectAllocation, ImEmployee> imEmployee;

	public static final String PROJECT_ROLES = "projectRoles";
	public static final String PERCENTAGE = "percentage";
	public static final String IM_PROJECTS = "imProjects";
	public static final String ID = "id";
	public static final String IM_EMPLOYEE = "imEmployee";

}


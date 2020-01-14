package com.qnowapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AllocationDetailsModel.class)
public abstract class AllocationDetailsModel_ {

	public static volatile SingularAttribute<AllocationDetailsModel, Integer> level;
	public static volatile SingularAttribute<AllocationDetailsModel, Long> imProjects;
	public static volatile SingularAttribute<AllocationDetailsModel, String> employeeEmail;
	public static volatile SingularAttribute<AllocationDetailsModel, String> employeeFirstName;
	public static volatile SingularAttribute<AllocationDetailsModel, Long> imEmployee;
	public static volatile SingularAttribute<AllocationDetailsModel, String> projectStatusName;
	public static volatile SingularAttribute<AllocationDetailsModel, Long> parentId;
	public static volatile SingularAttribute<AllocationDetailsModel, String> employeeLastName;
	public static volatile SingularAttribute<AllocationDetailsModel, Integer> sortOrder;
	public static volatile SingularAttribute<AllocationDetailsModel, Long> id;
	public static volatile SingularAttribute<AllocationDetailsModel, String> projectName;
	public static volatile SingularAttribute<AllocationDetailsModel, Long> projectStatusId;
	public static volatile SingularAttribute<AllocationDetailsModel, Long> ppdparentid;
	public static volatile SingularAttribute<AllocationDetailsModel, Boolean> milestoneP;

	public static final String LEVEL = "level";
	public static final String IM_PROJECTS = "imProjects";
	public static final String EMPLOYEE_EMAIL = "employeeEmail";
	public static final String EMPLOYEE_FIRST_NAME = "employeeFirstName";
	public static final String IM_EMPLOYEE = "imEmployee";
	public static final String PROJECT_STATUS_NAME = "projectStatusName";
	public static final String PARENT_ID = "parentId";
	public static final String EMPLOYEE_LAST_NAME = "employeeLastName";
	public static final String SORT_ORDER = "sortOrder";
	public static final String ID = "id";
	public static final String PROJECT_NAME = "projectName";
	public static final String PROJECT_STATUS_ID = "projectStatusId";
	public static final String PPDPARENTID = "ppdparentid";
	public static final String MILESTONE_P = "milestoneP";

}


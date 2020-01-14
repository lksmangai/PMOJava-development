package com.qnowapp.domain;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ImEmployee.class)
public abstract class ImEmployee_ {

	public static volatile SingularAttribute<ImEmployee, Integer> insurance;
	public static volatile SingularAttribute<ImEmployee, Integer> otherCosts;
	public static volatile SingularAttribute<ImEmployee, String> marriedP;
	public static volatile SingularAttribute<ImEmployee, QnowUser> qnowUser;
	public static volatile SingularAttribute<ImEmployee, ZonedDateTime> birthdate;
	public static volatile SingularAttribute<ImEmployee, String> headOfHouseholdP;
	public static volatile SingularAttribute<ImEmployee, String> jobTitle;
	public static volatile SingularAttribute<ImEmployee, Department> departmentId;
	public static volatile SingularAttribute<ImEmployee, Integer> availability;
	public static volatile SingularAttribute<ImEmployee, BigDecimal> salary;
	public static volatile SingularAttribute<ImEmployee, ImEmployee> supervisorId;
	public static volatile SingularAttribute<ImEmployee, EmployeeStatus> employeeStatus;
	public static volatile SingularAttribute<ImEmployee, String> ssNumber;
	public static volatile SingularAttribute<ImEmployee, String> dependantP;
	public static volatile SingularAttribute<ImEmployee, String> onlyJobP;
	public static volatile SingularAttribute<ImEmployee, BigDecimal> hourlyCost;
	public static volatile SetAttribute<ImEmployee, UserContact> userContacts;
	public static volatile SingularAttribute<ImEmployee, String> jobDescription;
	public static volatile SingularAttribute<ImEmployee, String> currency;
	public static volatile SingularAttribute<ImEmployee, Long> id;
	public static volatile SingularAttribute<ImEmployee, Integer> socialSecurity;

	public static final String INSURANCE = "insurance";
	public static final String OTHER_COSTS = "otherCosts";
	public static final String MARRIED_P = "marriedP";
	public static final String QNOW_USER = "qnowUser";
	public static final String BIRTHDATE = "birthdate";
	public static final String HEAD_OF_HOUSEHOLD_P = "headOfHouseholdP";
	public static final String JOB_TITLE = "jobTitle";
	public static final String DEPARTMENT_ID = "departmentId";
	public static final String AVAILABILITY = "availability";
	public static final String SALARY = "salary";
	public static final String SUPERVISOR_ID = "supervisorId";
	public static final String EMPLOYEE_STATUS = "employeeStatus";
	public static final String SS_NUMBER = "ssNumber";
	public static final String DEPENDANT_P = "dependantP";
	public static final String ONLY_JOB_P = "onlyJobP";
	public static final String HOURLY_COST = "hourlyCost";
	public static final String USER_CONTACTS = "userContacts";
	public static final String JOB_DESCRIPTION = "jobDescription";
	public static final String CURRENCY = "currency";
	public static final String ID = "id";
	public static final String SOCIAL_SECURITY = "socialSecurity";

}


package com.qnowapp.domain;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ImTimesheet.class)
public abstract class ImTimesheet_ {

	public static volatile SingularAttribute<ImTimesheet, Double> billhours;
	public static volatile SingularAttribute<ImTimesheet, String> notes;
	public static volatile SingularAttribute<ImTimesheet, ZonedDateTime> logdate;
	public static volatile SingularAttribute<ImTimesheet, Double> loghours;
	public static volatile SingularAttribute<ImTimesheet, ImProjects> imProjects;
	public static volatile SingularAttribute<ImTimesheet, Long> id;
	public static volatile SingularAttribute<ImTimesheet, ImEmployee> imEmployee;

	public static final String BILLHOURS = "billhours";
	public static final String NOTES = "notes";
	public static final String LOGDATE = "logdate";
	public static final String LOGHOURS = "loghours";
	public static final String IM_PROJECTS = "imProjects";
	public static final String ID = "id";
	public static final String IM_EMPLOYEE = "imEmployee";

}


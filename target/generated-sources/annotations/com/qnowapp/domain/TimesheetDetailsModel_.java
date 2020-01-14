package com.qnowapp.domain;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TimesheetDetailsModel.class)
public abstract class TimesheetDetailsModel_ {

	public static volatile SingularAttribute<TimesheetDetailsModel, String> notes;
	public static volatile SingularAttribute<TimesheetDetailsModel, ZonedDateTime> logdate;
	public static volatile SingularAttribute<TimesheetDetailsModel, Double> loghours;
	public static volatile SingularAttribute<TimesheetDetailsModel, Long> imProjectsId;
	public static volatile SingularAttribute<TimesheetDetailsModel, Long> id;
	public static volatile SingularAttribute<TimesheetDetailsModel, Long> imEmployeeId;
	public static volatile SingularAttribute<TimesheetDetailsModel, String> logday;

	public static final String NOTES = "notes";
	public static final String LOGDATE = "logdate";
	public static final String LOGHOURS = "loghours";
	public static final String IM_PROJECTS_ID = "imProjectsId";
	public static final String ID = "id";
	public static final String IM_EMPLOYEE_ID = "imEmployeeId";
	public static final String LOGDAY = "logday";

}


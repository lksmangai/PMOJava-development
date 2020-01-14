package com.qnowapp.domain;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProjectDetailsTask.class)
public abstract class ProjectDetailsTask_ {

	public static volatile SingularAttribute<ProjectDetailsTask, Double> percentCompleted;
	public static volatile SingularAttribute<ProjectDetailsTask, String> risktype;
	public static volatile SingularAttribute<ProjectDetailsTask, String> trainingLink;
	public static volatile SingularAttribute<ProjectDetailsTask, ZonedDateTime> endDate;
	public static volatile SingularAttribute<ProjectDetailsTask, Long> projectLeadId;
	public static volatile SingularAttribute<ProjectDetailsTask, String> trainingDoc;
	public static volatile SingularAttribute<ProjectDetailsTask, String> description;
	public static volatile SingularAttribute<ProjectDetailsTask, String> projectStatusName;
	public static volatile SingularAttribute<ProjectDetailsTask, String> task_record;
	public static volatile SingularAttribute<ProjectDetailsTask, Long> parentid;
	public static volatile SingularAttribute<ProjectDetailsTask, String> projectLeadEmail;
	public static volatile SingularAttribute<ProjectDetailsTask, String> projectLeadLastName;
	public static volatile SingularAttribute<ProjectDetailsTask, String> projectTypeName;
	public static volatile SingularAttribute<ProjectDetailsTask, Double> riskprobability;
	public static volatile SingularAttribute<ProjectDetailsTask, Long> id;
	public static volatile SingularAttribute<ProjectDetailsTask, Double> riskimpact;
	public static volatile SingularAttribute<ProjectDetailsTask, String> projectLeadFirstName;
	public static volatile SingularAttribute<ProjectDetailsTask, Boolean> milestoneP;
	public static volatile SingularAttribute<ProjectDetailsTask, String> projectThemeName;
	public static volatile SingularAttribute<ProjectDetailsTask, Long> opportunityPriorityId;
	public static volatile SingularAttribute<ProjectDetailsTask, Long> count;
	public static volatile SingularAttribute<ProjectDetailsTask, String> opportunityPriorityName;
	public static volatile SingularAttribute<ProjectDetailsTask, Long> projectStatus;
	public static volatile SingularAttribute<ProjectDetailsTask, Long> projectTypeId;
	public static volatile SingularAttribute<ProjectDetailsTask, Long> projectThemeId;
	public static volatile SingularAttribute<ProjectDetailsTask, String> allocationRecord;
	public static volatile SingularAttribute<ProjectDetailsTask, String> trainingName;
	public static volatile SingularAttribute<ProjectDetailsTask, Integer> sortOrder;
	public static volatile SingularAttribute<ProjectDetailsTask, Double> projectBudgetHours;
	public static volatile SingularAttribute<ProjectDetailsTask, String> projectVerticalName;
	public static volatile SingularAttribute<ProjectDetailsTask, Double> reportedHoursCache;
	public static volatile SingularAttribute<ProjectDetailsTask, String> projectName;
	public static volatile SingularAttribute<ProjectDetailsTask, Long> projectVerticalId;
	public static volatile SingularAttribute<ProjectDetailsTask, ZonedDateTime> startDate;

	public static final String PERCENT_COMPLETED = "percentCompleted";
	public static final String RISKTYPE = "risktype";
	public static final String TRAINING_LINK = "trainingLink";
	public static final String END_DATE = "endDate";
	public static final String PROJECT_LEAD_ID = "projectLeadId";
	public static final String TRAINING_DOC = "trainingDoc";
	public static final String DESCRIPTION = "description";
	public static final String PROJECT_STATUS_NAME = "projectStatusName";
	public static final String TASK_RECORD = "task_record";
	public static final String PARENTID = "parentid";
	public static final String PROJECT_LEAD_EMAIL = "projectLeadEmail";
	public static final String PROJECT_LEAD_LAST_NAME = "projectLeadLastName";
	public static final String PROJECT_TYPE_NAME = "projectTypeName";
	public static final String RISKPROBABILITY = "riskprobability";
	public static final String ID = "id";
	public static final String RISKIMPACT = "riskimpact";
	public static final String PROJECT_LEAD_FIRST_NAME = "projectLeadFirstName";
	public static final String MILESTONE_P = "milestoneP";
	public static final String PROJECT_THEME_NAME = "projectThemeName";
	public static final String OPPORTUNITY_PRIORITY_ID = "opportunityPriorityId";
	public static final String COUNT = "count";
	public static final String OPPORTUNITY_PRIORITY_NAME = "opportunityPriorityName";
	public static final String PROJECT_STATUS = "projectStatus";
	public static final String PROJECT_TYPE_ID = "projectTypeId";
	public static final String PROJECT_THEME_ID = "projectThemeId";
	public static final String ALLOCATION_RECORD = "allocationRecord";
	public static final String TRAINING_NAME = "trainingName";
	public static final String SORT_ORDER = "sortOrder";
	public static final String PROJECT_BUDGET_HOURS = "projectBudgetHours";
	public static final String PROJECT_VERTICAL_NAME = "projectVerticalName";
	public static final String REPORTED_HOURS_CACHE = "reportedHoursCache";
	public static final String PROJECT_NAME = "projectName";
	public static final String PROJECT_VERTICAL_ID = "projectVerticalId";
	public static final String START_DATE = "startDate";

}


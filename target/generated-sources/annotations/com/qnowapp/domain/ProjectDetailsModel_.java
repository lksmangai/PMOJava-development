package com.qnowapp.domain;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProjectDetailsModel.class)
public abstract class ProjectDetailsModel_ {

	public static volatile SingularAttribute<ProjectDetailsModel, Double> percentCompleted;
	public static volatile SingularAttribute<ProjectDetailsModel, String> risktype;
	public static volatile SingularAttribute<ProjectDetailsModel, String> trainingLink;
	public static volatile SingularAttribute<ProjectDetailsModel, ZonedDateTime> endDate;
	public static volatile SingularAttribute<ProjectDetailsModel, Long> projectLeadId;
	public static volatile SingularAttribute<ProjectDetailsModel, String> trainingDoc;
	public static volatile SingularAttribute<ProjectDetailsModel, String> description;
	public static volatile SingularAttribute<ProjectDetailsModel, String> projectStatusName;
	public static volatile SingularAttribute<ProjectDetailsModel, Long> parentid;
	public static volatile SingularAttribute<ProjectDetailsModel, String> projectLeadEmail;
	public static volatile SingularAttribute<ProjectDetailsModel, String> projectLeadLastName;
	public static volatile SingularAttribute<ProjectDetailsModel, String> projectTypeName;
	public static volatile SingularAttribute<ProjectDetailsModel, Double> riskprobability;
	public static volatile SingularAttribute<ProjectDetailsModel, Long> id;
	public static volatile SingularAttribute<ProjectDetailsModel, Double> riskimpact;
	public static volatile SingularAttribute<ProjectDetailsModel, String> projectLeadFirstName;
	public static volatile SingularAttribute<ProjectDetailsModel, Boolean> milestoneP;
	public static volatile SingularAttribute<ProjectDetailsModel, String> projectThemeName;
	public static volatile SingularAttribute<ProjectDetailsModel, Long> opportunityPriorityId;
	public static volatile SingularAttribute<ProjectDetailsModel, Long> count;
	public static volatile SingularAttribute<ProjectDetailsModel, String> opportunityPriorityName;
	public static volatile SingularAttribute<ProjectDetailsModel, Long> projectStatus;
	public static volatile SingularAttribute<ProjectDetailsModel, Long> projectTypeId;
	public static volatile SingularAttribute<ProjectDetailsModel, Long> projectThemeId;
	public static volatile SingularAttribute<ProjectDetailsModel, String> allocationRecord;
	public static volatile SingularAttribute<ProjectDetailsModel, String> trainingName;
	public static volatile SingularAttribute<ProjectDetailsModel, Integer> sortOrder;
	public static volatile SingularAttribute<ProjectDetailsModel, Double> projectBudgetHours;
	public static volatile SingularAttribute<ProjectDetailsModel, String> projectVerticalName;
	public static volatile SingularAttribute<ProjectDetailsModel, Double> reportedHoursCache;
	public static volatile SingularAttribute<ProjectDetailsModel, String> projectName;
	public static volatile SingularAttribute<ProjectDetailsModel, Long> projectVerticalId;
	public static volatile SingularAttribute<ProjectDetailsModel, ZonedDateTime> startDate;

	public static final String PERCENT_COMPLETED = "percentCompleted";
	public static final String RISKTYPE = "risktype";
	public static final String TRAINING_LINK = "trainingLink";
	public static final String END_DATE = "endDate";
	public static final String PROJECT_LEAD_ID = "projectLeadId";
	public static final String TRAINING_DOC = "trainingDoc";
	public static final String DESCRIPTION = "description";
	public static final String PROJECT_STATUS_NAME = "projectStatusName";
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


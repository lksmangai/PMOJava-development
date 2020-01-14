package com.qnowapp.domain;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "project_details_task")
public class ProjectDetailsTask {
	
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "project_name")
	private String projectName;

	@Column(name = "percent_completed")
	private Double percentCompleted;

	@Column(name = "project_budget_hours")
	private Double projectBudgetHours;

	@Column(name = "end_date")
	private ZonedDateTime endDate;

	@Column(name = "start_date")
	private ZonedDateTime startDate;

	@Column(name = "reported_hours_cache")
	private Double reportedHoursCache;

	@Column(name = "risktype")
	private String risktype;

	@Column(name = "riskimpact")
	private Double riskimpact;

	@Column(name = "riskprobability")
	private Double riskprobability;

	@Column(name = "training_link")
	private String trainingLink;

	@Column(name = "training_name")
	private String trainingName;

	@Column(name = "training_doc")
	private String trainingDoc;

	@Column(name = "sort_order")
	private Integer sortOrder;

	@Column(name = "parent_id_id")
	private Long parentId;

	@Column(name = "project_status_id_id")
	private Long projectStatusId;
	
	

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Column(name = "project_status_id_name")
	private String projectStatusName;

	@Column(name = "project_lead_id_id")
	private Long projectLeadId;

	@Column(name = "project_lead_first_name")
	private String projectLeadFirstName;

	@Column(name = "project_lead_last_name")
	private String projectLeadLastName;

	@Column(name = "project_lead_email")
	private String projectLeadEmail;

	@Column(name = "opportunity_priority_id_id")
	private Long opportunityPriorityId;

	@Column(name = "opportunity_priority_name")
	private String opportunityPriorityName;

	@Column(name = "project_type_id_id")
	private Long projectTypeId;

	@Column(name = "project_type_name")
	private String projectTypeName;

	@Column(name = "allocation_record")
	private String  allocationRecord;

	@Column(name = "description")
	private String  description;
	
	@Column(name = "count")
	private Long  count;
	
	@Column(name = "project_vertical_id")
	private Long  projectVerticalId;
	
	@Column(name = "project_vertical_name")
	private String  projectVerticalName;
	
	@Column(name = "project_theme_id")
	private Long  projectThemeId;
	
	@Column(name = "project_theme_name")
	private String  projectThemeName;
	
	@Column(name = "milestone_p")
	private Boolean milestoneP;
	
	@Column(name = "task_record")
	private String task_record;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Double getPercentCompleted() {
		return percentCompleted;
	}

	public void setPercentCompleted(Double percentCompleted) {
		this.percentCompleted = percentCompleted;
	}

	public Double getProjectBudgetHours() {
		return projectBudgetHours;
	}

	public void setProjectBudgetHours(Double projectBudgetHours) {
		this.projectBudgetHours = projectBudgetHours;
	}

	public ZonedDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(ZonedDateTime endDate) {
		this.endDate = endDate;
	}

	public ZonedDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(ZonedDateTime startDate) {
		this.startDate = startDate;
	}

	public Double getReportedHoursCache() {
		return reportedHoursCache;
	}

	public void setReportedHoursCache(Double reportedHoursCache) {
		this.reportedHoursCache = reportedHoursCache;
	}

	public String getRisktype() {
		return risktype;
	}

	public void setRisktype(String risktype) {
		this.risktype = risktype;
	}

	public Double getRiskimpact() {
		return riskimpact;
	}

	public void setRiskimpact(Double riskimpact) {
		this.riskimpact = riskimpact;
	}

	public Double getRiskprobability() {
		return riskprobability;
	}

	public void setRiskprobability(Double riskprobability) {
		this.riskprobability = riskprobability;
	}

	public String getTrainingLink() {
		return trainingLink;
	}

	public void setTrainingLink(String trainingLink) {
		this.trainingLink = trainingLink;
	}

	public String getTrainingName() {
		return trainingName;
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}

	public String getTrainingDoc() {
		return trainingDoc;
	}

	public void setTrainingDoc(String trainingDoc) {
		this.trainingDoc = trainingDoc;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}



	public Long getProjectStatusId() {
		return projectStatusId;
	}

	public void setProjectStatusId(Long projectStatusId) {
		this.projectStatusId = projectStatusId;
	}

	public String getProjectStatusName() {
		return projectStatusName;
	}

	public void setProjectStatusName(String projectStatusName) {
		this.projectStatusName = projectStatusName;
	}

	public Long getProjectLeadId() {
		return projectLeadId;
	}

	public void setProjectLeadId(Long projectLeadId) {
		this.projectLeadId = projectLeadId;
	}

	public String getProjectLeadFirstName() {
		return projectLeadFirstName;
	}

	public void setProjectLeadFirstName(String projectLeadFirstName) {
		this.projectLeadFirstName = projectLeadFirstName;
	}

	public String getProjectLeadLastName() {
		return projectLeadLastName;
	}

	public void setProjectLeadLastName(String projectLeadLastName) {
		this.projectLeadLastName = projectLeadLastName;
	}

	public String getProjectLeadEmail() {
		return projectLeadEmail;
	}

	public void setProjectLeadEmail(String projectLeadEmail) {
		this.projectLeadEmail = projectLeadEmail;
	}

	public Long getOpportunityPriorityId() {
		return opportunityPriorityId;
	}

	public void setOpportunityPriorityId(Long opportunityPriorityId) {
		this.opportunityPriorityId = opportunityPriorityId;
	}

	public String getOpportunityPriorityName() {
		return opportunityPriorityName;
	}

	public void setOpportunityPriorityName(String opportunityPriorityName) {
		this.opportunityPriorityName = opportunityPriorityName;
	}

	public Long getProjectTypeId() {
		return projectTypeId;
	}

	public void setProjectTypeId(Long projectTypeId) {
		this.projectTypeId = projectTypeId;
	}

	public String getProjectTypeName() {
		return projectTypeName;
	}

	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}

	public String getAllocationRecord() {
		return allocationRecord;
	}

	public void setAllocationRecord(String allocationRecord) {
		this.allocationRecord = allocationRecord;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getProjectVerticalId() {
		return projectVerticalId;
	}

	public void setProjectVerticalId(Long projectVerticalId) {
		this.projectVerticalId = projectVerticalId;
	}

	public String getProjectVerticalName() {
		return projectVerticalName;
	}

	public void setProjectVerticalName(String projectVerticalName) {
		this.projectVerticalName = projectVerticalName;
	}

	public Long getProjectThemeId() {
		return projectThemeId;
	}

	public void setProjectThemeId(Long projectThemeId) {
		this.projectThemeId = projectThemeId;
	}

	public String getProjectThemeName() {
		return projectThemeName;
	}

	public void setProjectThemeName(String projectThemeName) {
		this.projectThemeName = projectThemeName;
	}

	public Boolean getMilestoneP() {
		return milestoneP;
	}

	public void setMilestoneP(Boolean milestoneP) {
		this.milestoneP = milestoneP;
	}

	public String getTask_record() {
		return task_record;
	}

	public void setTask_record(String task_record) {
		this.task_record = task_record;
	}

	@Override
	public String toString() {
		return "ProjectDetailsTask [id=" + id + ", projectName=" + projectName + ", percentCompleted="
				+ percentCompleted + ", projectBudgetHours=" + projectBudgetHours + ", endDate=" + endDate
				+ ", startDate=" + startDate + ", reportedHoursCache=" + reportedHoursCache + ", risktype=" + risktype
				+ ", riskimpact=" + riskimpact + ", riskprobability=" + riskprobability + ", trainingLink="
				+ trainingLink + ", trainingName=" + trainingName + ", trainingDoc=" + trainingDoc + ", sortOrder="
				+ sortOrder + ", parentId=" + parentId + ", projectStatusId=" + projectStatusId + ", projectStatusName="
				+ projectStatusName + ", projectLeadId=" + projectLeadId + ", projectLeadFirstName="
				+ projectLeadFirstName + ", projectLeadLastName=" + projectLeadLastName + ", projectLeadEmail="
				+ projectLeadEmail + ", opportunityPriorityId=" + opportunityPriorityId + ", opportunityPriorityName="
				+ opportunityPriorityName + ", projectTypeId=" + projectTypeId + ", projectTypeName=" + projectTypeName
				+ ", allocationRecord=" + allocationRecord + ", description=" + description + ", count=" + count
				+ ", projectVerticalId=" + projectVerticalId + ", projectVerticalName=" + projectVerticalName
				+ ", projectThemeId=" + projectThemeId + ", projectThemeName=" + projectThemeName + ", milestoneP="
				+ milestoneP + ", task_record=" + task_record + "]";
	}

	
	
	

}

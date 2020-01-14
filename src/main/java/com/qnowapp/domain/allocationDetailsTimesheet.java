package com.qnowapp.domain;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "allocation_details_timesheet")
public class allocationDetailsTimesheet {

	@Id
	@Column(name = "id_emp")
	private Long idemp;

	
	@Column(name = "id")
	private Long id;

	@Column(name = "im_employee_id")
	private Long imEmployeeId;

	@Column(name = "im_projects_id")
	private Long imProjectsId;

	@Column(name = "parent_id_id")
	private Long parentId;

	@Column(name = "project_name")
	private String projectName;

	@Column(name = "employee_first_name")
	private String employeeFirstName;

	@Column(name = "employee_last_name")
	private String employeeLastName;

	@Column(name = "employee_email")
	private String employeeEmail;

	@Column(name = "level")
	private Integer level;

	@Column(name = "parentid")
	private Long ppdparentid;

	@Column(name = "sort_order")
	private Integer sortOrder;

	@Column(name = "project_status_id_id")
	private Long projectStatusId;

	@Column(name = "project_status_name")
	private String projectStatusName;

	@Column(name = "milestone_p")
	private Boolean milestoneP;

	@Column(name = "itd_id")
	private Long itdId;

	@Column(name = "logday") //
	private String logday;

	@Column(name = "loghours") //
	private Double loghours;

	@Column(name = "notes") //
	private String notes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getImEmployeeId() {
		return imEmployeeId;
	}

	public void setImEmployeeId(Long imEmployeeId) {
		this.imEmployeeId = imEmployeeId;
	}

	public Long getImProjectsId() {
		return imProjectsId;
	}

	public void setImProjectsId(Long imProjectsId) {
		this.imProjectsId = imProjectsId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getEmployeeFirstName() {
		return employeeFirstName;
	}

	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}

	public String getEmployeeLastName() {
		return employeeLastName;
	}

	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}



	public Long getPpdparentid() {
		return ppdparentid;
	}

	public void setPpdparentid(Long ppdparentid) {
		this.ppdparentid = ppdparentid;
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

	public Boolean getMilestoneP() {
		return milestoneP;
	}

	public void setMilestoneP(Boolean milestoneP) {
		this.milestoneP = milestoneP;
	}

	public Long getItdId() {
		return itdId;
	}

	public void setItdId(Long itdId) {
		this.itdId = itdId;
	}

	public String getLogday() {
		return logday;
	}

	public void setLogday(String logday) {
		this.logday = logday;
	}

	public Double getLoghours() {
		return loghours;
	}

	public void setLoghours(Double loghours) {
		this.loghours = loghours;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Long getIdemp() {
		return idemp;
	}

	public void setIdemp(Long idemp) {
		this.idemp = idemp;
	}


	

}

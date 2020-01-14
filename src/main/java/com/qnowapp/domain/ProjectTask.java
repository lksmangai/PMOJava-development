package com.qnowapp.domain;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "project_task")
public class ProjectTask {
	
//    ip.project_name,
//    ip.percent_completed,
//    ip.end_date,
//    ip.start_date,
//    ip.project_status_id_id,
//    psi.name AS project_status_id_name
	
	
	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "parent_id_id")
	private Long parentId;
	
	@Column(name = "project_name")
	private String projectName;
	
	@Column(name = "percent_completed")
	private Double percentCompleted;

	
	@Column(name = "end_date")
	private ZonedDateTime endDate;
	
	@Column(name = "start_date")
	private ZonedDateTime startDate;
	
	@Column(name = "project_status_id_id")
	private Long projectStatusId;

	@Column(name = "project_status_id_name")
	private String projectStatusName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Double getPercentCompleted() {
		return percentCompleted;
	}

	public void setPercentCompleted(Double percentCompleted) {
		this.percentCompleted = percentCompleted;
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
	
	
	
}

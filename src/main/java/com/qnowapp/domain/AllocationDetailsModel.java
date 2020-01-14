package com.qnowapp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "allocation_details")
public class AllocationDetailsModel {

		@Id
		@Column(name = "id")
		private Long id;
		
		@Column(name = "im_employee_id")
		private Long imEmployee;
		
		@Column(name = "im_projects_id")
		private Long imProjects;
		
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
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

	

	

		public Long getImEmployee() {
			return imEmployee;
		}

		public void setImEmployee(Long imEmployee) {
			this.imEmployee = imEmployee;
		}

		public Long getImProjects() {
			return imProjects;
		}

		public void setImProjects(Long imProjects) {
			this.imProjects = imProjects;
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
			this.employeeEmail = employeeEmail;
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


}

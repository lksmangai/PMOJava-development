package com.qnowapp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "project_managers")
public class ProjectManager {

	@Id
	@Column(name = "project_lead_id_id")
	private Long id;
	
	
	@Column(name = "project_lead_first_name")
	private String project_lead_first_name;
	
	@Column(name = "project_lead_last_name")
	private String project_lead_last_name;
	
	@Column(name = "project_lead_email")
	private String project_lead_email;
	
	@Column(name = "count")
	private Integer count;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProject_lead_first_name() {
		return project_lead_first_name;
	}

	public void setProject_lead_first_name(String project_lead_first_name) {
		this.project_lead_first_name = project_lead_first_name;
	}

	public String getProject_lead_last_name() {
		return project_lead_last_name;
	}

	public void setProject_lead_last_name(String project_lead_last_name) {
		this.project_lead_last_name = project_lead_last_name;
	}

	public String getProject_lead_email() {
		return project_lead_email;
	}

	public void setProject_lead_email(String project_lead_email) {
		this.project_lead_email = project_lead_email;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}


	
	 

	 

}

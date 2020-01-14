package com.qnowapp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "project_parent_details")
public class ProjectParentDetails {

	
	@Id
	@Column(name = "projectid")
	private Long projectid;
	

	@Column(name = "parentid")
	private Long parentId;
	

	@Column(name = "level")
	private Integer level;


	public Long getProjectid() {
		return projectid;
	}


	public void setProjectid(Long projectid) {
		this.projectid = projectid;
	}


	public Long getParentId() {
		return parentId;
	}


	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}


	public Integer getLevel() {
		return level;
	}


	public void setLevel(Integer level) {
		this.level = level;
	}




	
	
	



	
}





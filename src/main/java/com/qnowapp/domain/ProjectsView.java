package com.qnowapp.domain;


import java.io.IOException;


import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qnowapp.service.util.StringJsonUserType;

@Entity
@TypeDef(name = "StringJsonUserType", typeClass = StringJsonUserType.class)
public class ProjectsView {
	
	@Id
	private Long projectid;
	
	private String projects_name;
	

	
	@Type(type = "StringJsonUserType")
	private Object taskrecord;



	public Long getProjectid() {
		return projectid;
	}



	public void setProjectid(Long projectid) {
		this.projectid = projectid;
	}



	public String getProjects_name() {
		return projects_name;
	}



	public void setProjects_name(String projects_name) {
		this.projects_name = projects_name;
	}



	public Object getTaskrecord() {
		try {
			if(taskrecord!=null)
			{
				return new ObjectMapper().readValue(taskrecord.toString(), Object.class);
			}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



	public void setTaskrecord(Object taskrecord) {
		this.taskrecord = taskrecord;
	}

	


	
	
}

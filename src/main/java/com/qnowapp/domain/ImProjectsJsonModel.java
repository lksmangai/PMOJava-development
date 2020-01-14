/**
 * 
 */
package com.qnowapp.domain;

import java.io.IOException;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qnowapp.service.util.StringJsonUserType;

/**
 * @author komal.patalpure
 *
 */

@Entity
@TypeDef(name = "StringJsonUserType", typeClass = StringJsonUserType.class)
public class ImProjectsJsonModel {
	
	@Id
	private Long id;
	
	@Type(type = "StringJsonUserType")
	private Object improjectsrecord;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Object getImprojectsrecord() {
		try {
			if(improjectsrecord!=null)
			{
				return new ObjectMapper().readValue(improjectsrecord.toString(), Object.class);
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

	public void setImprojectsrecord(Object improjectsrecord) {
		this.improjectsrecord = improjectsrecord;
	}
	
	
}

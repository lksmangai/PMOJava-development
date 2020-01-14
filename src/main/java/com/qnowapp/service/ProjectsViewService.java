package com.qnowapp.service;


import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qnowapp.domain.ProjectsView;
import com.qnowapp.domain.ImProjectsJsonModel;
import com.qnowapp.domain.ProjectsView;


@Service
public class ProjectsViewService {
	

	
	@Autowired
	EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	
	
	@Transactional
	public ProjectsView ImProjectsIdJson(long userid) {
		ProjectsView lst = null;
		try {
			StoredProcedureQuery query = getEntityManager().createStoredProcedureQuery("improjects_temp_funwithoutid",ProjectsView.class);
			query.registerStoredProcedureParameter("userid", Long.class, ParameterMode.IN);
			query.setParameter("userid", userid);
			
			lst=(ProjectsView) query.getSingleResult();

		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {			
			getEntityManager().close();
		}
		
		
		return lst;
		
	}
	
	@Transactional
	public ImProjectsJsonModel ImProjectsDetailsJson() {
		ImProjectsJsonModel lst = null;
		try {
			StoredProcedureQuery query = getEntityManager().createStoredProcedureQuery("improjects_temp",ImProjectsJsonModel.class);
			lst=(ImProjectsJsonModel) query.getSingleResult();

		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {			
			getEntityManager().close();
		}
		
		
		return lst;
		
	}
	

}

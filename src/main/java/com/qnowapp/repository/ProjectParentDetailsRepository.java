package com.qnowapp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.qnowapp.domain.ProjectParentDetails;
import com.qnowapp.domain.ProjectTask;

public interface ProjectParentDetailsRepository extends CrudRepository<ProjectParentDetails, Long>{
	List<ProjectParentDetails>  findByparentIdIn(List<Long> parentid);

	List<ProjectParentDetails> findByparentId(Long id);
	

}

package com.qnowapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.qnowapp.domain.AllocationDetailsModel;
import com.qnowapp.domain.ImEmployee;
import com.qnowapp.domain.ImProjects;


/**
 * Spring Data  repository for the ImProjects entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImProjectsRepository extends JpaRepository<ImProjects, Long>, JpaSpecificationExecutor<ImProjects> {
	


public List<ImProjects> findByparentId(ImProjects parentId );

public List<AllocationDetailsModel> findByparentIdAndProjectLeadId(ImProjects parentId , ImEmployee projectLeadId);

public List<ImProjects> findByidIn(List<Long> projectid);

public ImProjects findByid(Long id); 

}

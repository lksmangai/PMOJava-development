package com.qnowapp.repository;

import com.qnowapp.domain.ImEmployee;
import com.qnowapp.domain.ImProjects;
import com.qnowapp.domain.ProjectAllocation;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProjectAllocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectAllocationRepository extends JpaRepository<ProjectAllocation, Long>, JpaSpecificationExecutor<ProjectAllocation> {

	

	List<ProjectAllocation> findByimProjects(ImProjects imProjects);

	ProjectAllocation findByimProjectsAndImEmployee(ImProjects imProjects , ImEmployee imEmployee);
	

}

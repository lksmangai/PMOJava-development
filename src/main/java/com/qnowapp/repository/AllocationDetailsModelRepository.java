package com.qnowapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.qnowapp.domain.AllocationDetailsModel;
import com.qnowapp.domain.ImEmployee;
import com.qnowapp.domain.ImProjects;
import com.qnowapp.domain.ProjectDetailsModel;
import com.qnowapp.domain.allocationDetailsTimesheet;


public interface AllocationDetailsModelRepository extends JpaRepository<AllocationDetailsModel, Long>  {
	

	List<AllocationDetailsModel> findByid(Long id);
	
	List<AllocationDetailsModel> findByimProjects(Long imProjects);
	
	List<AllocationDetailsModel> findByimEmployee(Long imEmployee);
	
	List<AllocationDetailsModel> findByimProjectsAndImEmployee(Long imProjects , Long imEmployee);
	List<AllocationDetailsModel> findByparentIdAndImEmployee(Long parentId , Long imEmployee);
	
	List<AllocationDetailsModel> findByppdparentid(Long ppdparentid);
	List<AllocationDetailsModel> findByppdparentidAndImEmployee(Long ppdparentid , Long imEmployee);

	List<AllocationDetailsModel> findByimProjectsAndImEmployee(ImProjects imProjects2, ImEmployee imEmployee2);

	
}

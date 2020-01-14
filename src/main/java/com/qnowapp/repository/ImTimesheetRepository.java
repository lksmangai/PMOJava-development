package com.qnowapp.repository;

import com.qnowapp.domain.ImEmployee;
import com.qnowapp.domain.ImProjects;
import com.qnowapp.domain.ImTimesheet;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ImTimesheet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImTimesheetRepository extends JpaRepository<ImTimesheet, Long>, JpaSpecificationExecutor<ImTimesheet> {

	List<ImTimesheet> findByimProjects(ImProjects imProjects);

	ImTimesheet findByimProjectsAndImEmployeeAndLogday(ImProjects imProjects, ImEmployee imEmployee,
			String logday);
	ImTimesheet findByid(Long id);

}


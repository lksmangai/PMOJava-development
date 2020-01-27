package com.qnowapp.repository;

import com.qnowapp.domain.ProjectAllocation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProjectAllocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectAllocationRepository extends JpaRepository<ProjectAllocation, Long>, JpaSpecificationExecutor<ProjectAllocation> {

}

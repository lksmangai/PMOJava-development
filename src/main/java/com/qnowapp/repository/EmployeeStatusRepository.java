package com.qnowapp.repository;

import com.qnowapp.domain.EmployeeStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EmployeeStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeStatusRepository extends JpaRepository<EmployeeStatus, Long>, JpaSpecificationExecutor<EmployeeStatus> {

}

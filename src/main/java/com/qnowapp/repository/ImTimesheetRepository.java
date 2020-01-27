package com.qnowapp.repository;

import com.qnowapp.domain.ImTimesheet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ImTimesheet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImTimesheetRepository extends JpaRepository<ImTimesheet, Long>, JpaSpecificationExecutor<ImTimesheet> {

}

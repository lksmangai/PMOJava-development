package com.qnowapp.repository;

import com.qnowapp.domain.BacklogPractice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BacklogPractice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BacklogPracticeRepository extends JpaRepository<BacklogPractice, Long>, JpaSpecificationExecutor<BacklogPractice> {

}

package com.qnowapp.repository;

import com.qnowapp.domain.OpportunityPriorityId;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OpportunityPriorityId entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpportunityPriorityIdRepository extends JpaRepository<OpportunityPriorityId, Long>, JpaSpecificationExecutor<OpportunityPriorityId> {

}

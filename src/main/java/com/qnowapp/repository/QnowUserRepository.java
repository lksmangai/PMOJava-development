package com.qnowapp.repository;

import com.qnowapp.domain.QnowUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the QnowUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QnowUserRepository extends JpaRepository<QnowUser, Long>, JpaSpecificationExecutor<QnowUser> {

}

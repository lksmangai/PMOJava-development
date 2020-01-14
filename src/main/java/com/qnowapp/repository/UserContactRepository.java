package com.qnowapp.repository;

import com.qnowapp.domain.UserContact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the UserContact entity.
 */
@Repository
public interface UserContactRepository extends JpaRepository<UserContact, Long>, JpaSpecificationExecutor<UserContact> {

    @Query(value = "select distinct userContact from UserContact userContact left join fetch userContact.groupMembers",
        countQuery = "select count(distinct userContact) from UserContact userContact")
    Page<UserContact> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct userContact from UserContact userContact left join fetch userContact.groupMembers")
    List<UserContact> findAllWithEagerRelationships();

    @Query("select userContact from UserContact userContact left join fetch userContact.groupMembers where userContact.id =:id")
    Optional<UserContact> findOneWithEagerRelationships(@Param("id") Long id);

}

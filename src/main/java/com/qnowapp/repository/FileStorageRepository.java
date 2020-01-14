package com.qnowapp.repository;

import com.qnowapp.domain.FileStorage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FileStorage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, Long>, JpaSpecificationExecutor<FileStorage> {

}

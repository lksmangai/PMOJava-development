package com.qnowapp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.qnowapp.domain.ProjectDetailsModel;
import com.qnowapp.domain.ProjectDetailsTask;
import com.qnowapp.domain.TimesheetDetailsModel;

@Repository
public interface ProjectDetailsModelRepository extends CrudRepository<ProjectDetailsModel, Long> {

	
	List<ProjectDetailsModel> findByprojectName(String name);
	
	//List<ProjectDetailsModel> findByidAndParentid(Long id , Long parentid);
	List<ProjectDetailsModel>  findByparentId(Long parentId);
	List<ProjectDetailsModel> findByprojectStatusNameIn(List<String> projectStatusNames);
	
	List<ProjectDetailsModel> findByprojectStatusNameInAndParentId(List<String> projectStatusNames ,Long parentId );
	List<ProjectDetailsModel> findByidIn(List<Long> ids );

	
	List<ProjectDetailsModel> findByprojectStatusNameInAndIdIn(List<String> projectStatusNames ,List<Long> ids );
	List<ProjectDetailsModel> findByprojectStatusNameInAndParentIdAndIdIn(List<String> projectStatusNames ,Long parentId ,List<Long> ids );
	List<ProjectDetailsModel> findByprojectLeadIdAndId(Long imEmployeeId, Long id);

	
	
}

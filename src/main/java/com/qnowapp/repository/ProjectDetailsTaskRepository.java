package com.qnowapp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.qnowapp.domain.ProjectDetailsTask;

public interface ProjectDetailsTaskRepository extends CrudRepository<ProjectDetailsTask, Long> {

	
	List<ProjectDetailsTask> findByprojectName(String name);
	
	//List<ProjectDetailsModel> findByidAndParentid(Long id , Long parentid);
	//List<ProjectDetailsTask>  findByparentid(Long parentid);
	List<ProjectDetailsTask> findByprojectStatusNameIn(List<String> projectStatusNames);
	List<ProjectDetailsTask> findByprojectStatusNameInAndParentId(List<String> projectStatusNames ,Long parentId );
	List<ProjectDetailsTask> findByidIn(List<Long> ids );
	List<ProjectDetailsTask> findByprojectStatusNameInAndId(List<String> projectStatusNames ,List<Long> ids );
	List<ProjectDetailsTask> findByprojectStatusNameInAndParentIdAndIdIn(List<String> projectStatusNames ,Long parentId ,List<Long> ids );
	List<ProjectDetailsTask> findByprojectLeadIdAndId(Long imEmployeeId, Long id);

	List<ProjectDetailsTask> findByparentId(Long parentId);
}



package com.qnowapp.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.qnowapp.domain.AllocationDetailsModel;
import com.qnowapp.domain.ProjectDetailsModel;
import com.qnowapp.domain.TimesheetDetailsModel;

public interface TimesheetDetailsModelRepository extends CrudRepository<TimesheetDetailsModel, Long> {
	List<TimesheetDetailsModel> findByid(Long id);

	List<TimesheetDetailsModel> findBylogday(String logday);

	List<TimesheetDetailsModel> findByimEmployeeId(Long imEmployeeId);

	List<TimesheetDetailsModel> findByimProjectsId(Long imProjectsId);
	List<TimesheetDetailsModel> findBylogdayAndImProjectsId(String logday, Long imProjectsId);
	
	List<TimesheetDetailsModel> findBylogdayAndImEmployeeId(String logday, Long imEmployeeId);
	
	List<TimesheetDetailsModel> findByimEmployeeIdAndImProjectsId(Long imEmployeeId, Long imProjectsId);
	
	List<TimesheetDetailsModel> findBylogdayAndImEmployeeIdAndImProjectsId(String logday ,Long imEmployeeId, Long imProjectsId);

	List<TimesheetDetailsModel> findByimProjectsIdIn(List<Long> projectid);

	//List<TimesheetDetailsModel> findByloghoursIn( Double loghours );
	
}

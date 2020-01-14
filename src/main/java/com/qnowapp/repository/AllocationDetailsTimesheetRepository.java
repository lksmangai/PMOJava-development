package com.qnowapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qnowapp.domain.AllocationDetailsModel;
import com.qnowapp.domain.TimesheetDetailsModel;
import com.qnowapp.domain.allocationDetailsTimesheet;

public interface AllocationDetailsTimesheetRepository extends JpaRepository<allocationDetailsTimesheet, Long>  {
	
List<allocationDetailsTimesheet> findByid(Long id);
	

	List<allocationDetailsTimesheet> findByimProjectsIdAndImEmployeeId(Long imProjectsId , Long imEmployeeId);
	List<allocationDetailsTimesheet> findByparentIdAndImEmployeeId(Long parentId , Long imEmployeeId);
	

	List<allocationDetailsTimesheet> findBylogday(String logday);

	List<allocationDetailsTimesheet> findByimEmployeeId(Long imEmployeeId);

	List<allocationDetailsTimesheet> findByimProjectsId(Long imProjectsId);
	List<allocationDetailsTimesheet> findByppdparentid(Long ppdparentid);
	
	List<allocationDetailsTimesheet> findByppdparentidAndImEmployeeId(Long ppdparentid , Long imEmployeeId);
	List<allocationDetailsTimesheet> findBylogdayAndImProjectsId(String logday, Long imProjectsId);
	
	List<allocationDetailsTimesheet> findBylogdayAndImEmployeeId(String logday, Long imEmployeeId);
	
	List<allocationDetailsTimesheet> findByimEmployeeIdAndImProjectsId(Long imEmployeeId, Long imProjectsId);
	
	List<allocationDetailsTimesheet> findBylogdayAndImEmployeeIdAndImProjectsId(String logday ,Long imEmployeeId, Long imProjectsId);

	List<allocationDetailsTimesheet> findBylogdayAndImEmployeeIdAndPpdparentid(String logday ,Long imEmployeeId, Long ppdparentid);

	//List<TimesheetDetailsModel> findByloghoursIn( Double loghours );


}

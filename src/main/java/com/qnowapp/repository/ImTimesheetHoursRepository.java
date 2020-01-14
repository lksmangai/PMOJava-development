package com.qnowapp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.qnowapp.domain.ImTimesheetHours;

public interface ImTimesheetHoursRepository extends JpaRepository<ImTimesheetHours, Long> {
	
	List<ImTimesheetHours> findBylogdayAndImEmployeeId(String logday , Long imEmployeeId);

	List<ImTimesheetHours> findByimEmployeeId(Long imEmployeeId);

	List<ImTimesheetHours> findAllByimEmployeeIdAndLogdayBetween(Long imEmployeeId , String startday , String endday);
	List<ImTimesheetHours> findAllBylogdayLessThanEqualAndLogdayGreaterThanEqual(String startday , String endday);
	List<ImTimesheetHours> findBylogdayLessThanEqualAndLogdayGreaterThanEqual(String startday , String endday);
	List<ImTimesheetHours> findByimEmployeeIdAndLogdayLessThanEqualAndLogdayGreaterThanEqual(Long imEmployeeId  , String endday ,String startday );

	List<ImTimesheetHours> findByLogdayLessThanEqualAndLogdayGreaterThanEqual(String endday, String startday);

}

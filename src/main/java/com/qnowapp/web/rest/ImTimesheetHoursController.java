package com.qnowapp.web.rest;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qnowapp.domain.ImTimesheetHours;
import com.qnowapp.domain.ListOfId;
import com.qnowapp.domain.ProjectDetailsTask;
import com.qnowapp.domain.ProjectParentDetails;
import com.qnowapp.repository.ImTimesheetHoursRepository;
import com.qnowapp.repository.ProjectParentDetailsRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ImTimesheetHoursController {

	@Autowired
	ImTimesheetHoursRepository imTimesheetHoursRepository;


	@Autowired
	ProjectParentDetailsRepository ProjectParentDetailsRepository;
	
	@CrossOrigin
	@GetMapping("/imTimesheetHours")
	public List<ImTimesheetHours> getAllTimesheet() {
		List<ImTimesheetHours> list = (List<ImTimesheetHours>) imTimesheetHoursRepository.findAll();

		return list;
	}

	@CrossOrigin
	@GetMapping("/imTimesheetHours/{logday}/{imEmployeeId}")
	public List<ImTimesheetHours> getfindById(@PathVariable("logday") String logday,
			@PathVariable("imEmployeeId") Long imEmployeeId) {
		List<ImTimesheetHours> list = (List<ImTimesheetHours>) imTimesheetHoursRepository
				.findBylogdayAndImEmployeeId(logday, imEmployeeId);

		return list;
	}

	@CrossOrigin
	@GetMapping("/imTimesheetHours/{imEmployeeId}")
	public List<ImTimesheetHours> getfindByimEmployeeId(@PathVariable("imEmployeeId") Long imEmployeeId) {
		List<ImTimesheetHours> list = (List<ImTimesheetHours>) imTimesheetHoursRepository
				.findByimEmployeeId(imEmployeeId);

		return list;
	}

	@CrossOrigin
	@GetMapping("/imTimesheetHoursimEmployeeId/{imEmployeeId}/{endday}/{startday}")
	public List<ImTimesheetHours> getfindByimEmployeeIdLogday22(@PathVariable("imEmployeeId") Long imEmployeeId,
			@PathVariable("endday") String endday, @PathVariable("startday") String startday) {
		List<ImTimesheetHours> list = (List<ImTimesheetHours>) imTimesheetHoursRepository
				.findByimEmployeeIdAndLogdayLessThanEqualAndLogdayGreaterThanEqual(imEmployeeId, endday, startday);

		return list;
	}

	@CrossOrigin
	@GetMapping("/imTimesheetHoursimEmployeeIdSum/{imEmployeeId}/{endday}/{startday}")
	public double getfindByimEmployeeIdLogdaybycount(@PathVariable("imEmployeeId") Long imEmployeeId,
			@PathVariable("endday") String endday, @PathVariable("startday") String startday) {
		List<ImTimesheetHours> list = (List<ImTimesheetHours>) imTimesheetHoursRepository
				.findByimEmployeeIdAndLogdayLessThanEqualAndLogdayGreaterThanEqual(imEmployeeId, endday, startday);
		double sum = 0.0;
		if(list!=null) {		
		for (ImTimesheetHours imTimesheetHours : list) {
			sum = sum + imTimesheetHours.getSum();

		}
	}

		return sum;
	}

	@CrossOrigin
	@GetMapping("/imTimesheetHoursByDate/{endday}/{startday}")
	public List<ImTimesheetHours> getfindByimEmployeeIdLogday22(@PathVariable("endday") String endday,
			@PathVariable("startday") String startday) {
		List<ImTimesheetHours> list = (List<ImTimesheetHours>) imTimesheetHoursRepository
				.findByLogdayLessThanEqualAndLogdayGreaterThanEqual(endday, startday);

		return list;
	}

	

}

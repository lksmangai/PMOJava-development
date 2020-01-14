package com.qnowapp.web.rest;

import java.util.ArrayList;
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

import com.qnowapp.domain.ListOfId;
import com.qnowapp.domain.ProjectParentDetails;
import com.qnowapp.domain.TimesheetDetailsModel;
import com.qnowapp.repository.ProjectParentDetailsRepository;
import com.qnowapp.repository.TimesheetDetailsModelRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class TimesheetDetailsControllers {
	
	@Autowired
	TimesheetDetailsModelRepository timesheetDetailsModelRepository;
	

	@Autowired
	ProjectParentDetailsRepository ProjectParentDetailsRepository;
	
	
	@CrossOrigin
	@GetMapping("/timesheet")
	
	public List<TimesheetDetailsModel> getAllTimesheet()
	{
		List<TimesheetDetailsModel> list= (List<TimesheetDetailsModel>) timesheetDetailsModelRepository.findAll();

		return  list ;
	}
	
	@CrossOrigin
	@GetMapping("/timesheet/{id}")
	
	public List<TimesheetDetailsModel> getfindById(@PathVariable("id") Long id)
	{
		List<TimesheetDetailsModel> list= (List<TimesheetDetailsModel>) timesheetDetailsModelRepository.findByid(id);

		return  list ;
	}

	@CrossOrigin
	@GetMapping("/timesheetProjects/{imProjectsId}")
	
	public List<TimesheetDetailsModel> getfindByimProjectsId(@PathVariable("imProjectsId") Long imProjectsId)
	{
		List<TimesheetDetailsModel> list= (List<TimesheetDetailsModel>) timesheetDetailsModelRepository.findByimProjectsId(imProjectsId);

		return  list ;
	}
	
	@CrossOrigin
	@GetMapping("/timesheetEmployees/{imEmployeeId}")
	
	public List<TimesheetDetailsModel> getfindByimEmployeeId(@PathVariable("imEmployeeId") Long imEmployeeId)
	{
		List<TimesheetDetailsModel> list= (List<TimesheetDetailsModel>) timesheetDetailsModelRepository.findByimEmployeeId(imEmployeeId);

		return  list ;
	}
	
	@CrossOrigin
	@GetMapping("/timesheetogdate/{logday}")
	
	public List<TimesheetDetailsModel> getfindBylogdate(@PathVariable("logday") String logday)
	{
		List<TimesheetDetailsModel> list= (List<TimesheetDetailsModel>) timesheetDetailsModelRepository.findBylogday(logday);

		return  list ;
	}
	@CrossOrigin
	@GetMapping("/timesheetDateEmployees/{logday}/{imEmployeeId}")
	
	public List<TimesheetDetailsModel> getfindBylogdateAndImEmployeeId(@PathVariable("logday") String logday,@PathVariable("imEmployeeId") Long imEmployeeId )
	{
		List<TimesheetDetailsModel> list= (List<TimesheetDetailsModel>) timesheetDetailsModelRepository.findBylogdayAndImEmployeeId(logday, imEmployeeId);

		return  list ;
	}
	@CrossOrigin
	@GetMapping("/timesheetDateProjects/{logday}/{imProjectsId}")
	
	public List<TimesheetDetailsModel> getfindBylogdateAndImProjectsId(@PathVariable("logday") String logday, @PathVariable("imProjectsId") Long imProjectsId)
	{
		List<TimesheetDetailsModel> list= (List<TimesheetDetailsModel>) timesheetDetailsModelRepository.findBylogdayAndImProjectsId(logday, imProjectsId);

		return  list ;
	}

	@CrossOrigin
	@GetMapping("/timesheetEmployeeProjects/{imEmployeeId}/{imProjectsId}")
	
	public List<TimesheetDetailsModel> getfindBylogdateAndImProjectsId(@PathVariable("imEmployeeId") Long imEmployeeId, @PathVariable("imProjectsId") Long imProjectsId)
	{
		List<TimesheetDetailsModel> list= (List<TimesheetDetailsModel>) timesheetDetailsModelRepository.findByimEmployeeIdAndImProjectsId(imEmployeeId, imProjectsId);

		return  list ;
	}
	@CrossOrigin
	@GetMapping("/timesheetFilter/{logday}/{imEmployeeId}/{imProjectsId}")
	
	public List<TimesheetDetailsModel> getfindBylogdate(@PathVariable("logdate") String logday,@PathVariable("imEmployeeId") Long imEmployeeId , @PathVariable("imProjectsId") Long imProjectsId)
	{
		List<TimesheetDetailsModel> list= (List<TimesheetDetailsModel>) timesheetDetailsModelRepository.findBylogdayAndImEmployeeIdAndImProjectsId(logday, imEmployeeId, imProjectsId);

		return  list ;
	}
	
	@CrossOrigin
	@PostMapping("/ProjectDetailsTaskbyParentinTimesheetDetailsModel")
	public ResponseEntity<List<TimesheetDetailsModel>> getAllProjectDetailsTaskbyParent(@RequestBody ListOfId parentid) {

		List<ProjectParentDetails> resultnew = (List<ProjectParentDetails>) ProjectParentDetailsRepository
				.findByparentIdIn(parentid.getId());

		List<Long> projectid = new ArrayList<Long>();

		resultnew.forEach(item -> {

			Long subtaskid = item.getProjectid();

			if (subtaskid != null) {

				projectid.add(subtaskid);

			}

		});

		List<TimesheetDetailsModel> result = (List<TimesheetDetailsModel>) timesheetDetailsModelRepository.findByimProjectsIdIn(projectid);

		if (result != null) {
			return new ResponseEntity<List<TimesheetDetailsModel>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<TimesheetDetailsModel>>(result, HttpStatus.NO_CONTENT);

		}

	}
	
	
}

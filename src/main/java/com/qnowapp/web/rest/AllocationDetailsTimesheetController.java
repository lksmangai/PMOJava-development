package com.qnowapp.web.rest;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qnowapp.domain.allocationDetailsTimesheet;
import com.qnowapp.domain.ProjectDetailsModel;

import com.qnowapp.repository.AllocationDetailsTimesheetRepository;

import com.qnowapp.repository.ProjectDetailsModelRepository;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class AllocationDetailsTimesheetController {
	@Autowired
	AllocationDetailsTimesheetRepository allocationDetailsTimesheetRepository;
	@Autowired
	ProjectDetailsModelRepository projectDetailsModelRepository;

	@CrossOrigin
	@GetMapping("/allocationDetailsTimesheetallocation")
	public List<allocationDetailsTimesheet> getAllTimesheet() {
		List<allocationDetailsTimesheet> list = (List<allocationDetailsTimesheet>) allocationDetailsTimesheetRepository
				.findAll();

		return list;
	}

	@CrossOrigin
	@GetMapping("/allocationDetailsTimesheetallocation/{id}")
	public List<allocationDetailsTimesheet> getfindById(@PathVariable("id") Long id) {
		List<allocationDetailsTimesheet> list = (List<allocationDetailsTimesheet>) allocationDetailsTimesheetRepository
				.findByid(id);

		return list;
	}

	@CrossOrigin
	@GetMapping("/allocationDetailsTimesheetallocationProjects/{imProjeimProjectsIdcts}")
	public List<allocationDetailsTimesheet> getfindByimProjects(@PathVariable("imProjectsId") Long imProjectsId) {
		List<allocationDetailsTimesheet> list = (List<allocationDetailsTimesheet>) allocationDetailsTimesheetRepository
				.findByimProjectsId(imProjectsId);

		return list;
	}

	
	@CrossOrigin
	@GetMapping("/allocationDetailsTimesheetPPDparentid/{ppdparentid}")
	public List<allocationDetailsTimesheet> getfindByPPDparentid(@PathVariable("ppdparentid") Long ppdparentid) {
		List<allocationDetailsTimesheet> list = (List<allocationDetailsTimesheet>) allocationDetailsTimesheetRepository
				.findByppdparentid(ppdparentid);

		return list;
	}
	
	
	@CrossOrigin
	@GetMapping("/allocationDetailsTimesheetallocationEmployees/{imEmployeeId}")
	public List<allocationDetailsTimesheet> getfindByimEmployee(@PathVariable("imEmployeeId") Long imEmployeeId) {
		List<allocationDetailsTimesheet> list = (List<allocationDetailsTimesheet>) allocationDetailsTimesheetRepository
				.findByimEmployeeId(imEmployeeId);
		return list;
	}

	@CrossOrigin
	@GetMapping("/allocationDetailsTimesheetallocationparent")
	public ResponseEntity<List<allocationDetailsTimesheet>> getAllTimesheetparent() {
		List<allocationDetailsTimesheet> list = (List<allocationDetailsTimesheet>) allocationDetailsTimesheetRepository
				.findAll();

		List<allocationDetailsTimesheet> result = list.stream().filter(d -> d.getParentId() == null)
				.collect(Collectors.toList());

		if(result!=null)
		{
			return new ResponseEntity<List<allocationDetailsTimesheet>>(result, HttpStatus.OK);
		}else {
			return new ResponseEntity<List<allocationDetailsTimesheet>>(result, HttpStatus.NO_CONTENT);
		
	}
	}

	@CrossOrigin
	@GetMapping("/allocationDetailsTimesheetallocationparent/{id}")
	public ResponseEntity<List<allocationDetailsTimesheet>> getfindByIdparent(@PathVariable("id") Long id) {
		List<allocationDetailsTimesheet> list = (List<allocationDetailsTimesheet>) allocationDetailsTimesheetRepository
				.findByid(id);
		List<allocationDetailsTimesheet> result = list.stream().filter(d -> d.getParentId() == null)
				.collect(Collectors.toList());

		if(result!=null)
		{
			return new ResponseEntity<List<allocationDetailsTimesheet>>(result, HttpStatus.OK);
		}else {
			return new ResponseEntity<List<allocationDetailsTimesheet>>(result, HttpStatus.NO_CONTENT);
		
	}
		
	}

	@CrossOrigin
	@GetMapping("/allocationDetailsTimesheetallocationProjectsparent/{imProjectsId}")
	public ResponseEntity<List<allocationDetailsTimesheet>> getfindByimProjectsparent(@PathVariable("imProjectsId") Long imProjectsId) {
		List<allocationDetailsTimesheet> list = (List<allocationDetailsTimesheet>) allocationDetailsTimesheetRepository
				.findByimProjectsId(imProjectsId);

		List<allocationDetailsTimesheet> result = list.stream().filter(d -> d.getParentId() == null)
				.collect(Collectors.toList());
	
		if(result!=null)
		{
			return new ResponseEntity<List<allocationDetailsTimesheet>>(result, HttpStatus.OK);
		}else {
			return new ResponseEntity<List<allocationDetailsTimesheet>>(result, HttpStatus.NO_CONTENT);
		
	}
	}

	@CrossOrigin
	@GetMapping("/allocationDetailsTimesheetallocationEmployeesparent/{imEmployeeId}")
	public List<allocationDetailsTimesheet> getfindByimEmployeeparent(@PathVariable("imEmployeeId") Long imEmployeeId) {
		List<allocationDetailsTimesheet> list = (List<allocationDetailsTimesheet>) allocationDetailsTimesheetRepository
				.findByimEmployeeId(imEmployeeId);
		List<allocationDetailsTimesheet> result = list.stream().filter(d -> d.getParentId() == null)
				.collect(Collectors.toList());
		return result;
	}

	@CrossOrigin
	@GetMapping("/allocationDetailsTimesheetEmployeesparentprojects/{imEmployeeId}")
	public ResponseEntity<List<ProjectDetailsModel>> getfindByimEmployeeparentprojects(
			@PathVariable("imEmployeeId") Long imEmployeeId) {
		List<allocationDetailsTimesheet> list = (List<allocationDetailsTimesheet>) allocationDetailsTimesheetRepository
				.findByimEmployeeId(imEmployeeId);
		List<allocationDetailsTimesheet> result = list.stream().filter(d -> d.getParentId() == null)
				.collect(Collectors.toList());
		List<Long> projectid = new ArrayList<Long>();
		result.forEach(item -> {

			Long projectID = item.getImProjectsId();

			if (projectID != null) {
				projectid.add(projectID);

			}
		}

		);

		List<ProjectDetailsModel> resultnew = (List<ProjectDetailsModel>) projectDetailsModelRepository
				.findByidIn(projectid);

		if(resultnew!=null)
		{
			return new ResponseEntity<List<ProjectDetailsModel>>(resultnew, HttpStatus.OK);
		}else {
			return new ResponseEntity<List<ProjectDetailsModel>>(resultnew, HttpStatus.NO_CONTENT);
		
	}
	}

	@CrossOrigin
	@GetMapping("/allocationDetailsTimesheetEmployeesprojectsparents/{parentId}/{imEmployeeId}")
	public ResponseEntity<List<ProjectDetailsModel>> getfindByimEmployeeparentprojects1(@PathVariable("parentId") Long parentId,
			@PathVariable("imEmployeeId") Long imEmployeeId) {
		List<allocationDetailsTimesheet> list = (List<allocationDetailsTimesheet>) allocationDetailsTimesheetRepository
				.findByparentIdAndImEmployeeId(parentId, imEmployeeId);
		List<allocationDetailsTimesheet> result = list;
		List<Long> projectid = new ArrayList<Long>();
		result.forEach(item -> {

			Long projectID = item.getImProjectsId();

			if (projectID != null) {
				projectid.add(projectID);

			}
		}

		);

		List<ProjectDetailsModel> resultnew = (List<ProjectDetailsModel>) projectDetailsModelRepository
				.findByidIn(projectid);


		if(resultnew!=null)
		{
			return new ResponseEntity<List<ProjectDetailsModel>>(resultnew, HttpStatus.OK);
		}else {
			return new ResponseEntity<List<ProjectDetailsModel>>(resultnew, HttpStatus.NO_CONTENT);
		
	}
	}

	@CrossOrigin
	@GetMapping("/allocationDetailsTimesheetEmployeesprojectsparentsallocation/{parentId}/{imEmployeeId}")
	public List<allocationDetailsTimesheet> getfindByimEmployeeparentprojects2(@PathVariable("parentId") Long parentId,
			@PathVariable("imEmployeeId") Long imEmployeeId) {
		List<allocationDetailsTimesheet> list = (List<allocationDetailsTimesheet>) allocationDetailsTimesheetRepository
				.findByparentIdAndImEmployeeId(parentId, imEmployeeId);

		return list;
	}

	
	@CrossOrigin
	@GetMapping("/allocationDetailsTimesheetEmployeesprojectsPPDparentid/{ppdparentid}/{imEmployeeId}")
	public List<allocationDetailsTimesheet> getfindByimEmployeeparentprojects3(@PathVariable("ppdparentid") Long ppdparentid,
			@PathVariable("imEmployeeId") Long imEmployeeId) {
		List<allocationDetailsTimesheet> list = (List<allocationDetailsTimesheet>) allocationDetailsTimesheetRepository
				.findByppdparentidAndImEmployeeId(ppdparentid, imEmployeeId);

		return list;
	}

	
	@CrossOrigin
	@GetMapping("/allocationDetailsTimesheetFilter/{imProjectsId}/{imEmployeeId}")
	public List<allocationDetailsTimesheet> getfindByimProjects(@PathVariable("imProjectsId") Long imProjectsId,
			@PathVariable("imEmployeeId") Long imEmployeeId) {
		List<allocationDetailsTimesheet> list = (List<allocationDetailsTimesheet>) allocationDetailsTimesheetRepository
				.findByimProjectsIdAndImEmployeeId(imProjectsId, imEmployeeId);

		return list;
	}

	@CrossOrigin
	@GetMapping("/allocationDetailsTimesheetEmployees/{imEmployeeId}")

	public List<allocationDetailsTimesheet> getfindByimEmployeeId(@PathVariable("imEmployeeId") Long imEmployeeId) {
		List<allocationDetailsTimesheet> list = (List<allocationDetailsTimesheet>) allocationDetailsTimesheetRepository
				.findByimEmployeeId(imEmployeeId);

		return list;
	}

	@CrossOrigin
	@GetMapping("/allocationDetailsTimesheetlogdate/{logday}")

	public List<allocationDetailsTimesheet> getfindBylogdate(@PathVariable("logday") String logday) {
		List<allocationDetailsTimesheet> list = (List<allocationDetailsTimesheet>) allocationDetailsTimesheetRepository
				.findBylogday(logday);

		return list;
	}

	@CrossOrigin
	@GetMapping("/allocationDetailsTimesheetDateEmployees/{logday}/{imEmployeeId}")

	public List<allocationDetailsTimesheet> getfindBylogdateAndImEmployeeId(@PathVariable("logday") String logday,
			@PathVariable("imEmployeeId") Long imEmployeeId) {
		List<allocationDetailsTimesheet> list = (List<allocationDetailsTimesheet>) allocationDetailsTimesheetRepository
				.findBylogdayAndImEmployeeId(logday, imEmployeeId);

		return list;
	}

	@CrossOrigin
	@GetMapping("/allocationDetailsTimesheetDateProjects/{logday}/{imProjectsId}")

	public List<allocationDetailsTimesheet> getfindBylogdateAndImProjectsId(@PathVariable("logday") String logday,
			@PathVariable("imProjectsId") Long imProjectsId) {
		List<allocationDetailsTimesheet> list = (List<allocationDetailsTimesheet>) allocationDetailsTimesheetRepository
				.findBylogdayAndImProjectsId(logday, imProjectsId);

		return list;
	}

	@CrossOrigin
	@GetMapping("/allocationDetailsTimesheetEmployeeProjects/{imEmployeeId}/{imProjectsId}")

	public List<allocationDetailsTimesheet> getfindBylogdateAndImProjectsId(
			@PathVariable("imEmployeeId") Long imEmployeeId, @PathVariable("imProjectsId") Long imProjectsId) {
		List<allocationDetailsTimesheet> list = (List<allocationDetailsTimesheet>) allocationDetailsTimesheetRepository
				.findByimEmployeeIdAndImProjectsId(imEmployeeId, imProjectsId);

		return list;
	}

	@CrossOrigin
	@GetMapping("/allocationDetailsTimesheetFilter/{logday}/{imEmployeeId}/{imProjectsId}")

	public List<allocationDetailsTimesheet> getfindBylogdate(@PathVariable("logday") String logday,
			@PathVariable("imEmployeeId") Long imEmployeeId, @PathVariable("imProjectsId") Long imProjectsId) {
		List<allocationDetailsTimesheet> list = (List<allocationDetailsTimesheet>) allocationDetailsTimesheetRepository
				.findBylogdayAndImEmployeeIdAndImProjectsId(logday, imEmployeeId, imProjectsId);

		return list;
	}

	@CrossOrigin
	@GetMapping("/allocationDetailsTimesheetFilterppdparentid/{logday}/{imEmployeeId}/{ppdparentid}")

	public List<allocationDetailsTimesheet> getfindBylogdateppd(@PathVariable("logday") String logday,
			@PathVariable("imEmployeeId") Long imEmployeeId, @PathVariable("ppdparentid") Long ppdparentid) {
		List<allocationDetailsTimesheet> list = (List<allocationDetailsTimesheet>) allocationDetailsTimesheetRepository
				.findBylogdayAndImEmployeeIdAndPpdparentid(logday, imEmployeeId, ppdparentid);

		return list;
	}
}

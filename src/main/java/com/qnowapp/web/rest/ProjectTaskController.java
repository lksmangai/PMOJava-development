package com.qnowapp.web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.loader.tools.BuildPropertiesWriter.ProjectDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qnowapp.domain.AllocationDetailsModel;
import com.qnowapp.domain.ListOfId;
import com.qnowapp.domain.ProjectDetailsModel;
import com.qnowapp.domain.ProjectParentDetails;
import com.qnowapp.domain.ProjectTask;
import com.qnowapp.repository.AllocationDetailsModelRepository;
import com.qnowapp.repository.ProjectDetailsModelRepository;
import com.qnowapp.repository.ProjectParentDetailsRepository;
import com.qnowapp.repository.ProjectTaskRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ProjectTaskController {
	@Autowired
    ProjectParentDetailsRepository  ProjectParentDetailsRepository;
	
	@Autowired
	ProjectTaskRepository ProjectTaskRepository;
	
	@Autowired
	AllocationDetailsModelRepository allocationDetailsModelRepository;
	
	@Autowired
	ProjectDetailsModelRepository ProjectDetailsRepository;
	
	@CrossOrigin
	@GetMapping("/ProjectTask")
	public List<ProjectTask> getAllProjectTask() {
		List<ProjectTask> list = (List<ProjectTask>) ProjectTaskRepository.findAll();

		 List<ProjectTask> result = list.stream()
		 .filter(d ->d.getParentId()!=null)
		 .collect(Collectors.toList());
		
		
		
		return result;
	}
	
	@CrossOrigin
	@PostMapping("/ProjectTaskbyParent")
	public ResponseEntity<List<ProjectTask>> getAllProjectTaskbyParent(@RequestBody  ListOfId parentid  ) {

		List<ProjectParentDetails> resultnew = (List<ProjectParentDetails>) ProjectParentDetailsRepository
				.findByparentIdIn(parentid.getId());
		
		List<Long> projectid = new ArrayList<Long>();

		
		resultnew .forEach(item -> {
	
			Long subtaskid = item.getProjectid();
	
			if(subtaskid!=null) {

				projectid.add(subtaskid);
		
			}

		});

		
		List<ProjectTask> result = (List<ProjectTask>) ProjectTaskRepository
				.findByidIn(projectid);

	
		

		if (result != null) {
			return new ResponseEntity<List<ProjectTask>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ProjectTask>>(result, HttpStatus.NO_CONTENT);

		}
	}
	
	
	@CrossOrigin
	@GetMapping("/ProjectTaskbyParent/{id}")
	public ResponseEntity<List<ProjectTask>> getAllProjectTaskbyParentbyId(@PathVariable("id") Long id  ) {

		List<ProjectParentDetails> resultnew = (List<ProjectParentDetails>) ProjectParentDetailsRepository
				.findByparentId(id);
		
		List<Long> projectid = new ArrayList<Long>();

		if(resultnew!=null) {
			
			resultnew .forEach(item -> {
				
				Long subtaskid = item.getProjectid();
		
				if(subtaskid!=null) {

					projectid.add(subtaskid);
			
				}

			});
		}


		

		
		List<ProjectTask> result = (List<ProjectTask>) ProjectTaskRepository
				.findByidIn(projectid);

	
		

		if (result != null) {
			return new ResponseEntity<List<ProjectTask>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ProjectTask>>(result, HttpStatus.NO_CONTENT);

		}
	}
	
	
	
	@CrossOrigin
	@GetMapping("/ProjectTaskEmployeesprojects/{imEmployee}")
	public ResponseEntity<List<ProjectTask>> getfindByimEmployeeprojects(
			@PathVariable("imEmployee") Long imEmployee) {
		List<AllocationDetailsModel> list = (List<AllocationDetailsModel>) allocationDetailsModelRepository
				.findByimEmployee(imEmployee);
		List<Long> projectid = new ArrayList<Long>();
		list.forEach(item -> {

			Long projectID = item.getImProjects();

			if (projectID != null) {
				projectid.add(projectID);

			}
		}

		);

		
		
		List<ProjectTask> resultnew = (List<ProjectTask>) ProjectTaskRepository
				.findByidIn(projectid);
		
		 List<ProjectTask> result = resultnew.stream()
		 .filter(d ->d.getParentId()!=null)
		 .collect(Collectors.toList());

		if (result != null) {
			return new ResponseEntity<List<ProjectTask>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ProjectTask>>(result, HttpStatus.NO_CONTENT);

		}

	}
	

}

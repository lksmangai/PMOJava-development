package com.qnowapp.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qnowapp.domain.ProjectManager;
import com.qnowapp.repository.ProjectManagerRepository;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ProjectManagerController {
	
	@Autowired
	ProjectManagerRepository projectManagerRepository ;
	
	
	@CrossOrigin
	@GetMapping("/projectManagers")
	public List<ProjectManager> getfindAll()
	{
		List<ProjectManager> list=(List<ProjectManager>) projectManagerRepository.findAll();

		return  list ;
	}
	

}

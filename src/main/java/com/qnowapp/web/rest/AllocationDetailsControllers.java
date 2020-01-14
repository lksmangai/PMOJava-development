package com.qnowapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qnowapp.domain.AllocationDetailsModel;
import com.qnowapp.domain.City;
import com.qnowapp.domain.ImEmployee;
import com.qnowapp.domain.ImProjects;
import com.qnowapp.domain.ImTimesheet;
import com.qnowapp.domain.ProjectAllocation;
import com.qnowapp.domain.ProjectDetailsModel;
import com.qnowapp.domain.ProjectDetailsTask;
import com.qnowapp.domain.ProjectRoles;
import com.qnowapp.domain.StringProject;
import com.qnowapp.repository.AllocationDetailsModelRepository;
import com.qnowapp.repository.CityRepository;
import com.qnowapp.repository.ImProjectsRepository;
import com.qnowapp.repository.ProjectAllocationRepository;
import com.qnowapp.repository.ProjectDetailsModelRepository;
import com.qnowapp.repository.ProjectDetailsTaskRepository;
import com.qnowapp.repository.ProjectRolesRepository;
import com.qnowapp.service.ImEmployeeService;
import com.qnowapp.service.ImProjectsService;
import com.qnowapp.service.ProjectAllocationService;
import com.qnowapp.service.ProjectRolesService;
import com.qnowapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class AllocationDetailsControllers {

	@Autowired
	ProjectRolesService projectRolesService;

	@Autowired
	AllocationDetailsModelRepository allocationDetailsModelRepository;

	@Autowired
	ProjectDetailsModelRepository projectDetailsModelRepository;

	@Autowired
	ProjectAllocationRepository projectAllocationRepository;

	@Autowired
	ImEmployeeService imEmployeeService;

	@Autowired
	ImProjectsService imProjectsService;

	@Autowired
	ProjectRolesRepository projectRolesRepository;
	@Autowired
	ProjectAllocationService projectAllocationService;

	@Autowired
	ProjectDetailsTaskRepository projectDetailsTaskRepository;

	@Autowired
	ImProjectsRepository imProjectsRepository;

	@Autowired
	CityRepository CityRepository;

	private static final String ENTITY_NAME = "projectAllocation";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;



	@CrossOrigin
	@PostMapping("/createProjectAllocation")
	public ResponseEntity<ProjectAllocation> createallocationNew1(

			@Valid @RequestBody AllocationDetailsModel AllocationDetailsModel) throws URISyntaxException {

		if (AllocationDetailsModel.getId() != null) {

			throw new BadRequestAlertException("A new projectAllocation cannot already have an ID", ENTITY_NAME,

					"idexists");

		} else {

			try {

				
				ProjectAllocation result = new ProjectAllocation();

				ProjectAllocation projectAllocation = new ProjectAllocation();

				ProjectAllocation ProjectAllocation2 = null;

				

				projectAllocation.setPercentage(100.0);

				Long allocationLid = AllocationDetailsModel.getImEmployee();

				ImEmployee imEmployee2 = null;

				if (allocationLid != null) {

					

					Optional<ImEmployee> imEmployee1 = imEmployeeService.findOne(allocationLid);

					if (imEmployee1.isPresent()) {

						imEmployee2 = imEmployee1.get();

						;

					}

					projectAllocation.setImEmployee(imEmployee2);

				}

				Long Projectid = AllocationDetailsModel.getImProjects();

				
				ImProjects imProjects2 = null;

				if (Projectid != null) {

					Optional<ImProjects> imProjects1 = imProjectsService.findOne(Projectid);

					
					if (imProjects1.isPresent()) {

						imProjects2 = imProjects1.get();

	
					}

					projectAllocation.setImProjects(imProjects2);

				}

				ProjectRoles ProjectRoles = new ProjectRoles();

				List<ProjectRoles> myRoles = projectRolesRepository.findAll();

				long roles = new Long(2555);

				Optional<ProjectRoles> ProjectRoles1 = projectRolesService.findOne(roles);

				
				ProjectRoles ProjectRoles2 = null;

				if (ProjectRoles1.isPresent()) {

					ProjectRoles2 = ProjectRoles1.get();

					
				}

				projectAllocation.setProjectRoles(ProjectRoles2);

				
				List<AllocationDetailsModel> myhashold = allocationDetailsModelRepository

						.findByimProjectsAndImEmployee(AllocationDetailsModel.getImProjects(),
								AllocationDetailsModel.getImEmployee());

				

				boolean toprocidold = false;

				if (myhashold == null) {

					

					toprocidold = true;

				} else {


					toprocidold = (myhashold.size() == 0);

				}

				// ProjectAllocation result = new ProjectAllocation();

				if (toprocidold) {

					

					result = projectAllocationService.save(projectAllocation);

//					ImProjects ParentProject = projectAllocation.getImProjects().getParentId();
//
//					
//					while (ParentProject != null) {
//
//						List<AllocationDetailsModel> myhash = allocationDetailsModelRepository
//
//								.findByimProjectsAndImEmployee(ParentProject.getId(), allocationLid);
//
//						boolean toprocidold1 = false;
//
//						if (myhash == null) {
//
//							
//
//							toprocidold1 = true;
//
//						} else {
//
//
//							toprocidold1 = (myhash.size() == 0);
//
//						}
//
//						if (toprocidold1) {
//
//							ProjectAllocation2 = new ProjectAllocation();
//
//							ProjectAllocation2.setPercentage(100.0);
//
//							ProjectAllocation2.setImEmployee(imEmployee2);
//
//	
//
//							ImProjects imProjects3 = ParentProject;
//
//							ProjectAllocation2.setImProjects(ParentProject);
//
//							ProjectAllocation2.setProjectRoles(projectAllocation.getProjectRoles());
//
//							ProjectAllocation resultnew = projectAllocationService.save(ProjectAllocation2);
//
//							if (imProjects3 != null) {
//
//								ImProjects LocalParent = imProjects3.getParentId();
//
//								if (LocalParent != null) {
//
//									ParentProject = imProjects3.getParentId();
//
//								} else {
//
//									ParentProject = null;
//
//								}
//
//							} else {
//
//								ParentProject = null;
//
//							}
//
//						}
//
//					}

				} else {


					result = new ProjectAllocation();

					result.setId(0L);

				}

				return ResponseEntity.created(new URI("/api/createProjectAllocation/" + result.getId()))

						.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,

								result.getId().toString()))

						.body(result);

			} catch (Exception e) {

				e.printStackTrace();

				return new ResponseEntity<ProjectAllocation>(HttpStatus.EXPECTATION_FAILED);

			}

		}

	}

	@CrossOrigin
	@GetMapping("/allocation")
	public List<AllocationDetailsModel> getAllTimesheet() {
		List<AllocationDetailsModel> list = (List<AllocationDetailsModel>) allocationDetailsModelRepository.findAll();

		return list;
	}

	@CrossOrigin
	@GetMapping("/allocation/{id}")
	public List<AllocationDetailsModel> getfindById(@PathVariable("id") Long id) {
		List<AllocationDetailsModel> list = (List<AllocationDetailsModel>) allocationDetailsModelRepository
				.findByid(id);

		return list;
	}

	@CrossOrigin
	@GetMapping("/allocationDetailsPPDparentid/{ppdparentid}")
	public List<AllocationDetailsModel> getfindByPPDparentid(@PathVariable("ppdparentid") Long ppdparentid) {
		List<AllocationDetailsModel> list = (List<AllocationDetailsModel>) allocationDetailsModelRepository
				.findByppdparentid(ppdparentid);

		return list;
	}

	@CrossOrigin
	@GetMapping("/allocationDetailsEmployeesprojectsPPDparentid/{ppdparentid}/{imEmployeeId}")
	public List<AllocationDetailsModel> getfindByimEmployeeparentprojects3(
			@PathVariable("ppdparentid") Long ppdparentid, @PathVariable("imEmployeeId") Long imEmployee) {
		List<AllocationDetailsModel> list = (List<AllocationDetailsModel>) allocationDetailsModelRepository
				.findByppdparentidAndImEmployee(ppdparentid, imEmployee);

		return list;
	}

	@CrossOrigin
	@GetMapping("/allocationProjects/{imProjectsId}")
	public List<AllocationDetailsModel> getfindByimProjects(@PathVariable("imProjectsId") Long imProjectsId) {
		List<AllocationDetailsModel> list = (List<AllocationDetailsModel>) allocationDetailsModelRepository
				.findByimProjects(imProjectsId);

		return list;
	}

	@CrossOrigin
	@GetMapping("/allocationEmployees/{imEmployee}")
	public List<AllocationDetailsModel> getfindByimEmployee(@PathVariable("imEmployee") Long imEmployee) {
		List<AllocationDetailsModel> list = (List<AllocationDetailsModel>) allocationDetailsModelRepository
				.findByimEmployee(imEmployee);
		return list;
	}

	@CrossOrigin
	@GetMapping("/allocationEmployeesprojects/{imEmployee}")
	public ResponseEntity<List<ProjectDetailsModel>> getfindByimEmployeeprojects(
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

		List<ProjectDetailsModel> resultnew = (List<ProjectDetailsModel>) projectDetailsModelRepository
				.findByidIn(projectid);

		if (resultnew != null) {
			return new ResponseEntity<List<ProjectDetailsModel>>(resultnew, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ProjectDetailsModel>>(resultnew, HttpStatus.NO_CONTENT);

		}

	}

	@CrossOrigin
	@GetMapping("/allocationparent")
	public List<AllocationDetailsModel> getAllTimesheetparent() {
		List<AllocationDetailsModel> list = (List<AllocationDetailsModel>) allocationDetailsModelRepository.findAll();

		List<AllocationDetailsModel> result = list.stream().filter(d -> d.getParentId() == null)
				.collect(Collectors.toList());
		return result;
	}

	@CrossOrigin
	@GetMapping("/allocationparentnotnull")
	public List<AllocationDetailsModel> getAllTimesheetparentnotnull() {
		List<AllocationDetailsModel> list = (List<AllocationDetailsModel>) allocationDetailsModelRepository.findAll();

		List<AllocationDetailsModel> result = list.stream().filter(d -> d.getParentId() != null)
				.collect(Collectors.toList());
		return result;
	}

	@CrossOrigin
	@GetMapping("/allocationparent/{id}")
	public List<AllocationDetailsModel> getfindByIdparent(@PathVariable("id") Long id) {
		List<AllocationDetailsModel> list = (List<AllocationDetailsModel>) allocationDetailsModelRepository
				.findByid(id);
		List<AllocationDetailsModel> result = list.stream().filter(d -> d.getParentId() == null)
				.collect(Collectors.toList());
		return result;
	}

	@CrossOrigin
	@GetMapping("/allocationProjectsparent/{imProjectsId}")
	public List<AllocationDetailsModel> getfindByimProjectsparent(@PathVariable("imProjectsId") Long imProjectsId) {
		List<AllocationDetailsModel> list = (List<AllocationDetailsModel>) allocationDetailsModelRepository
				.findByimProjects(imProjectsId);

		List<AllocationDetailsModel> result = list.stream().filter(d -> d.getParentId() == null)
				.collect(Collectors.toList());
		return result;
	}

	@CrossOrigin
	@GetMapping("/allocationEmployeesparent/{imEmployee}")
	public List<AllocationDetailsModel> getfindByimEmployeeparent(@PathVariable("imEmployee") Long imEmployee) {
		List<AllocationDetailsModel> list = (List<AllocationDetailsModel>) allocationDetailsModelRepository
				.findByimEmployee(imEmployee);
		List<AllocationDetailsModel> result = list.stream().filter(d -> d.getParentId() == null)
				.collect(Collectors.toList());
		return result;
	}

	@CrossOrigin
	@GetMapping("/allocationEmployeesparentprojects/{imEmployee}")
	public ResponseEntity<List<ProjectDetailsModel>> getfindByimEmployeeparentprojects(
			@PathVariable("imEmployee") Long imEmployee) {
		List<AllocationDetailsModel> list = (List<AllocationDetailsModel>) allocationDetailsModelRepository
				.findByimEmployee(imEmployee);
		
		List<AllocationDetailsModel> result = list.stream().filter(d -> d.getParentId() == null)
				.collect(Collectors.toList());
		
		List<Long> projectid = new ArrayList<Long>();
		
		result.forEach(item -> {
			

			Long projectID = item.getImProjects();

			
			if (projectID != null) {
				
				projectid.add(projectID);

			}
			
		}

		);

		

		List<ProjectDetailsModel> resultnew = (List<ProjectDetailsModel>) projectDetailsModelRepository
				.findByidIn(projectid);

		
		if (resultnew != null) {

			
			return new ResponseEntity<List<ProjectDetailsModel>>(resultnew, HttpStatus.OK);
		} else {

			
			return new ResponseEntity<List<ProjectDetailsModel>>(resultnew, HttpStatus.NO_CONTENT);

		}
	}

	@CrossOrigin
	@GetMapping("/allocationEmployeesparentprojectsnotnull/{imEmployee}")
	public ResponseEntity<List<ProjectDetailsModel>> getfindByimEmployeeparentprojectsnotnull(
			@PathVariable("imEmployee") Long imEmployee) {
		List<AllocationDetailsModel> list = (List<AllocationDetailsModel>) allocationDetailsModelRepository
				.findByimEmployee(imEmployee);
		List<AllocationDetailsModel> result = list.stream().filter(d -> d.getParentId() != null)
				.collect(Collectors.toList());
		List<Long> projectid = new ArrayList<Long>();
		result.forEach(item -> {

			Long projectID = item.getImProjects();

			if (projectID != null) {
				projectid.add(projectID);

			}
		}

		);

		List<ProjectDetailsModel> resultnew = (List<ProjectDetailsModel>) projectDetailsModelRepository
				.findByidIn(projectid);

		if (resultnew != null) {
			return new ResponseEntity<List<ProjectDetailsModel>>(resultnew, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ProjectDetailsModel>>(resultnew, HttpStatus.NO_CONTENT);

		}

	}

	@CrossOrigin
	@GetMapping("/allocationEmployeesparentprojectsrisktype/{imEmployee}")
	public ResponseEntity<List<ProjectDetailsModel>> getfindByimEmployeeparentprojectsrisktype(
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

		List<ProjectDetailsModel> resultnew = (List<ProjectDetailsModel>) projectDetailsModelRepository
				.findByidIn(projectid);

		List<ProjectDetailsModel> result2 = resultnew.stream()
				.filter(d -> d.getRisktype() != null && d.getRisktype().equals("") == false)
				.collect(Collectors.toList());

		if (resultnew != null) {
			return new ResponseEntity<List<ProjectDetailsModel>>(result2, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ProjectDetailsModel>>(result2, HttpStatus.NO_CONTENT);

		}

	}

	@CrossOrigin
	@GetMapping("/allocationEmployeesparentprojectsmilestone/{imEmployee}")
	public ResponseEntity<List<ProjectDetailsModel>> getfindByimEmployeeparentprojectsmilestone(
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

		List<ProjectDetailsModel> resultnew = (List<ProjectDetailsModel>) projectDetailsModelRepository
				.findByidIn(projectid);

		List<ProjectDetailsModel> result2 = resultnew.stream()
				.filter(d -> d.getMilestoneP() != null && d.getMilestoneP() == true).collect(Collectors.toList());

		if (resultnew != null) {
			return new ResponseEntity<List<ProjectDetailsModel>>(result2, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ProjectDetailsModel>>(result2, HttpStatus.NO_CONTENT);
		}
	}

	@CrossOrigin
	@GetMapping("/ProjectDetailsTaskallocationEmployeesparentprojects/{imEmployee}")
	public ResponseEntity<List<ProjectDetailsTask>> getfindByimEmployeeparentprojects1(
			@PathVariable("imEmployee") Long imEmployee) {
		List<AllocationDetailsModel> list = (List<AllocationDetailsModel>) allocationDetailsModelRepository
				.findByimEmployee(imEmployee);
		List<AllocationDetailsModel> result = list.stream().filter(d -> d.getParentId() == null)
				.collect(Collectors.toList());
		List<Long> projectid = new ArrayList<Long>();
		result.forEach(item -> {

			Long projectID = item.getImProjects();

			if (projectID != null) {
				projectid.add(projectID);

			}
		}

		);

		List<ProjectDetailsTask> resultnew = (List<ProjectDetailsTask>) projectDetailsTaskRepository
				.findByidIn(projectid);

		if (resultnew != null) {
			return new ResponseEntity<List<ProjectDetailsTask>>(resultnew, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ProjectDetailsTask>>(resultnew, HttpStatus.NO_CONTENT);
		}
	}

	@CrossOrigin
	@GetMapping("/allocationEmployeesprojectsparents/{parentId}/{imEmployee}")
	public ResponseEntity<List<ProjectDetailsModel>> getfindByimEmployeeparentprojects11(
			@PathVariable("parentId") Long parentId, @PathVariable("imEmployee") Long imEmployee) {
		List<AllocationDetailsModel> list = (List<AllocationDetailsModel>) allocationDetailsModelRepository
				.findByparentIdAndImEmployee(parentId, imEmployee);
		List<AllocationDetailsModel> result = list;
		List<Long> projectid = new ArrayList<Long>();
		result.forEach(item -> {

			Long projectID = item.getImProjects();

			if (projectID != null) {
				projectid.add(projectID);

			}
		}

		);

		List<ProjectDetailsModel> resultnew = (List<ProjectDetailsModel>) projectDetailsModelRepository
				.findByidIn(projectid);

		if (resultnew != null) {
			return new ResponseEntity<List<ProjectDetailsModel>>(resultnew, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ProjectDetailsModel>>(resultnew, HttpStatus.NO_CONTENT);
		}

	}

	@CrossOrigin
	@GetMapping("/ProjectDetailsTaskallocationEmployeesprojectsparents/{parentId}/{imEmployee}")
	public ResponseEntity<List<ProjectDetailsTask>> getfindByimEmployeeparentprojects1(
			@PathVariable("parentId") Long parentId, @PathVariable("imEmployee") Long imEmployee) {
		List<AllocationDetailsModel> list = (List<AllocationDetailsModel>) allocationDetailsModelRepository
				.findByparentIdAndImEmployee(parentId, imEmployee);
		List<AllocationDetailsModel> result = list;
		List<Long> projectid = new ArrayList<Long>();
		result.forEach(item -> {

			Long projectID = item.getImProjects();

			if (projectID != null) {
				projectid.add(projectID);

			}
		}

		);

		List<ProjectDetailsTask> resultnew = (List<ProjectDetailsTask>) projectDetailsTaskRepository
				.findByidIn(projectid);

		if (resultnew != null) {
			return new ResponseEntity<List<ProjectDetailsTask>>(resultnew, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ProjectDetailsTask>>(resultnew, HttpStatus.NO_CONTENT);
		}
	}

	@CrossOrigin
	@GetMapping("/allocationEmployeesprojectsparentsallocation/{parentId}/{imEmployee}")
	public List<AllocationDetailsModel> getfindByimEmployeeparentprojects2(@PathVariable("parentId") Long parentId,
			@PathVariable("imEmployee") Long imEmployee) {
		List<AllocationDetailsModel> list = (List<AllocationDetailsModel>) allocationDetailsModelRepository
				.findByparentIdAndImEmployee(parentId, imEmployee);

		return list;

	}

	@CrossOrigin
	@GetMapping("/allocationFilter/{imProjects}/{imEmployee}")
	public List<AllocationDetailsModel> getfindByimProjects(@PathVariable("imProjects") Long imProjects,
			@PathVariable("imEmployee") Long imEmployee) {
		List<AllocationDetailsModel> list = (List<AllocationDetailsModel>) allocationDetailsModelRepository
				.findByimProjectsAndImEmployee(imProjects, imEmployee);

		return list;
	}

	@CrossOrigin
	@DeleteMapping("/projectallocationDelete/{imProjects}/{imEmployee}")
	public ResponseEntity<StringProject> deleteImProjects11(@PathVariable("imProjects") Long imProjects,
			@PathVariable("imEmployee") Long imEmployee) {

		StringProject msg = new StringProject();
		List<AllocationDetailsModel> AllocationDetailsModel = allocationDetailsModelRepository
				.findByimProjectsAndImEmployee(imProjects, imEmployee);

		boolean toprocid = false;
		if (AllocationDetailsModel == null) {
			

			toprocid = true;
		} else {
			toprocid = AllocationDetailsModel.size() == 0;
		
		}
		if (!toprocid) {
			

			for (AllocationDetailsModel allocationDetailsModel2 : AllocationDetailsModel) {

				projectAllocationService.delete(allocationDetailsModel2.getId());
				msg.setMsg("record successfully deleted");

			}
			return new ResponseEntity<StringProject>(msg, HttpStatus.OK);

		} else {
			msg.setMsg("record not found");

			return new ResponseEntity<StringProject>(msg, HttpStatus.OK);
		}

	}

}
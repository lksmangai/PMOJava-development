package com.qnowapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qnowapp.domain.ImProjects;
import com.qnowapp.domain.ImTimesheet;
import com.qnowapp.domain.ListOfId;
import com.qnowapp.domain.ProjectDetailsModel;
import com.qnowapp.domain.ProjectDetailsTask;
import com.qnowapp.domain.ProjectParentDetails;
import com.qnowapp.repository.ImTimesheetRepository;
import com.qnowapp.repository.ProjectDetailsTaskRepository;
import com.qnowapp.repository.ProjectParentDetailsRepository;
import com.qnowapp.service.ImProjectsService;
import com.qnowapp.service.ImTimesheetService;
import com.qnowapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ProjectDetailsTaskController {

	@Autowired
	ProjectDetailsTaskRepository ProjectDetailsTaskRepository;

	@Autowired
	ImTimesheetService imTimesheetService;

	@Autowired
	ImTimesheetRepository imTimesheetRepository;

	@Autowired
	ImProjectsService imProjectsService;

	@Autowired
	ProjectParentDetailsRepository ProjectParentDetailsRepository;

	@Value("${jhipster.clientApp.name}")

	private static final String ENTITY_NAME = "imTimesheet";
	private String applicationName;

	
	
	@CrossOrigin
	@PostMapping("/createim-timesheets-update")
	public ResponseEntity<ImTimesheet> createImTimesheetUpdate(@Valid @RequestBody ImTimesheet imTimesheetnew)
			throws URISyntaxException {
	
			ImTimesheet updateTimesheet = new ImTimesheet();
			ImTimesheet findlist = imTimesheetRepository.findByimProjectsAndImEmployeeAndLogday(imTimesheetnew.getImProjects(),imTimesheetnew.getImEmployee(),imTimesheetnew.getLogday());
		
				
				
				if(findlist!= null ) {
	    			
	    			
	    			updateTimesheet		= findlist;

	    			Long time = updateTimesheet.getId();
	    			ImTimesheet ImTimesheet3 = null;

	    			if (time != null) {
	    				Optional<ImTimesheet> ImTimesheet1 = imTimesheetService.findOne(time);
	    	

	    				if (ImTimesheet1.isPresent()) {
	    					ImTimesheet3 = ImTimesheet1.get();
	    				}


	    			}

	    			double oldLoghours = 0.0;
	    			double cachehours = 0.0;
	    			double Loghours = 0.0;
	    			double sumcachehours = 0.0;
	    			if (ImTimesheet3 != null) {
	    				oldLoghours = ImTimesheet3.getLoghours();
	    			}
	    			ImTimesheet result = imTimesheetService.save(updateTimesheet);
	    			ImProjects imProjects = result.getImProjects();
	    			if (updateTimesheet.getLoghours() != null) {

	    				Loghours = updateTimesheet.getLoghours();
	    			}

	    			do {
	    				if (imProjects.getReportedHoursCache() != null) {
	    					cachehours = imProjects.getReportedHoursCache();
	    				}
	    				cachehours = imProjects.getReportedHoursCache();

	    				sumcachehours = cachehours + Loghours - oldLoghours;

	    				imProjects.setReportedHoursCache(sumcachehours);
	    				ImProjects resultnew = imProjectsService.save(imProjects);

	    				imProjects = resultnew.getParentId();

	    			} while (imProjects != null);
	    			if(result.getLoghours()==null || result.getLoghours()==0) {
	    			
	    				imTimesheetService.delete(result.getId());
	    				result= new ImTimesheet();
	    				result.setId(0L);
	    		
	    			}

	    			ResponseEntity<ImTimesheet> newresult = ResponseEntity
	    					.created(new URI("/api/createim-timesheets/" + result.getId())).headers(HeaderUtil
	    							.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
	    					.body(result);

	    			return newresult;
	    		}
  
		 else {
			
	
				double cachehours = 0.0;
				double Loghours = 0.0;
				double sumcachehours = 0.0;
				ImTimesheet result = imTimesheetService.save(imTimesheetnew);
				ImProjects imProjects = result.getImProjects();

				if (imTimesheetnew.getLoghours() != null) {
					Loghours = imTimesheetnew.getLoghours();
				}

				do {

					if (imProjects.getReportedHoursCache() != null) {
						cachehours = imProjects.getReportedHoursCache();
					}

					sumcachehours = cachehours + Loghours;

					imProjects.setReportedHoursCache(sumcachehours);
					ImProjects resultnew = imProjectsService.save(imProjects);

					imProjects = resultnew.getParentId();

				} while (imProjects != null);

				if(result.getLoghours()==null || result.getLoghours()==0) {
				
					imTimesheetService.delete(result.getId());
					result = new ImTimesheet();
					result.setId(0L);
			
				}
				
				ResponseEntity<ImTimesheet> newresult = ResponseEntity
						.created(new URI("/api/createim-timesheets/" + result.getId())).headers(HeaderUtil
								.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
						.body(result);

				return newresult;
			}		
    			
}	
	
	
	
	
	@CrossOrigin
	@PostMapping("/createim-timesheets")
	public ResponseEntity<ImTimesheet> createImTimesheet(@Valid @RequestBody ImTimesheet imTimesheetnew)
			throws URISyntaxException {
		if (imTimesheetnew.getId() != null) {
			throw new BadRequestAlertException("A new imTimesheet cannot already have an ID", ENTITY_NAME, "idexists");
		} else {
			double cachehours = 0.0;
			double Loghours = 0.0;
			double sumcachehours = 0.0;
			ImTimesheet result = imTimesheetService.save(imTimesheetnew);
			ImProjects imProjects = result.getImProjects();

			if (imTimesheetnew.getLoghours() != null) {
				Loghours = imTimesheetnew.getLoghours();
			}

			do {

				if (imProjects.getReportedHoursCache() != null) {
					cachehours = imProjects.getReportedHoursCache();
				}

				sumcachehours = cachehours + Loghours;

				imProjects.setReportedHoursCache(sumcachehours);
				ImProjects resultnew = imProjectsService.save(imProjects);

				imProjects = resultnew.getParentId();

			} while (imProjects != null);

			if(result.getLoghours()==null || result.getLoghours()==0) {
		
				imTimesheetService.delete(result.getId());
				result = new ImTimesheet();
				result.setId(0L);
		
			}
			
			ResponseEntity<ImTimesheet> newresult = ResponseEntity
					.created(new URI("/api/createim-timesheets/" + result.getId())).headers(HeaderUtil
							.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
					.body(result);

			return newresult;
		}
	}

	@CrossOrigin
	@PutMapping("/updateim-timesheets")
	public ResponseEntity<ImTimesheet> createImTimesheet1(@Valid @RequestBody ImTimesheet imTimesheet)
			throws URISyntaxException {
		if (imTimesheet.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		else {
			
			ImTimesheet record =imTimesheetRepository.findByid(imTimesheet.getId());
			
			if(record==null ) {
				throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "Id not found");
			}else {

			Long time = imTimesheet.getId();
			ImTimesheet ImTimesheet = null;

			ImTimesheet = record;
			
			double oldLoghours = 0.0;
			double cachehours = 0.0;
			double Loghours = 0.0;
			double sumcachehours = 0.0;
			if (ImTimesheet.getLoghours() != null) {
				oldLoghours = ImTimesheet.getLoghours();
			}
			ImTimesheet result = imTimesheetService.save(imTimesheet);
			ImProjects imProjects = result.getImProjects();
			if (imTimesheet.getLoghours() != null) {

				Loghours = imTimesheet.getLoghours();
			}

			do {
				if (imProjects.getReportedHoursCache() != null) {
					cachehours = imProjects.getReportedHoursCache();
				}
				cachehours = imProjects.getReportedHoursCache();

				sumcachehours = cachehours + Loghours - oldLoghours;

				imProjects.setReportedHoursCache(sumcachehours);
				ImProjects resultnew = imProjectsService.save(imProjects);

				imProjects = resultnew.getParentId();

			} while (imProjects != null);
			if(result.getLoghours()==null || result.getLoghours()==0) {
				
				imTimesheetService.delete(result.getId());
				result= new ImTimesheet();
				result.setId(0L);
				
			}

			ResponseEntity<ImTimesheet> newresult = ResponseEntity
					.created(new URI("/api/createim-timesheets/" + result.getId())).headers(HeaderUtil
							.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
					.body(result);

			return newresult;
		}
	}
	}

	@CrossOrigin
	@GetMapping("/ProjectDetailsTaskprojects")
	public List<ProjectDetailsTask> getAllProjects() {
		List<ProjectDetailsTask> list = (List<ProjectDetailsTask>) ProjectDetailsTaskRepository.findAll();

	
		return list;
	}

	@CrossOrigin
	@GetMapping("/ProjectDetailsTaskprojectsid/{ids}")
	public List<ProjectDetailsTask> getfindByidIn(@PathVariable("ids") List<Long> ids) {
		List<ProjectDetailsTask> list = (List<ProjectDetailsTask>) ProjectDetailsTaskRepository.findByidIn(ids);

	

		return list;
	}

	@CrossOrigin
	@GetMapping("/ProjectDetailsTaskmainprojects")
	public ResponseEntity<List<ProjectDetailsTask>> getAllParentProjects() {
		List<ProjectDetailsTask> list = (List<ProjectDetailsTask>) ProjectDetailsTaskRepository.findAll();

		List<ProjectDetailsTask> result = list.stream().filter(d -> d.getParentId() == null)
				.collect(Collectors.toList());
		if (result != null) {
			return new ResponseEntity<List<ProjectDetailsTask>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ProjectDetailsTask>>(result, HttpStatus.NO_CONTENT);

		}
	}

	@CrossOrigin
	@GetMapping("/ProjectDetailsTaskchildprojects/{parentId}")

	public List<ProjectDetailsTask> getfindByparentid(@PathVariable("parentId") Long parentId) {
		List<ProjectDetailsTask> list = (List<ProjectDetailsTask>) ProjectDetailsTaskRepository
				.findByparentId(parentId);
		return list;
	}

	@CrossOrigin
	@GetMapping("/ProjectDetailsTaskprojectstatus/{projectStatusNames}") // id in

	public List<ProjectDetailsTask> getfindByprojectStatusNameIn(
			@PathVariable("projectStatusNames") List<String> projectStatusNames) {
		List<ProjectDetailsTask> list = (List<ProjectDetailsTask>) ProjectDetailsTaskRepository
				.findByprojectStatusNameIn(projectStatusNames);
		return list;
	}

	@CrossOrigin
	@GetMapping("/ProjectDetailsTaskprojectstatusparent/{projectStatusNames}") // id in

	public ResponseEntity<List<ProjectDetailsTask>> getfindByprojectStatusNameInParent(
			@PathVariable("projectStatusNames") List<String> projectStatusNames) {
		List<ProjectDetailsTask> list = (List<ProjectDetailsTask>) ProjectDetailsTaskRepository
				.findByprojectStatusNameIn(projectStatusNames);
		List<ProjectDetailsTask> result = list.stream().filter(d -> d.getParentId() == null)
				.collect(Collectors.toList());
		if (result != null) {
			return new ResponseEntity<List<ProjectDetailsTask>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ProjectDetailsTask>>(result, HttpStatus.NO_CONTENT);

		}
	}

	@CrossOrigin
	@GetMapping("/ProjectDetailsTaskprojectstatus/{projectStatusNames}/{ids}") // id in

	public List<ProjectDetailsTask> getfindByprojectStatusNameInAndId(
			@PathVariable("projectStatusNames") List<String> projectStatusNames, @PathVariable("ids") List<Long> ids) {
		List<ProjectDetailsTask> list = (List<ProjectDetailsTask>) ProjectDetailsTaskRepository
				.findByprojectStatusNameInAndId(projectStatusNames, ids);
		return list;
	}

	@CrossOrigin
	@GetMapping("/ProjectDetailsTaskprojectstatusparent/{projectStatusNames}/{parentId}")

	public List<ProjectDetailsTask> getfindByprojectStatusNameInAndParentid(
			@PathVariable("projectStatusNames") List<String> projectStatusNames,
			@PathVariable("parentId") Long parentId) {
		List<ProjectDetailsTask> list = (List<ProjectDetailsTask>) ProjectDetailsTaskRepository
				.findByprojectStatusNameInAndParentId(projectStatusNames, parentId);
		return list;
	}

	@CrossOrigin
	@GetMapping("/ProjectDetailsTaskprojectstatusparent/{projectStatusNames}/{parentId}/{ids}")

	public List<ProjectDetailsTask> getfindByprojectStatusNameInAndParentidAndIdIn(
			@PathVariable("projectStatusNames") List<String> projectStatusNames,
			@PathVariable("parentId") Long parentId, @PathVariable("ids") List<Long> ids) {
		List<ProjectDetailsTask> list = (List<ProjectDetailsTask>) ProjectDetailsTaskRepository
				.findByprojectStatusNameInAndParentIdAndIdIn(projectStatusNames, parentId, ids);
		return list;
	}

	@CrossOrigin
	@GetMapping("/ProjectDetailsTaskprojectsbyname/{name}")

	public List<ProjectDetailsTask> getProjectsByName(@PathVariable("name") String name) {
		List<ProjectDetailsTask> list = (List<ProjectDetailsTask>) ProjectDetailsTaskRepository.findByprojectName(name);
		return list;
	}

	@CrossOrigin
	@PostMapping("/ProjectDetailsTaskbyParent")
	public ResponseEntity<List<ProjectDetailsTask>> getAllProjectDetailsTaskbyParent(@RequestBody ListOfId parentid) {

		List<ProjectParentDetails> resultnew = (List<ProjectParentDetails>) ProjectParentDetailsRepository
				.findByparentIdIn(parentid.getId());

		List<Long> projectid = new ArrayList<Long>();

		resultnew.forEach(item -> {

			Long subtaskid = item.getProjectid();

			if (subtaskid != null) {

				projectid.add(subtaskid);

			}

		});

		List<ProjectDetailsTask> result = (List<ProjectDetailsTask>) ProjectDetailsTaskRepository.findByidIn(projectid);

		if (result != null) {
			return new ResponseEntity<List<ProjectDetailsTask>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ProjectDetailsTask>>(result, HttpStatus.NO_CONTENT);

		}

	}
	
	@CrossOrigin
	@GetMapping("/ProjectDetailsTaskbyParent/{id}")
	public ResponseEntity<List<ProjectDetailsTask>> getAllProjectDetailsTaskbyParentbyId(@PathVariable("id") Long id  ) {

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


		List<ProjectDetailsTask> result = (List<ProjectDetailsTask>) ProjectDetailsTaskRepository.findByidIn(projectid);

		if (result != null) {
			return new ResponseEntity<List<ProjectDetailsTask>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ProjectDetailsTask>>(result, HttpStatus.NO_CONTENT);

		}

	}

}

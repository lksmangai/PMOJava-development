
package com.qnowapp.web.rest;

import java.net.URI;

import java.net.URISyntaxException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Transient;

import org.mapstruct.ap.shaded.freemarker.template.SimpleDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qnowapp.domain.ProjectsView;
import com.qnowapp.domain.AllocationModel;
import com.qnowapp.domain.ImEmployee;
import com.qnowapp.domain.ImProjects;
import com.qnowapp.domain.ImProjectsJsonModel;
import com.qnowapp.domain.ImTimesheet;
import com.qnowapp.domain.ListOfId;
import com.qnowapp.domain.OpportunityPriorityId;
import com.qnowapp.domain.ProjectAllocation;
import com.qnowapp.domain.ProjectDetailsModel;
import com.qnowapp.domain.ProjectParentDetails;
import com.qnowapp.domain.ProjectStatusId;
import com.qnowapp.domain.ProjectTask;
import com.qnowapp.domain.ProjectTheme;
import com.qnowapp.domain.ProjectTypeId;
import com.qnowapp.domain.ProjectVertical;
import com.qnowapp.domain.StatusList;
import com.qnowapp.domain.StringProject;
import com.qnowapp.repository.ImProjectsRepository;
import com.qnowapp.repository.ImTimesheetRepository;
import com.qnowapp.repository.ProjectAllocationRepository;
import com.qnowapp.repository.ProjectDetailsModelRepository;
import com.qnowapp.repository.ProjectParentDetailsRepository;
import com.qnowapp.repository.TimesheetDetailsModelRepository;
import com.qnowapp.service.ProjectsViewService;
import com.qnowapp.service.ImEmployeeService;
import com.qnowapp.service.ImProjectsService;
import com.qnowapp.service.OpportunityPriorityIdService;
import com.qnowapp.service.ProjectAllocationService;
//import com.qnowapp.service.ImEmployeeService;
//import com.qnowapp.service.ImProjectsService;
//import com.qnowapp.service.OpportunityPriorityIdService;
//import com.qnowapp.service.ProjectStatusIdService;
//import com.qnowapp.service.ProjectTypeIdService;
//import com.qnowapp.web.rest.errors.BadRequestAlertException;
//
//import io.github.jhipster.web.util.HeaderUtil;
//import io.swagger.annotations.ApiOperation;
import com.qnowapp.service.ProjectStatusIdService;
import com.qnowapp.service.ProjectThemeService;
import com.qnowapp.service.ProjectTypeIdService;
import com.qnowapp.service.ProjectVerticalService;
import com.qnowapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ProjectDetailsControllers {
	@Autowired
	ProjectsViewService agentDashboardService;
	
	@Autowired
	ImTimesheetRepository ImTimesheetRepository;

	@Autowired
	ProjectAllocationRepository projectAllocationRepository;

	@Autowired
	ProjectAllocationService projectAllocationService;

	@Autowired
	ProjectDetailsModelRepository projectDetailsModelRepository;

	@Autowired
	TimesheetDetailsModelRepository timesheetDetailsModelRepository;
	@Autowired
	ImProjectsRepository imProjectsRepository;

	@Autowired
	ImProjectsService imProjectsService;

	@Autowired
	ProjectStatusIdService projectStatusIdService;

	@Autowired
	ImEmployeeService imEmployeeService;

	@Autowired
	OpportunityPriorityIdService opportunityPriorityIdService;

	@Autowired
	ProjectVerticalService projectVerticalService;

	@Autowired
	ProjectTypeIdService projectTypeIdService;

	@Autowired
	ProjectThemeService projectThemeService;

	@Autowired
	ProjectParentDetailsRepository ProjectParentDetailsRepository;

	@Value("${jhipster.clientApp.name}")
	private String applicationName;
	private static final String ENTITY_NAME = "imProjects";

	public ZonedDateTime convertToZonedDateTime(Long zdt) {

		if (zdt == null) {

			return null;
		} else {

			ZonedDateTime zdt1 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(zdt), ZoneId.systemDefault());

			return zdt1;

		}
	}

	public Long convertToNumber(ZonedDateTime zdt) {

		if (zdt != null) {

			if (zdt.equals("")) {
				zdt = ZonedDateTime.now();
				long zdtresult = zdt.toInstant().toEpochMilli();
				return zdtresult;
			} else {

				long e = zdt.toInstant().toEpochMilli();

				return e;

			}
		} else {
			zdt = ZonedDateTime.now();
			long zdtresult = zdt.toInstant().toEpochMilli();
			return zdtresult;
		}

	}

//	public void Update(Long scalenew, Long divisionnew, Long shift, Double new_plan_hours, Double new_percentage,
//			ImProjects imProjects, boolean updateStart, boolean updateEnd) {
//
//		List<ImProjects> listsubtask = (List<ImProjects>) imProjectsRepository.findByparentId(imProjects);
//		
//
//		Long temp = 0L;
//
//		double tempIBH = 0.0;
//		double tempRBH = 0.0;
//		double tempIPC = 0.0;
//		double tempRPC = 0.0;
//
//		if (imProjects.getProjectBudgetHours() != null) {
//			tempRBH = imProjects.getProjectBudgetHours();
//		}
//		if (imProjects.getPercentCompleted() != null) {
//			tempRPC = imProjects.getPercentCompleted();
//		}
//		
//		boolean toprocid = false;
//		if (listsubtask == null) {
//
//			toprocid = true;
//		} else {
//			toprocid = (listsubtask.size() == 0);
//		}
//		if (!toprocid) {
//			Double sumPlanHours = 0.0;
//			Double sumCompleteHours = 0.0;
//
//			Double sumPlanDates = 0.0;
//			Double sumCompleteDates = 0.0;
//			double completdate = 0.0;
//
//			for (ImProjects ImProjectsSubTask : listsubtask) {
//			
//				ZonedDateTime ResultStartDate = ImProjectsSubTask.getStartDate();
//				
//				ZonedDateTime ResultEndDate = ImProjectsSubTask.getEndDate();
//				
//				double PercentCompletedSubtask = 0.0;
//				
//				double ProjectBudgetHoursSubtask = 0.0;
//
//				if (ResultStartDate == null) {
//					
//					ResultStartDate = ZonedDateTime.now();
//				}
//				if (ResultEndDate == null) {
//					
//					ResultEndDate = ResultStartDate;
//				}
//
//				if (ImProjectsSubTask.getProjectBudgetHours() != null) {
//					
//					ProjectBudgetHoursSubtask = ImProjectsSubTask.getProjectBudgetHours();
//				}
//
//				if (ImProjectsSubTask.getPercentCompleted() != null) {
//					
//					PercentCompletedSubtask = ImProjectsSubTask.getPercentCompleted();
//				}
//
//
//				double tempstartdate = convertToNumber(ResultStartDate);
//				double tempenddate = convertToNumber(ResultEndDate);
//
//				Long Setnewstartdate = (long) ((scalenew * tempstartdate + shift) / divisionnew);
//
//			
//				Long Setnewenddate = (long) (((scalenew * (tempenddate + 1) + shift) / divisionnew) - 1);
//				
//				if (updateStart) {
//					ImProjectsSubTask.setStartDate(convertToZonedDateTime(Setnewstartdate));
//					
//				}
//				if (updateEnd) {
//					ImProjectsSubTask.setEndDate(convertToZonedDateTime(Setnewenddate));
//					
//
//				}
//
//				double tempdate = tempenddate - tempstartdate + 1;
//
//				double completeHours = ProjectBudgetHoursSubtask * PercentCompletedSubtask;
//				sumCompleteHours = sumCompleteHours + completeHours;
//				sumPlanHours = sumPlanHours + ProjectBudgetHoursSubtask;
//				sumPlanDates = sumPlanDates + tempdate;
//				completdate = completdate + (tempdate * PercentCompletedSubtask);
//				
//
//			}
//
//			for (ImProjects ImProjectsSubTask : listsubtask) {
//				
//				ZonedDateTime ResultStartDate = ImProjectsSubTask.getStartDate();
//				
//				ZonedDateTime ResultEndDate = ImProjectsSubTask.getEndDate();
//			
//				double PercentCompletedSubtask = 0.0;
//				
//				double ProjectBudgetHoursSubtask = 0.0;
//
//				
//				if (ResultStartDate == null) {
//					
//					ResultStartDate = ZonedDateTime.now();
//				}
//				if (ResultEndDate == null) {
//					
//					ResultEndDate = ResultStartDate;
//				}
//
//				if (ImProjectsSubTask.getProjectBudgetHours() != null) {
//					
//					ProjectBudgetHoursSubtask = ImProjectsSubTask.getProjectBudgetHours();
//				}
//
//				if (ImProjectsSubTask.getPercentCompleted() != null) {
//					
//					PercentCompletedSubtask = ImProjectsSubTask.getPercentCompleted();
//				}
//
//
//				double tempstartdate = convertToNumber(ResultStartDate);
//				double tempenddate = convertToNumber(ResultEndDate);
//				double tempdate = tempenddate - tempstartdate + 1;
//				double new_complete_hours = 0.0;
//
//				double completeHours = ProjectBudgetHoursSubtask * PercentCompletedSubtask;
//
//				double new_plan_hours_sub_task = 0.0;
//				double new_percentage_sub_task = 0.0;
//				if (sumPlanHours != 0) {
//					new_plan_hours_sub_task = (ProjectBudgetHoursSubtask * tempRBH) / sumPlanHours;
//				} else {
//					if (completdate != 0) {
//						new_plan_hours_sub_task = ((tempenddate - tempstartdate + 1) * tempRBH) / completdate;
//					} else {
//
//					}
//				}
//				if (sumCompleteHours != 0) {
//					new_complete_hours = (completeHours * tempRBH * tempRPC) / sumCompleteHours;
//				}
//
//				if (new_plan_hours_sub_task != 0) {
//					
//					new_percentage_sub_task = new_complete_hours / new_plan_hours_sub_task;
//
//				} else {
//					new_percentage_sub_task = tempRPC;
//				}
//				
//				ImProjectsSubTask.setProjectBudgetHours(new_plan_hours_sub_task);
//				ImProjectsSubTask.setPercentCompleted(new_percentage_sub_task);
//				imProjectsRepository.save(ImProjectsSubTask);
//				
//				
//				Update(scalenew, divisionnew, shift, new_plan_hours_sub_task, new_percentage_sub_task,
//						ImProjectsSubTask, updateStart, updateEnd);
//
//			}
//
//		}
//
//	}
//
//	public void deletImProjects(ImProjects subtaskimproject) {
//		
//		if ( subtaskimproject.getReportedHoursCache() == null || subtaskimproject.getReportedHoursCache() == 0 ) {
//			
//			List<ProjectAllocation> employeeList = projectAllocationRepository.findByimProjects(subtaskimproject);
//			
//
//			if (employeeList != null) {
//
//				
//				for (ProjectAllocation projectAllocation2 : employeeList) {
//					
//					projectAllocationRepository.deleteById(projectAllocation2.getId());
//				}
//
//			}
//			
//		
//		List<ImProjects> subtask = (List<ImProjects>) imProjectsRepository.findByparentId(subtaskimproject);
//		
//		
//		
//		if (subtask != null) {
//
//			for (ImProjects imProjects2 : subtask) {
//				
//				
//					deletImProjects(imProjects2);
//					imProjectsRepository.deleteById(imProjects2.getId());
//
//				}
//
//			}
//
//		}
//	
//	}
//
//	public ImProjects duplicateImProjects(ImProjects subtaskimproject, ImProjects parentId) {
//
//		List<ImProjects> subtask = (List<ImProjects>) imProjectsRepository.findByparentId(subtaskimproject);
//		List<ProjectAllocation> employeeList = projectAllocationRepository.findByimProjects(subtaskimproject);
//		subtaskimproject.setId(null);
//		subtaskimproject.setParentId(parentId);
//		subtaskimproject.setReportedHoursCache(0.0);
//		subtaskimproject.setPercentCompleted(0.0);
//		ImProjects createdProject = imProjectsRepository.save(subtaskimproject);
//		if (employeeList != null) {
//			
//			for (ProjectAllocation projectAllocations : employeeList) {
//			
//				projectAllocations.setImProjects(createdProject);
//				projectAllocations.setId(null);
//				projectAllocationRepository.save(projectAllocations);
//			}
//
//		}
//		
//		if (subtask != null) {
//		
//			for (ImProjects subTaskProjects : subtask) {
//				duplicateImProjects(subTaskProjects, createdProject);
//			}
//		}
//		return createdProject;
//	}

	@CrossOrigin
	@PutMapping("/saveupdate")
	public ResponseEntity<ImProjects> updateImProjectsNew1(@RequestBody ProjectDetailsModel ProjectDetailsModel)
			throws URISyntaxException {

			if (ProjectDetailsModel.getId() == null) {
				throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
			} else {

				try {

					ImProjects imProjects;
				
					
					Long imProjectsID = ProjectDetailsModel.getId();
					ImProjects imProjects3 = null;

					if (imProjectsID != null) {
						Optional<ImProjects> imProjects1 = imProjectsService.findOne(imProjectsID);
						
						if (imProjects1.isPresent()) {
							imProjects3 = imProjects1.get();
						}

					

					}
					imProjects = imProjects3;
					if (imProjects == null) {
						imProjects = new ImProjects();
					}
					Double BudgetHours = ProjectDetailsModel.getProjectBudgetHours();


					String myProjectName = ProjectDetailsModel.getProjectName();
					if (myProjectName == null) {
						myProjectName = "null";
					} else {
						if (myProjectName.length() == 0) {
							myProjectName = "empty";
						}
					}
					imProjects.setProjectName(myProjectName);
					
					myProjectName = myProjectName.substring(0, Math.min(250, myProjectName.length()));
					imProjects.setProjectNr(myProjectName);
					
					imProjects.setProjectPath(myProjectName);
				

					double oldpercentcomplet = 0.0;
				

					
					if (ProjectDetailsModel.getPercentCompleted() != null) {
						
						oldpercentcomplet = ProjectDetailsModel.getPercentCompleted();
						

					}
					
					imProjects.setPercentCompleted(oldpercentcomplet);
					
					Double BudgetHours1 = 0.0;
					Double deltaBudgetHours = 0.0;
				if (BudgetHours == null) {

					BudgetHours = 0.0;
				}

				BudgetHours1 = imProjects.getProjectBudgetHours();
				if (BudgetHours1 == null) {
					BudgetHours1 = 0.0;
				}
				deltaBudgetHours = BudgetHours - BudgetHours1;
				imProjects.setProjectBudgetHours(BudgetHours1 + deltaBudgetHours);

				ZonedDateTime EndDate = ProjectDetailsModel.getEndDate();
				imProjects.setEndDate(EndDate);
				ZonedDateTime StartDate = ProjectDetailsModel.getStartDate();
				imProjects.setStartDate(StartDate);
				

				imProjects.setReportedHoursCache(ProjectDetailsModel.getReportedHoursCache());
			
				imProjects.setRisktype(ProjectDetailsModel.getRisktype());
			
				imProjects.setRiskimpact(ProjectDetailsModel.getRiskimpact());
		
				imProjects.setRiskprobability(ProjectDetailsModel.getRiskprobability());
			
				imProjects.setTrainingLink(ProjectDetailsModel.getTrainingLink());
				
				imProjects.setTrainingName(ProjectDetailsModel.getTrainingName());
			
				imProjects.setTrainingDoc(ProjectDetailsModel.getTrainingDoc());
				
				imProjects.setSortOrder(ProjectDetailsModel.getSortOrder());
			
				imProjects.setMilestoneP(ProjectDetailsModel.getMilestoneP());
				imProjects.setDescription(ProjectDetailsModel.getDescription());
				
				Long Pvid = ProjectDetailsModel.getProjectVertical();
				if (Pvid != null) {
					
					Optional<ProjectVertical> projectVertical = projectVerticalService.findOne(Pvid);

					ProjectVertical ProjectVertical2 = null;

					if (projectVertical.isPresent()) {

						ProjectVertical2 = projectVertical.get();
						;
					}

					imProjects.setProjectVertical(ProjectVertical2);
				}

				Long Pid = ProjectDetailsModel.getParentId();
				
				if (Pid != null) {
					Optional<ImProjects> imProjects1 = imProjectsService.findOne(Pid);
				
					ImProjects imProjects2 = null;

					if (imProjects1.isPresent()) {
						imProjects2 = imProjects1.get();
					}

					imProjects.setParentId(imProjects2);
				}

				Long PSid = ProjectDetailsModel.getProjectStatusId();
				if (PSid != null) {
					Optional<ProjectStatusId> projectStatusId1 = projectStatusIdService.findOne(PSid);

					ProjectStatusId ProjectStatusId2 = null;

					if (projectStatusId1.isPresent()) {

						ProjectStatusId2 = projectStatusId1.get();
						;
					}

					imProjects.setProjectStatusId(ProjectStatusId2);
				}

				Long PLid = ProjectDetailsModel.getProjectLeadId();
				if (PLid != null) {
					Optional<ImEmployee> imEmployee1 = imEmployeeService.findOne(PLid);

					ImEmployee imEmployee2 = null;

					if (imEmployee1.isPresent()) {
						imEmployee2 = imEmployee1.get();
						;
					}

					imProjects.setProjectLeadId(imEmployee2);
				}

				Long OPid = ProjectDetailsModel.getOpportunityPriorityId();
				if (OPid != null) {
					Optional<OpportunityPriorityId> opportunityPriorityId1 = opportunityPriorityIdService.findOne(OPid);

					OpportunityPriorityId opportunityPriorityId2 = null;

					if (opportunityPriorityId1.isPresent()) {
						opportunityPriorityId2 = opportunityPriorityId1.get();

					}

					imProjects.setOpportunityPriorityId(opportunityPriorityId2);
				}

				Long PTid = ProjectDetailsModel.getProjectTypeId();
				if (PTid != null) {
					Optional<ProjectTypeId> projectTypeId1 = projectTypeIdService.findOne(PTid);

					ProjectTypeId projectTypeId2 = null;

					if (projectTypeId1.isPresent()) {
						projectTypeId2 = projectTypeId1.get();

					}
					imProjects.setProjectTypeId(projectTypeId2);
				}

				Long PThid = ProjectDetailsModel.getProjectTheme();
				if (PThid != null) {
					Optional<ProjectTheme> ProjectTheme1 = projectThemeService.findOne(PThid);

					ProjectTheme ProjectTheme2 = null;

					if (ProjectTheme1.isPresent()) {
						ProjectTheme2 = ProjectTheme1.get();

					}
					imProjects.setProjectTheme(ProjectTheme2);
				}

				ImProjects result = imProjectsService.save(imProjects);
ImProjects localparent = imProjects.getParentId();

				ZonedDateTime newEndDate = result.getEndDate();
				ZonedDateTime newStartDate = result.getStartDate();
				if (newStartDate == null) {
					newStartDate = ZonedDateTime.now();
				}
				if (newEndDate == null) {
					newEndDate = newStartDate;
				}
				boolean StartDateNotNull = true;
				boolean EndDateNotNull = true;
				if (StartDate == null) {
					StartDate = ZonedDateTime.now();
					StartDateNotNull = false;
				}
				if (EndDate == null) {
					EndDate = StartDate;
					EndDateNotNull = false;
				}

				int comparisonStartDate = newStartDate.compareTo(EndDate);
				int comparison1EndDate = newEndDate.compareTo(StartDate);

				double newBudgetHours1 = result.getProjectBudgetHours();
				double newpercentcomplet = result.getPercentCompleted();
				if (newBudgetHours1 == BudgetHours1 && newpercentcomplet == oldpercentcomplet
						&& comparisonStartDate == 0 && comparison1EndDate == 0) {

				} else {
					while (localparent != null) {

						ImProjects imProjects2 = localparent;

						Double ParentBudgetHours = imProjects2.getProjectBudgetHours();
						Double NewHours = ParentBudgetHours + deltaBudgetHours;
						imProjects2.setProjectBudgetHours(NewHours);

						ZonedDateTime StartDate2 = imProjects2.getStartDate();

						if (StartDate2 == null) {
							StartDate2 = ZonedDateTime.now();
						}
						ZonedDateTime EndDate2 = imProjects2.getEndDate();

						if (EndDate2 == null) {
							EndDate2 = StartDate2;
						}
						int comparison = StartDate.compareTo(StartDate2);
						int comparison1 = EndDate.compareTo(EndDate2);

						if (comparison <= 0 && StartDateNotNull) {
						
							imProjects2.setStartDate(StartDate);

						}
						if (comparison1 >= 0 && EndDateNotNull) {

							imProjects2.setEndDate(EndDate);
							
						}
						
						if (imProjects2.getId() != null) {
							
							List<ProjectDetailsModel> list = (List<ProjectDetailsModel>) projectDetailsModelRepository
									.findByparentId(imProjects2.getId());
							
							if (list != null) {
							
								double planhours = 0.0;
								double completehours = 0.0;
								double plandate = 0.0;
								double completdate = 0.0;
								for (ProjectDetailsModel projectDetailsModel2 : list) {
									
									double tempplan = 0.0;
									double tempperc = 0.0;
									double tempdate = 0.0;
									

									double tempstartdate = 0.0;
									double tempenddate = 0.0;
									
									if (projectDetailsModel2.getStartDate() != null) {

										tempstartdate = convertToNumber(projectDetailsModel2.getStartDate());

									} else {
										tempstartdate = convertToNumber(ZonedDateTime.now());
									}

									if (projectDetailsModel2.getEndDate() != null) {

										tempenddate = convertToNumber(projectDetailsModel2.getEndDate());

									} else {
										tempenddate = tempstartdate;
									}
									tempdate = tempenddate - tempstartdate + 1;

									if (projectDetailsModel2.getProjectBudgetHours() != null) {

										tempplan = projectDetailsModel2.getProjectBudgetHours();
									

									}
									if (projectDetailsModel2.getPercentCompleted() != null) {

										tempperc = projectDetailsModel2.getPercentCompleted();
										

									}

									plandate = plandate + tempdate;
									planhours = planhours + tempplan;

									

									completehours = completehours + (tempplan * tempperc);

									completdate = completdate + (tempdate * tempperc);

								}
								if (planhours > 0) {
									imProjects2.setPercentCompleted(completehours / planhours);

								} else if (plandate > 0) {

									imProjects2.setPercentCompleted(completdate / plandate);

								}
								imProjects2.setProjectBudgetHours(planhours);

							}

						}

						ImProjects resultnew = imProjectsService.save(imProjects2);

						localparent = resultnew.getParentId();

					}

				}
				
//				Double sumPlanHours = 0.0;
//				Double sumCompleteHours = 0.0;
//				Double completeHours = 0.0;
//				Double new_plan_hours = 0.0;
//				Double new_complete_hours = 0.0;
//				Long temp = 0L;
//				Double new_percentage = 0.0;
//				double tempIBH = 0.0;
//				double tempRBH = 0.0;
//				double tempIPC = 0.0;
//				double tempRPC = 0.0;
//
//				ZonedDateTime TenpEndDate = ProjectDetailsModel.getEndDate();
//				ZonedDateTime TenpStartDate = ProjectDetailsModel.getStartDate();
//				ZonedDateTime ResultEndDate = result.getEndDate();
//				ZonedDateTime ResultStartDate = result.getStartDate();
//
//				if (TenpStartDate == null) {
//					TenpStartDate = ZonedDateTime.now();
//				}
//
//				if (TenpEndDate == null) {
//					TenpEndDate = TenpStartDate;
//				}
//				if (ResultStartDate == null) {
//					ResultStartDate = ZonedDateTime.now();
//				}
//
//				if (ResultEndDate == null) {
//					ResultEndDate = ResultStartDate;
//				}
//
//				Long scalenew = convertToNumber(TenpEndDate) + 1 - convertToNumber(TenpStartDate);
//
//				System.out.println("38 scalenew: " + scalenew);
//
//				Long divisionnew = convertToNumber(ResultEndDate) + 1 - convertToNumber(ResultStartDate);
//
//				System.out.println("38 divisionnew: " + divisionnew);
//				Long shift = (convertToNumber(ResultEndDate) + 1) * (convertToNumber(TenpStartDate))
//						- (convertToNumber(TenpEndDate) + 1) * (convertToNumber(ResultStartDate));
//
//				System.out.println("shift" + shift);
//
//
//				System.out.println("imProjects3 : " + result.toString());
//				//Update(scalenew, divisionnew, shift, new_plan_hours, new_percentage, result,StartDateNotNull,EndDateNotNull);
//
				
				System.out.println("39");
				return ResponseEntity.created(new URI("/api/saveupdate/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
						.body(result);

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error " + e);
				return new ResponseEntity<ImProjects>(HttpStatus.EXPECTATION_FAILED);
			}

		}
	}

	@CrossOrigin
	@PostMapping("/save")
	public ResponseEntity<ImProjects> createImProjectsNew3(@RequestBody ProjectDetailsModel ProjectDetailsModel)
			throws URISyntaxException {
		if (ProjectDetailsModel.getId() != null) {
			throw new BadRequestAlertException("A new imProjects cannot already have an ID", ENTITY_NAME, "idexists");
		} else {

			try {
				Double BudgetHours = ProjectDetailsModel.getProjectBudgetHours();
				
				ImProjects imProjects = new ImProjects();

				String myProjectName = ProjectDetailsModel.getProjectName();
				if (myProjectName == null) {
					myProjectName = "null";
				} else {
					if (myProjectName.length() == 0) {
						myProjectName = "empty";
					}
				}
				imProjects.setProjectName(myProjectName);
				
				myProjectName = myProjectName.substring(0, Math.min(250, myProjectName.length()));
				imProjects.setProjectNr(myProjectName);
			
				imProjects.setProjectPath(myProjectName);
			
				imProjects.setPercentCompleted(ProjectDetailsModel.getPercentCompleted());
			
				Double BudgetHours1 = 0.0;
				Double deltaBudgetHours = 0.0;

				if (BudgetHours == null) {

					BudgetHours = 0.0;
				}

				BudgetHours1 = imProjects.getProjectBudgetHours();
				if (BudgetHours1 == null) {
					BudgetHours1 = 0.0;
				}
				deltaBudgetHours = BudgetHours - BudgetHours1;
				imProjects.setProjectBudgetHours(BudgetHours1 + deltaBudgetHours);


				ZonedDateTime EndDate = ProjectDetailsModel.getEndDate();
				imProjects.setEndDate(EndDate);
				ZonedDateTime StartDate = ProjectDetailsModel.getStartDate();
				imProjects.setStartDate(StartDate);
				
				imProjects.setReportedHoursCache(ProjectDetailsModel.getReportedHoursCache());
				
				imProjects.setRisktype(ProjectDetailsModel.getRisktype());
			
				imProjects.setRiskimpact(ProjectDetailsModel.getRiskimpact());
				
				imProjects.setRiskprobability(ProjectDetailsModel.getRiskprobability());
				
				imProjects.setTrainingLink(ProjectDetailsModel.getTrainingLink());
				
				imProjects.setTrainingName(ProjectDetailsModel.getTrainingName());
				
				imProjects.setTrainingDoc(ProjectDetailsModel.getTrainingDoc());
				
				imProjects.setSortOrder(ProjectDetailsModel.getSortOrder());
				
				imProjects.setMilestoneP(ProjectDetailsModel.getMilestoneP());
				imProjects.setDescription(ProjectDetailsModel.getDescription());
				
				Long Pvid = ProjectDetailsModel.getProjectVertical();
				if (Pvid != null) {
			
					Optional<ProjectVertical> projectVertical = projectVerticalService.findOne(Pvid);

					ProjectVertical ProjectVertical2 = null;

					if (projectVertical.isPresent()) {

						ProjectVertical2 = projectVertical.get();
						;
					}

					imProjects.setProjectVertical(ProjectVertical2);
				}

				Long Pid = ProjectDetailsModel.getParentId();
			
				if (Pid != null) {
					Optional<ImProjects> imProjects1 = imProjectsService.findOne(Pid);
					
					ImProjects imProjects2 = null;

					if (imProjects1.isPresent()) {
						imProjects2 = imProjects1.get();
					}

					imProjects.setParentId(imProjects2);
				}

				Long PSid = ProjectDetailsModel.getProjectStatusId();
				if (PSid != null) {
					
					Optional<ProjectStatusId> projectStatusId1 = projectStatusIdService.findOne(PSid);

					ProjectStatusId ProjectStatusId2 = null;

					if (projectStatusId1.isPresent()) {

						ProjectStatusId2 = projectStatusId1.get();
						;
					}

					imProjects.setProjectStatusId(ProjectStatusId2);
				}

				Long PLid = ProjectDetailsModel.getProjectLeadId();
				if (PLid != null) {
					
					Optional<ImEmployee> imEmployee1 = imEmployeeService.findOne(PLid);

					ImEmployee imEmployee2 = null;

					if (imEmployee1.isPresent()) {
						imEmployee2 = imEmployee1.get();
						;
					}

					imProjects.setProjectLeadId(imEmployee2);
				}

				Long OPid = ProjectDetailsModel.getOpportunityPriorityId();
				if (OPid != null) {
					
					Optional<OpportunityPriorityId> opportunityPriorityId1 = opportunityPriorityIdService.findOne(OPid);

					OpportunityPriorityId opportunityPriorityId2 = null;

					if (opportunityPriorityId1.isPresent()) {
						opportunityPriorityId2 = opportunityPriorityId1.get();

					}

					imProjects.setOpportunityPriorityId(opportunityPriorityId2);
				}

				Long PTid = ProjectDetailsModel.getProjectTypeId();
				if (PTid != null) {
					
					Optional<ProjectTypeId> projectTypeId1 = projectTypeIdService.findOne(PTid);

					ProjectTypeId projectTypeId2 = null;

					if (projectTypeId1.isPresent()) {
						projectTypeId2 = projectTypeId1.get();

					}
					
					imProjects.setProjectTypeId(projectTypeId2);
				}

				Long PThid = ProjectDetailsModel.getProjectTheme();
				if (PThid != null) {
					
					Optional<ProjectTheme> ProjectTheme1 = projectThemeService.findOne(PThid);

					ProjectTheme ProjectTheme2 = null;

					if (ProjectTheme1.isPresent()) {
						ProjectTheme2 = ProjectTheme1.get();

					}
				
					imProjects.setProjectTheme(ProjectTheme2);
				}

				ImProjects result = imProjectsService.save(imProjects);
				
				ImProjects localparent = imProjects.getParentId();
				boolean StartDateNotNull = true;
				boolean EndDateNotNull = true;
				if (StartDate == null) {
					StartDate = ZonedDateTime.now();
					StartDateNotNull = false;
				}
				if (EndDate == null) {
					EndDate = StartDate;
					EndDateNotNull = false;
				}
				while (localparent != null) {

					ImProjects imProjects2 = localparent;

					Double ParentBudgetHours = imProjects2.getProjectBudgetHours();
					if (ParentBudgetHours == null) {
						ParentBudgetHours = 0.0;
					}
					Double NewHours = ParentBudgetHours + deltaBudgetHours;
					imProjects2.setProjectBudgetHours(NewHours);

					ZonedDateTime StartDate2 = imProjects2.getStartDate();
				ZonedDateTime EndDate2 = imProjects2.getEndDate();
				
					if (StartDate2 == null) {
						StartDate2 = ZonedDateTime.now();
					}

					if (EndDate2 == null) {
						EndDate2 = StartDate2;
					}

					int comparison = StartDate.compareTo(StartDate2);
					int comparison1 = EndDate.compareTo(EndDate2);
				
					if (comparison <= 0 && StartDateNotNull) {
						
						imProjects2.setStartDate(StartDate);

					}
					if (comparison1 >= 0 && EndDateNotNull) {

						imProjects2.setEndDate(EndDate);
						
					}
					
					if (imProjects2.getId() != null) {
						
						List<ProjectDetailsModel> list = (List<ProjectDetailsModel>) projectDetailsModelRepository
								.findByparentId(imProjects2.getId());
						
						if (list != null) {
							
							double planhours = 0.0;
							double completehours = 0.0;
							double plandate = 0.0;
							double completdate = 0.0;
							for (ProjectDetailsModel projectDetailsModel2 : list) {
							
								double tempplan = 0.0;
								double tempperc = 0.0;
								double tempdate = 0.0;


								double tempstartdate = 0.0;
								double tempenddate = 0.0;

								if (projectDetailsModel2.getStartDate() != null) {

									tempstartdate = convertToNumber(projectDetailsModel2.getStartDate());

								} else {
									tempstartdate = convertToNumber(ZonedDateTime.now());
								}

								if (projectDetailsModel2.getEndDate() != null) {

									tempenddate = convertToNumber(projectDetailsModel2.getEndDate());

								} else {
									tempenddate = tempstartdate;
								}
								tempdate = tempenddate - tempstartdate + 1;

								if (projectDetailsModel2.getProjectBudgetHours() != null) {

									tempplan = projectDetailsModel2.getProjectBudgetHours();
							
								}
								if (projectDetailsModel2.getPercentCompleted() != null) {

									tempperc = projectDetailsModel2.getPercentCompleted();
								
								}

								plandate = plandate + tempdate;
								planhours = planhours + tempplan;

								completehours = completehours + (tempplan * tempperc);

								completdate = completdate + (tempdate * tempperc);

							}
							if (planhours > 0) {
								imProjects2.setPercentCompleted(completehours / planhours);

							} else if (plandate > 0) {

								imProjects2.setPercentCompleted(completdate / plandate);
							}
							imProjects2.setProjectBudgetHours(planhours);

						}

					}
					ImProjects resultnew = imProjectsService.save(imProjects2);

					localparent = resultnew.getParentId();

				}

				return ResponseEntity.created(new URI("/api/save/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
						.body(result);

			} catch (Exception e) {
				e.printStackTrace();
		
				return new ResponseEntity<ImProjects>(HttpStatus.EXPECTATION_FAILED);
			}

		}
	}

	@CrossOrigin
	@GetMapping("/projects")
	public List<ProjectDetailsModel> getAllProjects() {
		List<ProjectDetailsModel> list = (List<ProjectDetailsModel>) projectDetailsModelRepository.findAll();

		return list;
	}

	@CrossOrigin
	@GetMapping("/projectsid/{ids}")
	public List<ProjectDetailsModel> getfindByidIn(@PathVariable("ids") List<Long> ids) {
		List<ProjectDetailsModel> list = (List<ProjectDetailsModel>) projectDetailsModelRepository.findByidIn(ids);

		return list;
	}

	@CrossOrigin
	@GetMapping("/mainprojects")
	public ResponseEntity<List<ProjectDetailsModel>> getAllParentProjects() {
		List<ProjectDetailsModel> list = (List<ProjectDetailsModel>) projectDetailsModelRepository.findAll();

		List<ProjectDetailsModel> result = list.stream().filter(d -> d.getParentId() == null)
				.collect(Collectors.toList());

		if (result != null) {
			return new ResponseEntity<List<ProjectDetailsModel>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ProjectDetailsModel>>(result, HttpStatus.NO_CONTENT);

		}
	}

	@CrossOrigin
	@GetMapping("/mainprojectsnotnull")
	public ResponseEntity<List<ProjectDetailsModel>> getAllParentProjectsnotnull() {
		List<ProjectDetailsModel> list = (List<ProjectDetailsModel>) projectDetailsModelRepository.findAll();

		List<ProjectDetailsModel> result = list.stream().filter(d -> d.getParentId() != null)
				.collect(Collectors.toList());
		if (result != null) {
			return new ResponseEntity<List<ProjectDetailsModel>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ProjectDetailsModel>>(result, HttpStatus.NO_CONTENT);

		}
	}

	@CrossOrigin
	@GetMapping("/Risktypemainprojects")
	public ResponseEntity<List<ProjectDetailsModel>> getAllParentProjectsRisktype() {
		List<ProjectDetailsModel> list = (List<ProjectDetailsModel>) projectDetailsModelRepository.findAll();

		List<ProjectDetailsModel> result = list.stream()
				.filter(d -> d.getRisktype() != null && d.getRisktype().equals("") == false)
				.collect(Collectors.toList());
		if (result != null) {
			return new ResponseEntity<List<ProjectDetailsModel>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ProjectDetailsModel>>(result, HttpStatus.NO_CONTENT);

		}
	}

	@CrossOrigin
	@GetMapping("/milestonemainprojects")
	public ResponseEntity<List<ProjectDetailsModel>> getAllParentProjectsmilestone() {
		List<ProjectDetailsModel> list = (List<ProjectDetailsModel>) projectDetailsModelRepository.findAll();

		List<ProjectDetailsModel> result = list.stream()
				.filter(d -> d.getMilestoneP() != null && d.getMilestoneP() == true).collect(Collectors.toList());
		if (result != null) {
			return new ResponseEntity<List<ProjectDetailsModel>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ProjectDetailsModel>>(result, HttpStatus.NO_CONTENT);

		}
	}

	@CrossOrigin
	@GetMapping("/childprojectsMilestone/{parentId}")

	public ResponseEntity<List<ProjectDetailsModel>> getfindByparentidMilestone(
			@PathVariable("parentId") Long parentId) {
		List<ProjectDetailsModel> list = (List<ProjectDetailsModel>) projectDetailsModelRepository
				.findByparentId(parentId);

		List<ProjectDetailsModel> result = list.stream()
				.filter(d -> d.getMilestoneP() != null && d.getMilestoneP() == true).collect(Collectors.toList());
		if (result != null) {
			return new ResponseEntity<List<ProjectDetailsModel>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ProjectDetailsModel>>(result, HttpStatus.NO_CONTENT);

		}

	}

	@CrossOrigin
	@GetMapping("/childprojectsRisktype/{parentId}")

	public ResponseEntity<List<ProjectDetailsModel>> getfindByparentidRisktype(
			@PathVariable("parentId") Long parentId) {
		List<ProjectDetailsModel> list = (List<ProjectDetailsModel>) projectDetailsModelRepository
				.findByparentId(parentId);
		List<ProjectDetailsModel> result = list.stream()
				.filter(d -> d.getRisktype() != null && d.getRisktype().equals("") == false)
				.collect(Collectors.toList());
		if (result != null) {
			return new ResponseEntity<List<ProjectDetailsModel>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ProjectDetailsModel>>(result, HttpStatus.NO_CONTENT);

		}
	}

	@CrossOrigin
	@GetMapping("/childprojects/{parentId}")

	public List<ProjectDetailsModel> getfindByparentid(@PathVariable("parentId") Long parentId) {
		List<ProjectDetailsModel> list = (List<ProjectDetailsModel>) projectDetailsModelRepository
				.findByparentId(parentId);
		return list;
	}

	
	@CrossOrigin
	@PostMapping("/projectstatusarray") 
	public ResponseEntity<List<ProjectDetailsModel>> getfindByprojectStatusNameIn1(
			@RequestBody StatusList projectStatusNames) {
		
		List<ProjectDetailsModel> list = (List<ProjectDetailsModel>) projectDetailsModelRepository
				.findByprojectStatusNameIn(projectStatusNames.getName());
		
		List<ProjectDetailsModel> result = list.stream().filter(d -> d.getParentId() == null)
				.collect(Collectors.toList());

				
		if (result != null) {
			return new ResponseEntity<List<ProjectDetailsModel>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ProjectDetailsModel>>(result, HttpStatus.NO_CONTENT);

		}
	}
	
	@CrossOrigin
	@GetMapping("/projectstatus/{projectStatusNames}/{ids}") // id in

	public List<ProjectDetailsModel> getfindByprojectStatusNameInAndId(
			@PathVariable("projectStatusNames") List<String> projectStatusNames, @PathVariable("ids") List<Long> ids) {
		List<ProjectDetailsModel> list = (List<ProjectDetailsModel>) projectDetailsModelRepository
				.findByprojectStatusNameInAndIdIn(projectStatusNames, ids);
		return list;
	}

	@CrossOrigin
	@GetMapping("/projectstatusparent/{projectStatusNames}/{parentId}")

	public List<ProjectDetailsModel> getfindByprojectStatusNameInAndParentid(
			@PathVariable("projectStatusNames") List<String> projectStatusNames,
			@PathVariable("parentId") Long parentId) {
		List<ProjectDetailsModel> list = (List<ProjectDetailsModel>) projectDetailsModelRepository
				.findByprojectStatusNameInAndParentId(projectStatusNames, parentId);
		return list;
	}

	@CrossOrigin
	@GetMapping("/projectstatusparent/{projectStatusNames}/{parentid}/{ids}")

	public List<ProjectDetailsModel> getfindByprojectStatusNameInAndParentidAndIdIn(
			@PathVariable("projectStatusNames") List<String> projectStatusNames,
			@PathVariable("parentId") Long parentId, @PathVariable("ids") List<Long> ids) {
		List<ProjectDetailsModel> list = (List<ProjectDetailsModel>) projectDetailsModelRepository
				.findByprojectStatusNameInAndParentIdAndIdIn(projectStatusNames, parentId, ids);
		return list;
	}

	@CrossOrigin
	@GetMapping("/projectsbyname/{name}")

	public List<ProjectDetailsModel> getProjectsByName(@PathVariable("name") String name) {
		List<ProjectDetailsModel> list = (List<ProjectDetailsModel>) projectDetailsModelRepository
				.findByprojectName(name);
		return list;
	}

	@CrossOrigin
	@PostMapping("/ProjectDetailsbyParent")
	public ResponseEntity<List<ProjectDetailsModel>> getAllProjectDetailsbyParent(@RequestBody ListOfId parentid) {

		List<ProjectParentDetails> resultnew = (List<ProjectParentDetails>) ProjectParentDetailsRepository
				.findByparentIdIn(parentid.getId());

		List<Long> projectid = new ArrayList<Long>();

		resultnew.forEach(item -> {

			Long subtaskid = item.getProjectid();

			if (subtaskid != null) {

				projectid.add(subtaskid);

			}

		});

		List<ProjectDetailsModel> result = (List<ProjectDetailsModel>) projectDetailsModelRepository
				.findByidIn(projectid);

		if (result != null) {
			return new ResponseEntity<List<ProjectDetailsModel>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ProjectDetailsModel>>(result, HttpStatus.NO_CONTENT);

		}
	}

	@CrossOrigin
	@GetMapping("/ProjectDetailsbyParent/{id}")
	public ResponseEntity<List<ProjectDetailsModel>> getAllProjectDetailsbyParentid(@PathVariable("id") Long id) {

		List<ProjectParentDetails> resultnew = (List<ProjectParentDetails>) ProjectParentDetailsRepository
				.findByparentId(id);

		List<Long> projectid = new ArrayList<Long>();

		if (resultnew != null) {

			resultnew.forEach(item -> {

				Long subtaskid = item.getProjectid();

				if (subtaskid != null) {

					projectid.add(subtaskid);

				}

			});
		}

		List<ProjectDetailsModel> result = (List<ProjectDetailsModel>) projectDetailsModelRepository
				.findByidIn(projectid);

		if (result != null) {
			return new ResponseEntity<List<ProjectDetailsModel>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ProjectDetailsModel>>(result, HttpStatus.NO_CONTENT);

		}
	}


//    @CrossOrigin
//    @DeleteMapping("/im-projectsDeleteNew/{id}")
//    public ResponseEntity<StringProject> deleteImProjects11(@PathVariable("id") Long id) {
//    	StringProject	 msg = new StringProject();
//    	ImProjects imProjects = imProjectsRepository.findByid(id);
//    	if(imProjects != null) {
//    
//    	if(imProjects.getReportedHoursCache()==null || imProjects.getReportedHoursCache()==0 ) {
//    	
//    		List<ImTimesheet> timesheet = ImTimesheetRepository .findByimProjects(imProjects);
//    		
//    		
//    		
//			boolean toprocid = false;
//			if (timesheet == null) {
//
//				toprocid = true;
//			} else {
//				toprocid = (timesheet.size() == 0);
//			}
//			if (toprocid) {
//		
//    
//    		if(timesheet.size() == 0 ) {
//				Double BudgetHours = imProjects.getProjectBudgetHours();
//				if(BudgetHours==null) {
//					BudgetHours=0.0;
//				}
//				double deltaBudgetHours = -BudgetHours;
//			ImProjects	localparent = imProjects.getParentId();
//    		    deletImProjects(imProjects);	
//    		    msg.setMsg("record successfully deleted"); 
//				
//				
//				while (localparent != null) {
//
//					ImProjects imProjects2 = localparent;
//
//					Double ParentBudgetHours = imProjects2.getProjectBudgetHours();
//					if (ParentBudgetHours == null) {
//						ParentBudgetHours = 0.0;
//					}
//					Double NewHours = ParentBudgetHours + deltaBudgetHours;
//					imProjects2.setProjectBudgetHours(NewHours);
//
//
//					if (imProjects2.getId() != null) {
//						
//						List<ProjectDetailsModel> list = (List<ProjectDetailsModel>) projectDetailsModelRepository
//								.findByparentId(imProjects2.getId());
//						
//						if (list != null) {
//							
//							double planhours = 0.0;
//							double completehours = 0.0;
//							double plandate = 0.0;
//							double completdate = 0.0;
//							for (ProjectDetailsModel projectDetailsModel2 : list) {
//								
//								double tempplan = 0.0;
//								double tempperc = 0.0;
//								double tempdate = 0.0;
//
//
//								double tempstartdate = 0.0;
//								double tempenddate = 0.0;
//
//								if (projectDetailsModel2.getStartDate() != null) {
//
//									tempstartdate = convertToNumber(projectDetailsModel2.getStartDate());
//
//								} else {
//									tempstartdate = convertToNumber(ZonedDateTime.now());
//								}
//
//								if (projectDetailsModel2.getEndDate() != null) {
//
//									tempenddate = convertToNumber(projectDetailsModel2.getEndDate());
//
//								} else {
//									tempenddate = tempstartdate;
//								}
//								tempdate = tempenddate - tempstartdate + 1;
//
//								if (projectDetailsModel2.getProjectBudgetHours() != null) {
//
//									tempplan = projectDetailsModel2.getProjectBudgetHours();
//								}
//								if (projectDetailsModel2.getPercentCompleted() != null) {
//
//									tempperc = projectDetailsModel2.getPercentCompleted();
//								
//								}
//
//								plandate = plandate + tempdate;
//								planhours = planhours + tempplan;
//
//								completehours = completehours + (tempplan * tempperc);
//
//								completdate = completdate + (tempdate * tempperc);
//							}
//								if (planhours > 0) {
//									imProjects2.setPercentCompleted(completehours / planhours);
//									
//								} else if (plandate > 0) {
//
//									imProjects2.setPercentCompleted(completdate / plandate);
//								}
//								imProjects2.setProjectBudgetHours(planhours);
//							}
//
//						}
//
//					
//					ImProjects resultnew = imProjectsService.save(imProjects2);
//
//					localparent = resultnew.getParentId();
//
//				}
//
//       			return new ResponseEntity<StringProject>(msg, HttpStatus.OK);
//    			
//    			
//    		}else {
//    			
//    			msg.setMsg("For this project timesheet are present , you can't delete Task");
//
//    			return new ResponseEntity<StringProject>(msg, HttpStatus.NO_CONTENT);
//    		}
//    		
//    	}
//			msg.setMsg("For this project timesheet are present , you can't delete Task");
//    		return new ResponseEntity<StringProject>(msg, HttpStatus.OK);
//
//    	}else {
//    		msg.setMsg("For this project reported Hours are present , you can't delete Task");
//    		
//    		return new ResponseEntity<StringProject>(msg, HttpStatus.OK);
//    	}
//    	
//    	}else {
//    		msg.setMsg("Invalid request");
//    		return new ResponseEntity<StringProject>(msg, HttpStatus.NO_CONTENT);
//    	}
//    	
//    }
//    
//
//
//
//	
//	
//	@CrossOrigin
//	@GetMapping("/im-projectsclone/{id}")
//	public ResponseEntity<ImProjects> cloneImProjects(@PathVariable("id") Long id) {
//
//		ImProjects imProjects =   imProjectsRepository.findByid(id);
//		String msg = null;
//		if(imProjects != null) {
//			ImProjects localparent = imProjects.getParentId();
//			
//			imProjects.setProjectName(imProjects.getProjectName() + "-(copy)");
//			ImProjects createdProject = duplicateImProjects(imProjects,localparent);
//			msg = "record successfully duplcated" ; 
//			Double BudgetHours = createdProject.getProjectBudgetHours();
//			if (BudgetHours == null) {
//				BudgetHours = 0.0;
//			}
//			double deltaBudgetHours = BudgetHours;
//			while (localparent != null) {
//
//				ImProjects imProjects2 = localparent;
//
//				Double ParentBudgetHours = imProjects2.getProjectBudgetHours();
//				if (ParentBudgetHours == null) {
//					ParentBudgetHours = 0.0;
//				}
//				Double NewHours = ParentBudgetHours + deltaBudgetHours;
//				imProjects2.setProjectBudgetHours(NewHours);
//
//
//				if (imProjects2.getId() != null) {
//				
//					List<ProjectDetailsModel> list = (List<ProjectDetailsModel>) projectDetailsModelRepository
//							.findByparentId(imProjects2.getId());
//					
//					if (list != null) {
//						
//						double planhours = 0.0;
//						double completehours = 0.0;
//						double plandate = 0.0;
//						double completdate = 0.0;
//						for (ProjectDetailsModel projectDetailsModel2 : list) {
//							
//							double tempplan = 0.0;
//							double tempperc = 0.0;
//							double tempdate = 0.0;
//							
//
//							double tempstartdate = 0.0;
//							double tempenddate = 0.0;
//
//							if (projectDetailsModel2.getStartDate() != null) {
//
//								tempstartdate = convertToNumber(projectDetailsModel2.getStartDate());
//
//							} else {
//								tempstartdate = convertToNumber(ZonedDateTime.now());
//							}
//
//							if (projectDetailsModel2.getEndDate() != null) {
//
//								tempenddate = convertToNumber(projectDetailsModel2.getEndDate());
//
//							} else {
//								tempenddate = tempstartdate;
//							}
//							tempdate = tempenddate - tempstartdate + 1;
//
//							if (projectDetailsModel2.getProjectBudgetHours() != null) {
//
//								tempplan = projectDetailsModel2.getProjectBudgetHours();
//							
//							}
//							if (projectDetailsModel2.getPercentCompleted() != null) {
//
//								tempperc = projectDetailsModel2.getPercentCompleted();
//					
//							}
//
//							plandate = plandate + tempdate;
//							planhours = planhours + tempplan;
//
//							completehours = completehours + (tempplan * tempperc);
//
//							completdate = completdate + (tempdate * tempperc);
//						}
//						if (planhours > 0) {
//							imProjects2.setPercentCompleted(completehours / planhours);
//
//						} else if (plandate > 0) {
//
//							imProjects2.setPercentCompleted(completdate / plandate);
//						}
//						imProjects2.setProjectBudgetHours(planhours);
//
//					}
//
//				}
//
//
//				ImProjects resultnew = imProjectsService.save(imProjects2);
//
//				localparent = resultnew.getParentId();
//
//
//			}
//		
//			  msg= "project clone successfully " ;
//			return new ResponseEntity<ImProjects>(createdProject, HttpStatus.OK);
//
//
//		}else {
//			
//			msg="Project Not found";
//
//			return new ResponseEntity<ImProjects>(new ImProjects(), HttpStatus.NO_CONTENT);
//		}
//
//	}	

    @CrossOrigin
    @GetMapping("/ListAllProjectsView/{userid}")
    public ResponseEntity<?> agentDashboarddetail(@PathVariable long userid){
        ResponseEntity<?> response=null;
        try {
            ProjectsView lst = agentDashboardService.ImProjectsIdJson(userid);
            response = new ResponseEntity<>(lst, HttpStatus.OK);
            
        } catch (Exception e) {
            response = new ResponseEntity<>("Failed", HttpStatus.OK);
            e.printStackTrace();
           
        }
        return response;
    }

    @CrossOrigin
    @GetMapping("/ListImProjectsJsonModel")
    public ResponseEntity<?> improjectsjsonmodel(){
        ResponseEntity<?> response=null;
        try {
            ImProjectsJsonModel lst = agentDashboardService.ImProjectsDetailsJson();
            response = new ResponseEntity<>(lst, HttpStatus.OK);
            
        } catch (Exception e) {
            response = new ResponseEntity<>("Failed", HttpStatus.OK);
            e.printStackTrace();
           
        }
        return response;
    }
  
}



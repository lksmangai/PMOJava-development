package com.qnowapp.web.rest;

import com.qnowapp.domain.BacklogPractice;

import com.qnowapp.domain.Blank;
import com.qnowapp.domain.ImEmployee;
import com.qnowapp.domain.ImProjects;
import com.qnowapp.domain.OpportunityPriorityId;
import com.qnowapp.domain.ProjectBucketId;
import com.qnowapp.domain.ProjectBusinessgoalId;
import com.qnowapp.domain.ProjectClass;
import com.qnowapp.domain.ProjectCostCenterId;
import com.qnowapp.domain.ProjectInitiativeId;
import com.qnowapp.domain.ProjectMaingoalId;
import com.qnowapp.domain.ProjectRoles;
import com.qnowapp.domain.ProjectStatusId;
import com.qnowapp.domain.ProjectSubgoalId;
import com.qnowapp.domain.ProjectTheme;
import com.qnowapp.domain.ProjectTypeId;
import com.qnowapp.domain.ProjectVertical;
import com.qnowapp.domain.QnowUser;
import com.qnowapp.domain.User;
import com.qnowapp.repository.BacklogPracticeRepository;
import com.qnowapp.repository.ImEmployeeRepository;
import com.qnowapp.repository.ImProjectsRepository;
import com.qnowapp.repository.OpportunityPriorityIdRepository;
import com.qnowapp.repository.ProjectBucketIdRepository;
import com.qnowapp.repository.ProjectBusinessgoalIdRepository;
import com.qnowapp.repository.ProjectClassRepository;
import com.qnowapp.repository.ProjectCostCenterIdRepository;
import com.qnowapp.repository.ProjectInitiativeIdRepository;
import com.qnowapp.repository.ProjectMaingoalIdRepository;
import com.qnowapp.repository.ProjectStatusIdRepository;
import com.qnowapp.repository.ProjectSubgoalIdRepository;
import com.qnowapp.repository.ProjectThemeRepository;
import com.qnowapp.repository.ProjectTypeIdRepository;
import com.qnowapp.repository.ProjectVerticalRepository;
import com.qnowapp.repository.QnowUserRepository;


import com.qnowapp.service.ImProjectsService;
import com.qnowapp.service.dto.ImProjectsCriteria;

import com.qnowapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.qnowapp.domain.ImProjects}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ImProjectsResource {


    ProjectInitiativeId projectInitiativeId = new ProjectInitiativeId();

    ProjectBusinessgoalId projectBusinessgoalId = new ProjectBusinessgoalId();

    ProjectSubgoalId projectSubgoalId = new ProjectSubgoalId();

    ProjectMaingoalId projectMaingoalId = new ProjectMaingoalId();

    ProjectBucketId projectBucketId = new ProjectBucketId();

    ProjectCostCenterId projectCostCenterId = new ProjectCostCenterId();

    OpportunityPriorityId opportunityPriorityId = new OpportunityPriorityId();

    BacklogPractice backlogPractice = new BacklogPractice();

    ProjectStatusId projectStatusId = new ProjectStatusId();

    ProjectTypeId projectTypeId = new ProjectTypeId();

    ProjectClass projectClass = new ProjectClass();

    ProjectVertical projectVertical = new ProjectVertical();

    ProjectTheme projectTheme = new ProjectTheme();

    ImEmployee imEmployee = new ImEmployee();
    
    HashMap<String, ImProjects> myhash = new HashMap<String, ImProjects>();

    @Autowired
    ProjectInitiativeIdRepository projectInitiativeIdRepository;

    @Autowired
    ProjectBusinessgoalIdRepository projectBusinessgoalIdRepository;

    @Autowired
    ProjectSubgoalIdRepository projectSubgoalIdRepository;

    @Autowired
    ProjectMaingoalIdRepository projectMaingoalIdRepository;

    @Autowired
    ProjectBucketIdRepository projectBucketIdRepository;

    @Autowired
    ProjectCostCenterIdRepository projectCostCenterIdRepository;

    @Autowired
    OpportunityPriorityIdRepository opportunityPriorityIdRepository;

    @Autowired
    BacklogPracticeRepository backlogPracticerRepository;

    @Autowired
    ProjectStatusIdRepository projectStatusIdRepository;

    @Autowired
    ProjectTypeIdRepository projectTypeIdRepository;

    @Autowired
    ProjectClassRepository projectClassRepository;

    @Autowired
    ProjectVerticalRepository projectVerticalRepository;

    @Autowired
    ProjectThemeRepository projectThemeRepository;

    @Autowired
    ImEmployeeRepository imEmployeeRepository;

    @Autowired
    ImProjectsRepository imProjectsRepository;
    
    
    
    private final Logger log = LoggerFactory.getLogger(ImProjectsResource.class);

    private static final String ENTITY_NAME = "imProjects";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;


    private static Boolean fromTesting = false;
    private final ImProjectsService imProjectsService;


	public ImProjectsResource( ImProjectsService imProjectsService) {
	
		this.imProjectsService = imProjectsService;
   
	}



    public static void setFromTesting(Boolean bState) {
        fromTesting = bState;
    }

    public String removeQuotes(String MyEmail) {
        if (MyEmail == null) {
            return null;

        }
        if (MyEmail.equals("")) {
           
            return MyEmail;

        }
        String doubleQuote = "\"";
        if (MyEmail.substring(0, 1).equals(doubleQuote)) {
            MyEmail = MyEmail.substring(1, MyEmail.length());
        }

        if (MyEmail.substring(MyEmail.length() - 1, MyEmail.length()).equals(doubleQuote)) {

            MyEmail = MyEmail.substring(0, MyEmail.length() - 1);

        }
        return MyEmail;
    }

    public Integer convertToInteger(String integer) {

        if (integer.equals("")) {
            return 0;
        } else {
            try {
                Float fl = Float.parseFloat(integer);
                Integer i = Math.round(fl);
                return i;
            } catch (Exception e) {
                
            }

        }
        return 0;

    }

    public BigDecimal convertToBigDecimal(String integer) {

        if (integer.equals("")) {
            return new BigDecimal(0);
        } else {
            try {
                Float fl = Float.parseFloat(integer);

                return new BigDecimal(fl);
            } catch (Exception e) {
                
            }

        }
        return new BigDecimal(0);

    }

    public Double convertToDouble(String integer) {

        if (integer.equals("") || integer == null) {
            return new Double(0);
        } else {
            try {
                Float fl = Float.parseFloat(integer);

                return new Double(fl);
            } catch (Exception e) {
               
            }

        }
        return new Double(0);

    }

    public ZonedDateTime convertToZonedDateTime(String integer) {

        if (integer.equals("")) {
            return null;
        } else {
            try {
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime mydate = LocalDateTime.parse(integer.substring(0, 19), formatter1);
                ZonedDateTime resultado = ZonedDateTime.ofLocal(mydate, ZoneOffset.UTC, null);
                return resultado;
            } catch (Exception e) {
            	System.out.println(e);
            }

        }

        return null;
    }

    public Boolean setboolean(String Myboolean) {
        if (Myboolean == null) {
            return null;

        }
        if (Myboolean.equals("t")) {
            
            return true;

        }
       
        if (Myboolean.equals("f")) {
           return false;
        }
        return null;

    }

    /*
     * String a = "2018-03-26 00:00:00-05"; DateTimeFormatter formatter1 =
     * DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); LocalDateTime mydate =
     * LocalDateTime.parse(a.substring(0, 19), formatter1); ZonedDateTime resultado
     * = ZonedDateTime.ofLocal(mydate, ZoneOffset.UTC, null);
     */
    @CrossOrigin
    @PostMapping("/im-projects")
    public ResponseEntity<ImProjects> createImProjects(@Valid @RequestBody ImProjects imProjects) throws URISyntaxException {
        log.debug("REST request to save ImProjects : {}", imProjects);
        if (imProjects.getId() != null) {
            throw new BadRequestAlertException("A new imProjects cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImProjects result = imProjectsService.save(imProjects);
        return ResponseEntity.created(new URI("/api/im-projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    
    @CrossOrigin
    @PostMapping("/im-projectscsv")
    public ResponseEntity<ImProjects> createImProjectscsv(@Valid @RequestBody Blank blank)
            throws URISyntaxException {
     
            ImProjects result = new ImProjects();
            long a=new Long(1);
            result.setId(a);
            result.setProjectName("demo");
            result.setProjectNr("demo");
            ImProjects imProjects = new ImProjects();
            ResponseEntity<ImProjects> returnObj = ResponseEntity
                    .created(new URI("/api/im-projects/" + result.getId())).headers(HeaderUtil
                            .createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                    .body(result);
            if (fromTesting == false) {
                List<ProjectMaingoalId> myMaingoal = projectMaingoalIdRepository.findAll();
                List<ProjectSubgoalId> mySubgoal = projectSubgoalIdRepository.findAll();
                List<ImEmployee> myEmployee = imEmployeeRepository.findAll();
                List<ProjectTheme> myTheme = projectThemeRepository.findAll();
                List<ProjectVertical> myVertical = projectVerticalRepository.findAll();
                List<ProjectTypeId> myType = projectTypeIdRepository.findAll();
                List<BacklogPractice> myBacklog = backlogPracticerRepository.findAll();
                List<ProjectClass> myClass = projectClassRepository.findAll();
                List<ProjectStatusId> myStatus = projectStatusIdRepository.findAll();
                List<OpportunityPriorityId> myPriority = opportunityPriorityIdRepository.findAll();
                List<ProjectCostCenterId> myCost = projectCostCenterIdRepository.findAll();
                List<ProjectInitiativeId> myInitiative = projectInitiativeIdRepository.findAll();
                List<ProjectBucketId> myBucket = projectBucketIdRepository.findAll();
                List<ProjectBusinessgoalId> myBusinessgoal = projectBusinessgoalIdRepository.findAll();
                
               
                String csvFile1 = "F:\\PMO1\\src\\main\\resources\\pmqprojects06Jul2019.csv";
                BufferedReader br = null;
                String line = "";
                String cvsSplitBy = ";";

                try {
                    br = new BufferedReader(new FileReader(csvFile1));
                    int count = 0;
                    while ((line = br.readLine()) != null) {
                        count++;
                       
                        if (count == 1)
                            continue;
                        line = line + ";test";
                        String[] country = line.split(cvsSplitBy);
                       
                        try {
                            if (country.length >= 117) {
                               
                                imProjects.setId(null);
                                String s1 = removeQuotes(country[1]);
                                String ProjectName = s1.replace("$", ";");
                                imProjects.setProjectName(ProjectName);

                               
                                imProjects.setProjectNr(removeQuotes(country[2]));

                                
                                imProjects.setProjectPath(removeQuotes(country[4]));

                               
                                imProjects.setTreeSortkey(removeQuotes(country[6]));

                               
                                imProjects.setMaxChildSortkey(removeQuotes(country[7]));

                     
                                String s2 = removeQuotes(country[11]);
                                String description = s1.replace("$", ";");
                                imProjects.setDescription(description);

                               
                                imProjects.setNote(removeQuotes(country[13]));

                    
                                imProjects.setMaxChildSortkey(removeQuotes(country[7]));
                       
                                imProjects.setBillingTypeId(convertToInteger(removeQuotes(country[12])));

                 
                                imProjects.setNote(removeQuotes(country[3]));

                              
                           
                               imProjects.setRequiresReportP(setboolean(removeQuotes(country[16])));
                            	
                                imProjects.setProjectBudget(convertToDouble(removeQuotes(country[17])));

                                imProjects.setProjectRisk(removeQuotes(country[18]));

                                imProjects.setCorporateSponsor(removeQuotes(country[19]));

                                imProjects.setPercentCompleted(convertToDouble(country[21]));

                                imProjects.setProjectBudgetHours(convertToDouble(removeQuotes(country[23])));

                                imProjects.setCostQuotesCache(convertToBigDecimal(removeQuotes(country[24])));

                                imProjects.setCostInvoicesCache(convertToInteger(removeQuotes(country[25])));

                                imProjects.setCostTimesheetPlannedCache(convertToInteger(removeQuotes(country[26])));

                                imProjects.setCostPurchaseOrdersCache(convertToInteger(removeQuotes(country[27])));

                                imProjects.setCostBillsCache(convertToInteger(removeQuotes(country[28])));

                                imProjects.setCostTimesheetLoggedCache(convertToInteger(removeQuotes(country[29])));

                                imProjects.setEndDate(convertToZonedDateTime(removeQuotes(country[30])));

                                imProjects.setStartDate(convertToZonedDateTime(removeQuotes(country[31])));

                                imProjects.setTemplateP(setboolean(removeQuotes(country[32])));

                                imProjects.setSortOrder(convertToInteger(removeQuotes(country[33])));

                                imProjects.setReportedHoursCache(convertToDouble(removeQuotes(country[34])));

                                imProjects.setCostExpensePlannedCache(convertToInteger(removeQuotes(country[35])));

                                imProjects.setCostExpenseLoggedCache(convertToInteger(removeQuotes(country[36])));

                                imProjects.setCostDeliveryNotesCache(convertToBigDecimal(removeQuotes(country[38])));

                          
                                imProjects.setMilestoneP(setboolean(removeQuotes(country[40])));

                                imProjects.setReleaseItemP(removeQuotes(country[41]));

                                imProjects.setPresalesProbability(convertToBigDecimal(removeQuotes(country[42])));

                                imProjects.setPresalesValue(convertToBigDecimal(removeQuotes(country[43])));

                                imProjects.setReportedDaysCache(convertToBigDecimal(removeQuotes(country[44])));

                                imProjects.setPresalesValueCurrency(removeQuotes(country[50]));

                                imProjects.setOpportunitySalesStageId(convertToInteger(removeQuotes(country[52])));

                                imProjects.setOpportunityCampaignId(convertToInteger(removeQuotes(country[53])));

                                imProjects.setScoreRevenue(convertToBigDecimal(removeQuotes(country[54])));

                                imProjects.setScoreStrategic(convertToBigDecimal(removeQuotes(country[55])));

                                imProjects.setScoreFinanceNpv(convertToBigDecimal(removeQuotes(country[56])));

                                imProjects.setScoreCustomers(convertToBigDecimal(removeQuotes(country[57])));

                                imProjects.setScoreFinanceCost(convertToBigDecimal(removeQuotes(country[58])));

                                imProjects.setCostBillsPlanned(convertToBigDecimal(removeQuotes(country[53])));

                                imProjects.setCostExpensesPlanned(convertToBigDecimal(removeQuotes(country[60])));

                                imProjects.setScoreRisk(convertToBigDecimal(removeQuotes(country[61])));

                                imProjects.setScoreCapabilities(convertToBigDecimal(removeQuotes(country[62])));
                        
                                imProjects.setScoreEinanceRoi(convertToBigDecimal(removeQuotes(country[63])));

                                imProjects.setProjectUserwiseBoard(removeQuotes(country[77]));

                                imProjects.setProjectBringNextday(convertToInteger(removeQuotes(country[78])));

                                imProjects.setProjectBringSameboard(removeQuotes(country[79]));

                                imProjects.setProjectNewboardEverytime(removeQuotes(country[80]));

                                imProjects.setProjectUserwiseBoard2(removeQuotes(country[82]));

                                imProjects.setProjectBringSameboard2(removeQuotes(country[83]));

                                imProjects.setProjectNewboard2Everytime(convertToInteger(removeQuotes(country[84])));

                                imProjects.setProjectNewboard2Always(removeQuotes(country[85]));

                                imProjects.setProjectReportWeekly(removeQuotes(country[86]));

                                imProjects.setScoreGain(convertToDouble(removeQuotes(country[88])));

                                imProjects.setScoreLoss(convertToDouble(removeQuotes(country[89])));

                                imProjects.setScoreDelivery(convertToDouble(removeQuotes(country[90])));

                                imProjects.setScoreOperations(convertToDouble(removeQuotes(country[91])));

                                imProjects.setScoreWhy(convertToInteger(removeQuotes(country[92])));

                                imProjects.setNetServices(removeQuotes(country[97]));

                                imProjects.setTrainingLink(removeQuotes(country[99]));

                                imProjects.setCollectionName(removeQuotes(country[100]));

                                imProjects.setTrainingName(removeQuotes(country[101]));

                                imProjects.setTrainingDoc(removeQuotes(country[102]));

                                imProjects.setTestingRichtext(convertToInteger(removeQuotes(country[103])));

                                imProjects.setTemplateCategory(removeQuotes(country[104]));

                                imProjects.setdType(convertToInteger(removeQuotes(country[105])));

                                imProjects.setdOption(convertToInteger(removeQuotes(country[106])));

                                imProjects.setdFilter(convertToInteger(removeQuotes(country[107])));

                                imProjects.setdId(convertToInteger(removeQuotes(country[108])));

                                imProjects.settType(convertToInteger(removeQuotes(country[109])));

                                imProjects.settOption(convertToInteger(removeQuotes(country[110])));

                                imProjects.settFilter(convertToInteger(removeQuotes(country[111])));

                                imProjects.settId(convertToInteger(removeQuotes(country[112])));

                                imProjects.setRisktype(removeQuotes(country[113]));

                                imProjects.setRiskimpact(convertToDouble(removeQuotes(country[114])));

                                String test = removeQuotes(country[115]);
                            
                                Double test2 = convertToDouble(test);
                             
                                imProjects.setRiskprobability(test2);

                                Boolean found13 = false;
                               
                                String Initiative = removeQuotes(country[68]);
                               
                                imProjects.setProjectInitiativeId(null);
                              
                                if (Initiative.equals("") == false && Initiative != null) {
                                    
                                    myInitiative.forEach(item -> {
                                     
                                        String myPath = item.getCode();
                                        
                                        if (myPath.equals(Initiative)) {
                                           
                                            imProjects.setProjectInitiativeId(item); // 68
                                           
                                        }
                                       
                                    });
                                }
                               
                                Boolean found12 = false;
                                String Businessgoal = removeQuotes(country[67]);
                                imProjects.setProjectBusinessgoalId(null);
                                if (Businessgoal.equals("") == false && Businessgoal != null) {
                                    myBusinessgoal.forEach(item -> {
                                        String myPath = item.getCode();
                                        if (myPath.equals(Businessgoal)) {
                                          
                                            imProjects.setProjectBusinessgoalId(item); // 67

                                        }

                                    });
                                }

                                
                                Boolean found11 = false;
                                String Subgoal = removeQuotes(country[66]);
                                imProjects.setProjectSubgoalId(null);
                                if (Subgoal.equals("") == false && Subgoal != null) {
                                    mySubgoal.forEach(item -> {
                                        String myPath = item.getCode();
                                        if (myPath.equals(Subgoal)) {
                                         
                                            imProjects.setProjectSubgoalId(item); // 66
                                        }

                                    });
                                }

                                
                                Boolean found10 = false;
                                String Maingoal = removeQuotes(country[65]);
                                imProjects.setProjectMaingoalId(null);
                                if (Maingoal.equals("") == false && Maingoal != null) {
                                    myMaingoal.forEach(item -> {
                                        String myPath = item.getCode();
                                        if (myPath.equals(Maingoal)) {
                                            
                                            imProjects.setProjectMaingoalId(item); // 65
                                        }

                                    });
                                }

                                
                                Boolean found9 = false;
                                String Bucket = removeQuotes(country[64]);
                                imProjects.setProjectBucketId(null);
                                if (Bucket.equals("") == false && Bucket != null) {
                                    myBucket.forEach(item -> {
                                        String myPath = item.getCode();
                                        if (myPath.equals(Bucket)) {
                                            
                                            imProjects.setProjectBucketId(item); // 64
                                        }

                                    });
                                }

                                
                                Boolean found8 = false;
                                String Cost = removeQuotes(country[46]);
                                imProjects.setProjectCostCenterId(null);
                                if (Cost.equals("") == false && Cost != null) {
                                    myCost.forEach(item -> {
                                        String myPath = item.getCode();
                                        if (myPath.equals(Cost)) {
                                            
                                            imProjects.setProjectCostCenterId(item);// 46
                                        }

                                    });
                                }

                                
                                Boolean found7 = false;
                                String Priority = removeQuotes(country[51]);
                                imProjects.setOpportunityPriorityId(null);
                                if (Priority.equals("") == false && Priority != null) {
                                    myPriority.forEach(item -> {
                                        String myPath = item.getCode();
                                        if (myPath.equals(Priority)) {
                                          
                                            imProjects.setOpportunityPriorityId(item); // 51
                                        }

                                    });
                                }

                                
                                Boolean found6 = false;
                                String Backlog = removeQuotes(country[87]);
                                imProjects.setBacklogPractice(null);
                                if (Backlog.equals("") == false && Backlog != null) {
                                    myBacklog.forEach(item -> {
                                        String myPath = item.getCode();
                                        if (myPath.equals(Backlog)) {
                                           
                                            imProjects.setBacklogPractice(item);// 87
                                        }

                                    });
                                }

                                
                                Boolean found5 = false;
                                String Status = removeQuotes(country[10]);
                                imProjects.setProjectStatusId(null);
                                if (Status.equals("") == false && Status != null) {
                                    myStatus.forEach(item -> {
                                        String myPath = item.getCode();
                                        if (myPath.equals(Status)) {
                                            
                                            imProjects.setProjectStatusId(item); // 10
                                        }

                                    });
                                }


                                Boolean found4 = false;
                                String Type = removeQuotes(country[9]);
                                imProjects.setProjectTypeId(null);
                                if (Type.equals("") == false && Type != null) {
                                    myType.forEach(item -> {
                                        String myPath = item.getCode();
                                        if (myPath.equals(Type)) {
                                           
                                            imProjects.setProjectTypeId(item); // 9

                                        }

                                    });
                                }

                                
                                Boolean found3 = false;
                                String Class = removeQuotes(country[94]);
                                imProjects.setProjectClass(null);
                                if (Class.equals("") == false && Class != null) {
                                    myClass.forEach(item -> {
                                        String myPath = item.getCode();
                                        if (myPath.equals(Class)) {
                                            
                                            imProjects.setProjectClass(item); // 94
                                            
                                        }
                                       
                                    });
                                    
                                }
                             
                                Boolean found2 = false;
                                String vertical = removeQuotes(country[95]);
                                imProjects.setProjectVertical(null);
                                if (vertical.equals("") == false && vertical != null) {
                                    myVertical.forEach(item -> {
                                        String myPath = item.getCode();
                                        if (myPath.equals(vertical)) {
                                            
                                            imProjects.setProjectVertical(item); // 95

                                        }

                                    });
                                }

                                
                                Boolean found1 = false;
                                String Theme = removeQuotes(country[93]);
                                imProjects.setProjectTheme(null);
                                if (Theme.equals("") == false && Theme != null) {
                                    myTheme.forEach(item -> {
                                        String myPath = item.getCode();
                                        if (myPath.equals(Theme)) {
                                        	imProjects.setProjectTheme(item); // 93

                                        }

                                    });
                                }

                                
                                Boolean founda = false;
                              

                                String Employee = removeQuotes(country[117]);
                            
                                imProjects.setProjectLeadId(null);
                               
                                if (Employee.equals("") == false && Employee != null) {
                                   
                                    myEmployee.forEach(item -> {
                                       
                                        QnowUser QuserHere = item.getQnowUser();
                                       
                                        if (QuserHere != null) {
                                            
                                            User userHere = QuserHere.getUser();
                                      
                                            if (userHere != null) {
                                             
                                                String myEmailHere = userHere.getEmail();
                                              
                                                if (myEmailHere.equals(Employee)) {
                                            
                                                    imProjects.setProjectLeadId(item);// 117
                                                    
                                                }
                                            }
                                        }

                                    });
                                }

                                
                                
                                Boolean found = false;
                                String myParentId = removeQuotes(country[5]);
                                imProjects.setParentId(null);
                                if (myParentId.equals("") == false && myParentId!=null ) {
                                  
                                    ImProjects value = myhash.get(myParentId);
                                   
                                    if (value == null)
                                    {
                                    List<ImProjects> myProjects = imProjectsRepository.findAll();
                                    myProjects.forEach(item -> {
                                        String myPath = item.getProjectPath();
                                        if (myPath.equals(myParentId)) {
                                           
                                            imProjects.setParentId(item); // 5
                                            myhash.put(myParentId, item);
                                        }
                                    
                                    });
                                }else {
                                   
                                    imProjects.setParentId(value);
                                 
                                }
                                }
                              
                                imProjects.setId(null);
                                ImProjects result2 = imProjectsRepository.save(imProjects);

                               
                            }
                        } catch (Exception e) {
                        	System.out.println(e + "e3");
                            return returnObj;
                        }
                    }
                } catch (Exception e) {
                	System.out.println(e + "e2");
                    return returnObj;
                } finally {
                    if (br != null) {
                        try {
                            br.close();
                        } catch (Exception e) {
                        	System.out.println(e + "e1");
                        }
                    }
                }

            }

            return returnObj;

        }

    

    /**
     * {@code PUT  /im-projects} : Updates an existing imProjects.
     *
     * @param imProjects the imProjects to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imProjects,
     * or with status {@code 400 (Bad Request)} if the imProjects is not valid,
     * or with status {@code 500 (Internal Server Error)} if the imProjects couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/im-projects")
    public ResponseEntity<ImProjects> updateImProjects(@Valid @RequestBody ImProjects imProjects) throws URISyntaxException {
        log.debug("REST request to update ImProjects : {}", imProjects);
        if (imProjects.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ImProjects result = imProjectsService.save(imProjects);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, imProjects.getId().toString()))
            .body(result);
    }

    
    
    
    
  
    /**
     * {@code GET  /im-projects} : get all the imProjects.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of imProjects in body.
     */
    @CrossOrigin
    @GetMapping("/im-projects")
    public ResponseEntity<List<ImProjects>> getAllImProjects( ) {
        log.debug("REST request to get ImProjects by criteria: {}");
        List<ImProjects> entityList = imProjectsService.findAll();
        return ResponseEntity.ok().body(entityList);
    }



    /**
     * {@code GET  /im-projects/:id} : get the "id" imProjects.
     *
     * @param id the id of the imProjects to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the imProjects, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/im-projects/{id}")
    public ResponseEntity<ImProjects> getImProjects(@PathVariable Long id) {
        log.debug("REST request to get ImProjects : {}", id);
        Optional<ImProjects> imProjects = imProjectsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(imProjects);
    }

    /**
     * {@code DELETE  /im-projects/:id} : delete the "id" imProjects.
     *
     * @param id the id of the imProjects to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/im-projects/{id}")
    public ResponseEntity<Void> deleteImProjects(@PathVariable Long id) {
        log.debug("REST request to delete ImProjects : {}", id);
        imProjectsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
	
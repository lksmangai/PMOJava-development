package com.qnowapp.web.rest;

import com.qnowapp.domain.ImEmployee;
import com.qnowapp.domain.ImProjects;
import com.qnowapp.domain.ProjectAllocation;
import com.qnowapp.domain.ProjectClass;
import com.qnowapp.domain.ProjectRoles;
import com.qnowapp.domain.QnowUser;
import com.qnowapp.domain.User;
import com.qnowapp.repository.ImEmployeeRepository;
import com.qnowapp.repository.ImProjectsRepository;
import com.qnowapp.repository.ProjectAllocationRepository;
import com.qnowapp.repository.ProjectRolesRepository;

import com.qnowapp.service.ProjectAllocationQueryService;
import com.qnowapp.service.ProjectAllocationService;
import com.qnowapp.service.dto.ProjectAllocationCriteria;

import com.qnowapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.qnowapp.domain.ProjectAllocation}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ProjectAllocationResource {

  
    private final Logger log = LoggerFactory.getLogger(ProjectAllocationResource.class);

    private static final String ENTITY_NAME = "projectAllocation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

  
    private static Boolean fromTesting = true;
    
    
    @Autowired
    ImEmployeeRepository imEmployeeRepository;
    
    
    @Autowired
    ImProjectsRepository imProjectsRepository;
    
    @Autowired
    ProjectRolesRepository projectRolesRepository;
    
    @Autowired
    ProjectAllocationRepository projectAllocationRepository ;
    
    ImEmployee imEmployee = new ImEmployee();

    ImProjects imProjects = new ImProjects();
    
    ProjectRoles projectRoles = new ProjectRoles();
    
    private final ProjectAllocationService projectAllocationService;

    private final ProjectAllocationQueryService projectAllocationQueryService;

    public ProjectAllocationResource(ProjectAllocationService projectAllocationService, ProjectAllocationQueryService projectAllocationQueryService) {
        this.projectAllocationService = projectAllocationService;
        this.projectAllocationQueryService = projectAllocationQueryService;
      
    }
    


    /**
     * {@code POST  /project-allocations} : Create a new projectAllocation.
     *
     * @param projectAllocation the projectAllocation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new projectAllocation, or with status
     *         {@code 400 (Bad Request)} if the projectAllocation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    public String removeDoubleQuotes(String MyEmail) {
        if (MyEmail == null) {
            return null;

        }
        if (MyEmail.equals("")) {
            System.out.println(MyEmail);
            return MyEmail;

        }
        String doubleQuote = "\"\"";
        if (MyEmail.substring(0, 2).equals(doubleQuote)) {
            MyEmail = MyEmail.substring(2, MyEmail.length());
        }

        if (MyEmail.substring(MyEmail.length() - 2, MyEmail.length()).equals(doubleQuote)) {

            MyEmail = MyEmail.substring(0, MyEmail.length() - 2);

        }
        return MyEmail;
    }

    public String removeQuotes(String MyEmail) {
        if (MyEmail == null) {
            return null;

        }
        if (MyEmail.equals("")) {
            System.out.println(MyEmail);
            return MyEmail;

        }
        String doubleQuote = "\"{";
        if (MyEmail.substring(0, 2).equals(doubleQuote)) {
            MyEmail = MyEmail.substring(2, MyEmail.length());
        }
        String doubleQuote1 = "}\"";
        if (MyEmail.substring(MyEmail.length() - 2, MyEmail.length()).equals(doubleQuote1)) {

            MyEmail = MyEmail.substring(0, MyEmail.length() - 2);

        }
        return MyEmail;
    }

    public String removeComma(String MyEmail) {
        if (MyEmail == null) {
            return null;

        }
        if (MyEmail.equals("")) {
            System.out.println(MyEmail);
            return MyEmail;

        }
        String doubleQuote = ",";
        if (MyEmail.substring(0, 1).equals(doubleQuote)) {
            MyEmail = MyEmail.substring(1, MyEmail.length());
        }

        if (MyEmail.substring(MyEmail.length() - 1, MyEmail.length()).equals(doubleQuote)) {

            MyEmail = MyEmail.substring(0, MyEmail.length() - 1);

        }
        return MyEmail;
    }

    public Double convertToDouble(String integer) {

        if (integer.equals("") || integer == null) {
            return new Double(0);
        } else {
            try {
                Float fl = Float.parseFloat(integer);

                return new Double(fl);
            } catch (Exception e) {
                System.out.println(e);
            }

        }
        return new Double(0);

    }

    @CrossOrigin
    @PostMapping("/project-allocations")
    public ResponseEntity<ProjectAllocation> createProjectAllocation(@RequestBody ProjectAllocation projectAllocation)
            throws URISyntaxException {
        log.debug("REST request to save ProjectAllocation : {}", projectAllocation);
        if (projectAllocation.getId() != null) {
            throw new BadRequestAlertException("A new projectAllocation cannot already have an ID", ENTITY_NAME,
                    "idexists");
        } else {
            ProjectAllocation result = projectAllocationRepository.save(projectAllocation);
            ResponseEntity<ProjectAllocation> a = ResponseEntity
                    .created(new URI("/api/project-initiative-ids/" + result.getId())).headers(HeaderUtil
                            .createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                    .body(result);

            if (fromTesting == false) {
                
                List<ImEmployee> myEmployee = imEmployeeRepository.findAll();
                List<ImProjects> myProject = imProjectsRepository.findAll();
                List<ProjectRoles> myRoles = projectRolesRepository.findAll();
                
                System.out.println(projectAllocation.getId());
                String csvFile1 = "src\\main\\resources\\pmqprojects.csv";
                BufferedReader br = null;
                String line = "";
                String cvsSplitBy = ";";

                try {
                    br = new BufferedReader(new FileReader(csvFile1));
                    int count = 0;
                    while ((line = br.readLine()) != null) {
                        count++;
                        System.out.println(+count);
                        if (count == 1)
                            continue;
                        //line = line + ";test";
                        String[] country = line.split(cvsSplitBy);

                        

                        try {
                            if (country.length > 117) {
                                String country1 = removeQuotes(country[116]);
                                String country2 =country1.replace("\"","");
                                String country3 =country2.replace("{","");
                                String country4 = country3.replace("}","");
                                System.out.println(country4 + "country1");
                                String[] tempArray;
                                String delimiter = ",";
                                //country1 = country1 + ",test1";
                                
                                System.out.println("country1 222" + country4);
                                tempArray =country4.split(delimiter);
                                System.out.println("country1 224" + country4);
                                for (int i = 0; i < tempArray.length; i++)

                                {
                                    String delimiter1 = "#";
                                    
                                    String str = removeDoubleQuotes(tempArray[i]);
                                    //String str = st.toString();
                                    //str = str + "#test";
                                    String[] allocation = str.split(delimiter1);
                                    if(allocation.length > 3) {
                                        System.out.println(allocation[0]);
                                        System.out.println(allocation[1]);
                                        System.out.println(allocation[2]);
                                        System.out.println(allocation[3]);
                                        System.out.println(country[4] + " ImProject");

                                            try {
                                                 
                                                    System.out.println("1");
                                                    
                                                    Boolean founda = false;
                                                    System.out.println("2");
                                                    String Employee = allocation[0];
                                                    System.out.println("3" + Employee);
                                                    projectAllocation.setImEmployee(null);
                                                    System.out.println("4");
                                                    if (Employee.equals("") == false && Employee != null) {
                                                        System.out.println("5");
                                                        myEmployee.forEach(item -> {
                                                            System.out.println("6");
                                                            QnowUser QuserHere = item.getQnowUser();
                                                            System.out.println("7");
                                                            if (QuserHere != null) {
                                                                System.out.println("8");
                                                                User userHere = QuserHere.getUser();
                                                                System.out.println("9");
                                                                if (userHere != null) {
                                                                    System.out.println("10");
                                                                    String myEmailHere = userHere.getEmail();
                                                                    System.out.println("11");
                                                                    if (myEmailHere.equals(Employee)) {
                                                                        System.out.println("12");
                                                                        projectAllocation.setImEmployee(item);
                                                                        System.out.println("13");
                                                                    }
                                                                }
                                                            }

                                                        });
                                                    }
                                                    
                                                    
                                                    Boolean found1 = false;
                                                    String Project = country[4];
                                                    projectAllocation.setImProjects(null);
                                                    if (Project.equals("") == false && Project != null) {
                                                        myProject.forEach(item -> {
                                                            String myPath = item.getProjectPath();
                                                            if (myPath.equals(Project)) {
                                                                System.out.println("14");
                                                                projectAllocation.setImProjects(item); 

                                                            }

                                                        });
                                                    }

                                                    
                                                    Boolean found2 = false;
                                                    String Roles = allocation[1];
                                                    projectAllocation.setProjectRoles(null);
                                                    if (Roles.equals("") == false && Roles != null) {
                                                        myRoles.forEach(item -> {
                                                            String myPath = item.getCode();
                                                            if (myPath.equals(Roles)) {
                                                                System.out.println("15");
                                                                projectAllocation.setProjectRoles(item); // 93

                                                            }

                                                        });
                                                    }
                                                    System.out.println("16");
                                                    projectAllocation.setPercentage(convertToDouble(allocation[3]));
                                                    
                                                    System.out.println("17");
                                                    projectAllocation.setId(null);
                                                    ProjectAllocation result2 = projectAllocationRepository.save(projectAllocation);
                                                    System.out.println("18");
                                                    System.out.println(result2.getId());
                                                    System.out.println(projectAllocation + "new project created");
                                                
                                            } catch (Exception e) {
                                                System.out.println(e + " e1 " + count + " " + line);
                                                return a;
                                                
                                            }
                                    
                                        
                                    }
                                    
                                }
                                System.out.println("country1 324" + country1);
                                }

                                

                            
                        } catch (Exception e) {
                            System.out.println(e + " e2 " + count + " " + line);
                            return a;
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e + " e3  "+ line);
                    return a;
                    // TODO: handle exception
                } finally {
                    if (br != null) {
                        try {
                            br.close();
                        } catch (Exception e) {
                            System.out.println(e);
                        }

                    }
                }
            }
            return a;
        }
    }
    /**
     * {@code PUT  /project-allocations} : Updates an existing projectAllocation.
     *
     * @param projectAllocation the projectAllocation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectAllocation,
     * or with status {@code 400 (Bad Request)} if the projectAllocation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectAllocation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/project-allocations")
    public ResponseEntity<ProjectAllocation> updateProjectAllocation(@RequestBody ProjectAllocation projectAllocation) throws URISyntaxException {
        log.debug("REST request to update ProjectAllocation : {}", projectAllocation);
        if (projectAllocation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectAllocation result = projectAllocationService.save(projectAllocation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectAllocation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-allocations} : get all the projectAllocations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectAllocations in body.
     */
    @CrossOrigin
    @GetMapping("/project-allocations")
    public ResponseEntity<List<ProjectAllocation>> getAllProjectAllocations(ProjectAllocationCriteria criteria) {
        log.debug("REST request to get ProjectAllocations by criteria: {}", criteria);
        List<ProjectAllocation> entityList = projectAllocationQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /project-allocations/count} : count all the projectAllocations.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @CrossOrigin
    @GetMapping("/project-allocations/count")
    public ResponseEntity<Long> countProjectAllocations(ProjectAllocationCriteria criteria) {
        log.debug("REST request to count ProjectAllocations by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectAllocationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-allocations/:id} : get the "id" projectAllocation.
     *
     * @param id the id of the projectAllocation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectAllocation, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/project-allocations/{id}")
    public ResponseEntity<ProjectAllocation> getProjectAllocation(@PathVariable Long id) {
        log.debug("REST request to get ProjectAllocation : {}", id);
        Optional<ProjectAllocation> projectAllocation = projectAllocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectAllocation);
    }

    /**
     * {@code DELETE  /project-allocations/:id} : delete the "id" projectAllocation.
     *
     * @param id the id of the projectAllocation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/project-allocations/{id}")
    public ResponseEntity<Void> deleteProjectAllocation(@PathVariable Long id) {
        log.debug("REST request to delete ProjectAllocation : {}", id);
        projectAllocationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}



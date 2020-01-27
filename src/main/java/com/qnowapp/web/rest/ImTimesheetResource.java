package com.qnowapp.web.rest;


import com.qnowapp.domain.ImEmployee;
import com.qnowapp.domain.ImProjects;
import com.qnowapp.domain.ImTimesheet;

import com.qnowapp.domain.QnowUser;
import com.qnowapp.domain.User;
import com.qnowapp.repository.ImEmployeeRepository;
import com.qnowapp.repository.ImProjectsRepository;
import com.qnowapp.repository.ImTimesheetRepository;
import com.qnowapp.service.ImTimesheetQueryService;
import com.qnowapp.service.ImTimesheetService;
import com.qnowapp.service.dto.ImTimesheetCriteria;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.qnowapp.domain.ImTimesheet}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ImTimesheetResource {

    private final Logger log = LoggerFactory.getLogger(ImTimesheetResource.class);

    private static final String ENTITY_NAME = "imTimesheet";
    
    private static Boolean fromTesting = true;
    
    @Autowired
    ImEmployeeRepository imEmployeeRepository;
    
    
    @Autowired
    ImProjectsRepository imProjectsRepository;

    @Autowired
    ImTimesheetRepository imTimesheetRepository ;
    
    ImEmployee imEmployee = new ImEmployee();

    ImProjects imProjects = new ImProjects();
    
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;


    private final ImTimesheetService imTimesheetService;

    private final ImTimesheetQueryService imTimesheetQueryService;
    public ImTimesheetResource( ImTimesheetService imTimesheetService, ImTimesheetQueryService imTimesheetQueryService) {
      
        this.imTimesheetService = imTimesheetService;
        this.imTimesheetQueryService = imTimesheetQueryService;
    }
    
    public static void setFromTesting(Boolean bState) {
        fromTesting = bState;
    }

    public String removeQuotes(String MyEmail) {
        if (MyEmail == null) {
            return null;

        }
        if (MyEmail.equals("")) {
            System.out.println(MyEmail);
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

    /*
     * String a = "2018-03-26 00:00:00-05"; DateTimeFormatter formatter1 =
     * DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); LocalDateTime mydate =
     * LocalDateTime.parse(a.substring(0, 19), formatter1); ZonedDateTime resultado
     * = ZonedDateTime.ofLocal(mydate, ZoneOffset.UTC, null);
     */
    
    @CrossOrigin
    @PostMapping("/im-timesheets")
    public ResponseEntity<ImTimesheet> createImTimesheet(@Valid @RequestBody ImTimesheet imTimesheet) throws URISyntaxException {
        log.debug("REST request to save ImTimesheet : {}", imTimesheet);
        if (imTimesheet.getId() != null) {
            throw new BadRequestAlertException("A new imTimesheet cannot already have an ID", ENTITY_NAME, "idexists");
        }else {

            ImTimesheet result = imTimesheetRepository.save(imTimesheet);
            ResponseEntity<ImTimesheet> returnObj = ResponseEntity
                    .created(new URI("/api/im-projects/" + result.getId())).headers(HeaderUtil
                            .createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                    .body(result);
            if (fromTesting == false) {
                
                List<ImEmployee> myEmployee = imEmployeeRepository.findAll();
                List<ImProjects> myProject = imProjectsRepository.findAll();
                
                
                
                String csvFile1 = "src\\main\\resources\\pmqtimesheet.csv";
                BufferedReader br = null;
                String line = "";
                String cvsSplitBy = ";";

                try {
                    br = new BufferedReader(new FileReader(csvFile1));
                    int count = 0;
                    while ((line = br.readLine()) != null) {
                        count++;
                        System.out.println("hiii111111  " + count);
                        if (count == 1)
                            continue;
                        line = line + ";test";
                        String[] country = line.split(cvsSplitBy);
                        System.out.println(line);
                        //System.out.println(country.length);
                        try {
                            if (country.length >= 4) {
                                System.out.println("1");
                                imTimesheet.setId(null);
                                
                                System.out.println("country[3]) " + country[3]);
                                imTimesheet.setLoghours(convertToDouble(removeQuotes(country[3])));
                                System.out.println("country[3]) " + country[2]);
                                imTimesheet.setLogdate(convertToZonedDateTime(country[2]));
                                System.out.println("country[3]) " + country[4]);
                                imTimesheet.setNotes(removeQuotes(country[4]));
                                

                                Boolean founda = false;
                                System.out.println("2");
                                String Employee = removeQuotes(country[0]);
                                System.out.println("3" + Employee);
                                imTimesheet.setImEmployee(null);
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
                                                    imTimesheet.setImEmployee(item);
                                                    System.out.println("13");
                                                }
                                            }
                                        }

                                    });
                                }
                                
                            
                                Boolean found1 = false;
                                String Project = removeQuotes(country[1]);
                                imTimesheet.setImProjects(null);
                                if (Project.equals("") == false && Project != null) {
                                    myProject.forEach(item -> {
                                        String myPath = item.getProjectPath();
                                        if (myPath.equals(Project)) {
                                            System.out.println("14");
                                            imTimesheet.setImProjects(item); 

                                        }

                                    });
                                }
                                }
                            System.out.println("17");
                            imTimesheet.setId(null);
                            ImTimesheet result2 =imTimesheetRepository.save(imTimesheet);
                            System.out.println("18");
                            System.out.println(result2.getId());
                            System.out.println(imTimesheet + "new project created");
                            
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

    }

    /**
     * {@code PUT  /im-timesheets} : Updates an existing imTimesheet.
     *
     * @param imTimesheet the imTimesheet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imTimesheet,
     * or with status {@code 400 (Bad Request)} if the imTimesheet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the imTimesheet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/im-timesheets")
    public ResponseEntity<ImTimesheet> updateImTimesheet(@Valid @RequestBody ImTimesheet imTimesheet) throws URISyntaxException {
        log.debug("REST request to update ImTimesheet : {}", imTimesheet);
        if (imTimesheet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ImTimesheet result = imTimesheetService.save(imTimesheet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, imTimesheet.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /im-timesheets} : get all the imTimesheets.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of imTimesheets in body.
     */
    @CrossOrigin
    @GetMapping("/im-timesheets")
    public ResponseEntity<List<ImTimesheet>> getAllImTimesheets(ImTimesheetCriteria criteria) {
        log.debug("REST request to get ImTimesheets by criteria: {}", criteria);
        List<ImTimesheet> entityList = imTimesheetQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /im-timesheets/count} : count all the imTimesheets.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @CrossOrigin
    @GetMapping("/im-timesheets/count")
    public ResponseEntity<Long> countImTimesheets(ImTimesheetCriteria criteria) {
        log.debug("REST request to count ImTimesheets by criteria: {}", criteria);
        return ResponseEntity.ok().body(imTimesheetQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /im-timesheets/:id} : get the "id" imTimesheet.
     *
     * @param id the id of the imTimesheet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the imTimesheet, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/im-timesheets/{id}")
    public ResponseEntity<ImTimesheet> getImTimesheet(@PathVariable Long id) {
        log.debug("REST request to get ImTimesheet : {}", id);
        Optional<ImTimesheet> imTimesheet = imTimesheetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(imTimesheet);
    }

    /**
     * {@code DELETE  /im-timesheets/:id} : delete the "id" imTimesheet.
     *
     * @param id the id of the imTimesheet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/im-timesheets/{id}")
    public ResponseEntity<Void> deleteImTimesheet(@PathVariable Long id) {
        log.debug("REST request to delete ImTimesheet : {}", id);
        imTimesheetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
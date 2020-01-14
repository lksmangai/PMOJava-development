package com.qnowapp.web.rest;

import com.qnowapp.domain.BacklogPractice;

import com.qnowapp.domain.ProjectInitiativeId;
import com.qnowapp.repository.BacklogPracticeRepository;
import com.qnowapp.service.BacklogPracticeService;
import com.qnowapp.service.dto.BacklogPracticeCriteria;
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
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.qnowapp.domain.BacklogPractice}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class BacklogPracticeResource {

	@Autowired
	BacklogPracticeService backlogPracticeService;
    private final Logger log = LoggerFactory.getLogger(BacklogPracticeResource.class);

    private static final String ENTITY_NAME = "backlogPractice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private static Boolean fromTesting = true;
   


    public BacklogPracticeResource(BacklogPracticeService backlogPracticeService) {
     
        this.backlogPracticeService = backlogPracticeService;
       
    }
    public static void setFromTesting(Boolean bState) {
        fromTesting = bState;
    }
    /**
     * {@code POST  /backlog-practices} : Create a new backlogPractice.
     *
     * @param backlogPractice the backlogPractice to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new backlogPractice, or with status {@code 400 (Bad Request)} if the backlogPractice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/backlog-practices")
    public ResponseEntity<BacklogPractice> createBacklogPractice(@RequestBody BacklogPractice backlogPractice) throws URISyntaxException {
        log.debug("REST request to save BacklogPractice : {}", backlogPractice);
        if (backlogPractice.getId() != null) {
            throw new BadRequestAlertException("A new backlogPractice cannot already have an ID", ENTITY_NAME, "idexists");
        }else {

            BacklogPractice result = backlogPracticeService.save(backlogPractice);
            ResponseEntity<BacklogPractice> a = ResponseEntity.created(new URI("/api/project-initiative-ids/" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                    .body(result);

            if (fromTesting == false) {
               
                String csvFile1 = "src\\main\\resources\\backlog.csv";
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
                        String[] country = line.split(cvsSplitBy);
                        try {
                            if (country.length > 3 ) {
                                
                                backlogPractice.setId(null);
                                backlogPractice.setCode(country[0]);
                                
                                backlogPractice.setName(country[1]);
                               
                                backlogPractice.setDescription(country[2]);
                                BacklogPractice result2 = backlogPracticeService.save(backlogPractice);  
                               
                            }
                        } catch (Exception e) {
                        }
                    }
                } catch (Exception e) {
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
     * {@code PUT  /backlog-practices} : Updates an existing backlogPractice.
     *
     * @param backlogPractice the backlogPractice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated backlogPractice,
     * or with status {@code 400 (Bad Request)} if the backlogPractice is not valid,
     * or with status {@code 500 (Internal Server Error)} if the backlogPractice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/backlog-practices")
    public ResponseEntity<BacklogPractice> updateBacklogPractice(@RequestBody BacklogPractice backlogPractice) throws URISyntaxException {
        log.debug("REST request to update BacklogPractice : {}", backlogPractice);
        if (backlogPractice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BacklogPractice result = backlogPracticeService.save(backlogPractice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, backlogPractice.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /backlog-practices} : get all the backlogPractices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of backlogPractices in body.
     */
    @CrossOrigin
    @GetMapping("/backlog-practices")
    public ResponseEntity<List<BacklogPractice>> getAllBacklogPractices(BacklogPracticeCriteria criteria) {
        log.debug("REST request to get BacklogPractices by criteria: {}", criteria);
        List<BacklogPractice> entityList = backlogPracticeService.findAll();
        return ResponseEntity.ok().body(entityList);
    }



    /**
     * {@code GET  /backlog-practices/:id} : get the "id" backlogPractice.
     *
     * @param id the id of the backlogPractice to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the backlogPractice, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/backlog-practices/{id}")
    public ResponseEntity<BacklogPractice> getBacklogPractice(@PathVariable Long id) {
        log.debug("REST request to get BacklogPractice : {}", id);
        Optional<BacklogPractice> backlogPractice = backlogPracticeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(backlogPractice);
    }

    /**
     * {@code DELETE  /backlog-practices/:id} : delete the "id" backlogPractice.
     *
     * @param id the id of the backlogPractice to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/backlog-practices/{id}")
    public ResponseEntity<Void> deleteBacklogPractice(@PathVariable Long id) {
        log.debug("REST request to delete BacklogPractice : {}", id);
        backlogPracticeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

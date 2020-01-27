package com.qnowapp.web.rest;

import com.qnowapp.domain.OpportunityPriorityId;
import com.qnowapp.domain.ProjectInitiativeId;
import com.qnowapp.repository.OpportunityPriorityIdRepository;
import com.qnowapp.service.OpportunityPriorityIdQueryService;
import com.qnowapp.service.OpportunityPriorityIdService;
import com.qnowapp.service.dto.OpportunityPriorityIdCriteria;
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
 * REST controller for managing {@link com.qnowapp.domain.OpportunityPriorityId}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class OpportunityPriorityIdResource {

    private final Logger log = LoggerFactory.getLogger(OpportunityPriorityIdResource.class);

    private static final String ENTITY_NAME = "opportunityPriorityId";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    @Autowired
    OpportunityPriorityIdRepository opportunityPriorityIdRepository;
    private static Boolean fromTesting = true;


    
    private final OpportunityPriorityIdService opportunityPriorityIdService;

    private final OpportunityPriorityIdQueryService opportunityPriorityIdQueryService;

    public OpportunityPriorityIdResource( OpportunityPriorityIdService opportunityPriorityIdService, OpportunityPriorityIdQueryService opportunityPriorityIdQueryService) {
        this.opportunityPriorityIdService = opportunityPriorityIdService;
        this.opportunityPriorityIdQueryService = opportunityPriorityIdQueryService;
       
    }
 
    public static void setFromTesting(Boolean bState) {
        fromTesting = bState;
    }
    /**
     * {@code POST  /opportunity-priority-ids} : Create a new opportunityPriorityId.
     *
     * @param opportunityPriorityId the opportunityPriorityId to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new opportunityPriorityId, or with status {@code 400 (Bad Request)} if the opportunityPriorityId has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/opportunity-priority-ids")
    public ResponseEntity<OpportunityPriorityId> createOpportunityPriorityId(@RequestBody OpportunityPriorityId opportunityPriorityId) throws URISyntaxException {
        log.debug("REST request to save OpportunityPriorityId : {}", opportunityPriorityId);
        if (opportunityPriorityId.getId() != null) {
            throw new BadRequestAlertException("A new opportunityPriorityId cannot already have an ID", ENTITY_NAME, "idexists");
        }else {

            OpportunityPriorityId result = opportunityPriorityIdRepository.save(opportunityPriorityId);
            ResponseEntity<OpportunityPriorityId> a = ResponseEntity.created(new URI("/api/project-initiative-ids/" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                    .body(result);

            if (fromTesting == false) {
                System.out.println(opportunityPriorityId.getName());
                String csvFile1 = "src\\main\\resources\\priority.csv";
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
                        String[] country = line.split(cvsSplitBy);
                        try {
                            if (country.length > 3 ) {
                                System.out.println("1");
                                opportunityPriorityId.setId(null);
                                opportunityPriorityId.setCode(country[0]);
                                System.out.println("2");
                                opportunityPriorityId.setName(country[1]);
                                System.out.println("3");
                                opportunityPriorityId.setDescription(country[2]);
                                OpportunityPriorityId result2 = opportunityPriorityIdRepository.save(opportunityPriorityId);    
                                System.out.println(result2.getId());
                                System.out.println(opportunityPriorityId + "new project created");
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

    @CrossOrigin
    @PutMapping("/opportunity-priority-ids")
    public ResponseEntity<OpportunityPriorityId> updateOpportunityPriorityId(@RequestBody OpportunityPriorityId opportunityPriorityId) throws URISyntaxException {
        log.debug("REST request to update OpportunityPriorityId : {}", opportunityPriorityId);
        if (opportunityPriorityId.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OpportunityPriorityId result = opportunityPriorityIdService.save(opportunityPriorityId);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, opportunityPriorityId.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /opportunity-priority-ids} : get all the opportunityPriorityIds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of opportunityPriorityIds in body.
     */
    @CrossOrigin
    @GetMapping("/opportunity-priority-ids")
    public ResponseEntity<List<OpportunityPriorityId>> getAllOpportunityPriorityIds(OpportunityPriorityIdCriteria criteria) {
        log.debug("REST request to get OpportunityPriorityIds by criteria: {}", criteria);
        List<OpportunityPriorityId> entityList = opportunityPriorityIdQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

  
    @CrossOrigin
    @GetMapping("/opportunity-priority-ids/count")
    public ResponseEntity<Long> countOpportunityPriorityIds(OpportunityPriorityIdCriteria criteria) {
        log.debug("REST request to count OpportunityPriorityIds by criteria: {}", criteria);
        return ResponseEntity.ok().body(opportunityPriorityIdQueryService.countByCriteria(criteria));
    }

    

    @CrossOrigin
    @GetMapping("/opportunity-priority-ids/{id}")
    public ResponseEntity<OpportunityPriorityId> getOpportunityPriorityId(@PathVariable Long id) {
        log.debug("REST request to get OpportunityPriorityId : {}", id);
        Optional<OpportunityPriorityId> opportunityPriorityId = opportunityPriorityIdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(opportunityPriorityId);
    }


    @CrossOrigin
    @DeleteMapping("/opportunity-priority-ids/{id}")
    public ResponseEntity<Void> deleteOpportunityPriorityId(@PathVariable Long id) {
        log.debug("REST request to delete OpportunityPriorityId : {}", id);
        opportunityPriorityIdService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

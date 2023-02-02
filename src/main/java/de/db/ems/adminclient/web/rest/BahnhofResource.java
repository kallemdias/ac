package de.db.ems.adminclient.web.rest;

import de.db.ems.adminclient.domain.Bahnhof;
import de.db.ems.adminclient.repository.BahnhofRepository;
import de.db.ems.adminclient.service.BahnhofService;
import de.db.ems.adminclient.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link de.db.ems.adminclient.domain.Bahnhof}.
 */
@RestController
@RequestMapping("/api")
public class BahnhofResource {

    private final Logger log = LoggerFactory.getLogger(BahnhofResource.class);

    private static final String ENTITY_NAME = "bahnhof";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BahnhofService bahnhofService;

    private final BahnhofRepository bahnhofRepository;

    public BahnhofResource(BahnhofService bahnhofService, BahnhofRepository bahnhofRepository) {
        this.bahnhofService = bahnhofService;
        this.bahnhofRepository = bahnhofRepository;
    }

    /**
     * {@code POST  /bahnhofs} : Create a new bahnhof.
     *
     * @param bahnhof the bahnhof to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bahnhof, or with status {@code 400 (Bad Request)} if the bahnhof has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bahnhofs")
    public ResponseEntity<Bahnhof> createBahnhof(@RequestBody Bahnhof bahnhof) throws URISyntaxException {
        log.debug("REST request to save Bahnhof : {}", bahnhof);
        if (bahnhof.getId() != null) {
            throw new BadRequestAlertException("A new bahnhof cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bahnhof result = bahnhofService.save(bahnhof);
        return ResponseEntity
            .created(new URI("/api/bahnhofs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bahnhofs/:id} : Updates an existing bahnhof.
     *
     * @param id the id of the bahnhof to save.
     * @param bahnhof the bahnhof to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bahnhof,
     * or with status {@code 400 (Bad Request)} if the bahnhof is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bahnhof couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bahnhofs/{id}")
    public ResponseEntity<Bahnhof> updateBahnhof(@PathVariable(value = "id", required = false) final Long id, @RequestBody Bahnhof bahnhof)
        throws URISyntaxException {
        log.debug("REST request to update Bahnhof : {}, {}", id, bahnhof);
        if (bahnhof.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bahnhof.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bahnhofRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Bahnhof result = bahnhofService.update(bahnhof);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bahnhof.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bahnhofs/:id} : Partial updates given fields of an existing bahnhof, field will ignore if it is null
     *
     * @param id the id of the bahnhof to save.
     * @param bahnhof the bahnhof to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bahnhof,
     * or with status {@code 400 (Bad Request)} if the bahnhof is not valid,
     * or with status {@code 404 (Not Found)} if the bahnhof is not found,
     * or with status {@code 500 (Internal Server Error)} if the bahnhof couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bahnhofs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bahnhof> partialUpdateBahnhof(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bahnhof bahnhof
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bahnhof partially : {}, {}", id, bahnhof);
        if (bahnhof.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bahnhof.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bahnhofRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bahnhof> result = bahnhofService.partialUpdate(bahnhof);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bahnhof.getId().toString())
        );
    }

    /**
     * {@code GET  /bahnhofs} : get all the bahnhofs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bahnhofs in body.
     */
    @GetMapping("/bahnhofs")
    public List<Bahnhof> getAllBahnhofs() {
        log.debug("REST request to get all Bahnhofs");
        return bahnhofService.findAll();
    }

    /**
     * {@code GET  /bahnhofs/:id} : get the "id" bahnhof.
     *
     * @param id the id of the bahnhof to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bahnhof, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bahnhofs/{id}")
    public ResponseEntity<Bahnhof> getBahnhof(@PathVariable Long id) {
        log.debug("REST request to get Bahnhof : {}", id);
        Optional<Bahnhof> bahnhof = bahnhofService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bahnhof);
    }

    /**
     * {@code DELETE  /bahnhofs/:id} : delete the "id" bahnhof.
     *
     * @param id the id of the bahnhof to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bahnhofs/{id}")
    public ResponseEntity<Void> deleteBahnhof(@PathVariable Long id) {
        log.debug("REST request to delete Bahnhof : {}", id);
        bahnhofService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}

package de.db.ems.adminclient.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.db.ems.adminclient.IntegrationTest;
import de.db.ems.adminclient.domain.Bahnhof;
import de.db.ems.adminclient.repository.BahnhofRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BahnhofResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BahnhofResourceIT {

    private static final Integer DEFAULT_BAHNHOFS_NR = 1;
    private static final Integer UPDATED_BAHNHOFS_NR = 2;

    private static final String DEFAULT_BAHNHOFS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BAHNHOFS_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bahnhofs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BahnhofRepository bahnhofRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBahnhofMockMvc;

    private Bahnhof bahnhof;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bahnhof createEntity(EntityManager em) {
        Bahnhof bahnhof = new Bahnhof().bahnhofsNr(DEFAULT_BAHNHOFS_NR).bahnhofsName(DEFAULT_BAHNHOFS_NAME);
        return bahnhof;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bahnhof createUpdatedEntity(EntityManager em) {
        Bahnhof bahnhof = new Bahnhof().bahnhofsNr(UPDATED_BAHNHOFS_NR).bahnhofsName(UPDATED_BAHNHOFS_NAME);
        return bahnhof;
    }

    @BeforeEach
    public void initTest() {
        bahnhof = createEntity(em);
    }

    @Test
    @Transactional
    void createBahnhof() throws Exception {
        int databaseSizeBeforeCreate = bahnhofRepository.findAll().size();
        // Create the Bahnhof
        restBahnhofMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bahnhof)))
            .andExpect(status().isCreated());

        // Validate the Bahnhof in the database
        List<Bahnhof> bahnhofList = bahnhofRepository.findAll();
        assertThat(bahnhofList).hasSize(databaseSizeBeforeCreate + 1);
        Bahnhof testBahnhof = bahnhofList.get(bahnhofList.size() - 1);
        assertThat(testBahnhof.getBahnhofsNr()).isEqualTo(DEFAULT_BAHNHOFS_NR);
        assertThat(testBahnhof.getBahnhofsName()).isEqualTo(DEFAULT_BAHNHOFS_NAME);
    }

    @Test
    @Transactional
    void createBahnhofWithExistingId() throws Exception {
        // Create the Bahnhof with an existing ID
        bahnhof.setId(1L);

        int databaseSizeBeforeCreate = bahnhofRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBahnhofMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bahnhof)))
            .andExpect(status().isBadRequest());

        // Validate the Bahnhof in the database
        List<Bahnhof> bahnhofList = bahnhofRepository.findAll();
        assertThat(bahnhofList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBahnhofs() throws Exception {
        // Initialize the database
        bahnhofRepository.saveAndFlush(bahnhof);

        // Get all the bahnhofList
        restBahnhofMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bahnhof.getId().intValue())))
            .andExpect(jsonPath("$.[*].bahnhofsNr").value(hasItem(DEFAULT_BAHNHOFS_NR)))
            .andExpect(jsonPath("$.[*].bahnhofsName").value(hasItem(DEFAULT_BAHNHOFS_NAME)));
    }

    @Test
    @Transactional
    void getBahnhof() throws Exception {
        // Initialize the database
        bahnhofRepository.saveAndFlush(bahnhof);

        // Get the bahnhof
        restBahnhofMockMvc
            .perform(get(ENTITY_API_URL_ID, bahnhof.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bahnhof.getId().intValue()))
            .andExpect(jsonPath("$.bahnhofsNr").value(DEFAULT_BAHNHOFS_NR))
            .andExpect(jsonPath("$.bahnhofsName").value(DEFAULT_BAHNHOFS_NAME));
    }

    @Test
    @Transactional
    void getNonExistingBahnhof() throws Exception {
        // Get the bahnhof
        restBahnhofMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBahnhof() throws Exception {
        // Initialize the database
        bahnhofRepository.saveAndFlush(bahnhof);

        int databaseSizeBeforeUpdate = bahnhofRepository.findAll().size();

        // Update the bahnhof
        Bahnhof updatedBahnhof = bahnhofRepository.findById(bahnhof.getId()).get();
        // Disconnect from session so that the updates on updatedBahnhof are not directly saved in db
        em.detach(updatedBahnhof);
        updatedBahnhof.bahnhofsNr(UPDATED_BAHNHOFS_NR).bahnhofsName(UPDATED_BAHNHOFS_NAME);

        restBahnhofMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBahnhof.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBahnhof))
            )
            .andExpect(status().isOk());

        // Validate the Bahnhof in the database
        List<Bahnhof> bahnhofList = bahnhofRepository.findAll();
        assertThat(bahnhofList).hasSize(databaseSizeBeforeUpdate);
        Bahnhof testBahnhof = bahnhofList.get(bahnhofList.size() - 1);
        assertThat(testBahnhof.getBahnhofsNr()).isEqualTo(UPDATED_BAHNHOFS_NR);
        assertThat(testBahnhof.getBahnhofsName()).isEqualTo(UPDATED_BAHNHOFS_NAME);
    }

    @Test
    @Transactional
    void putNonExistingBahnhof() throws Exception {
        int databaseSizeBeforeUpdate = bahnhofRepository.findAll().size();
        bahnhof.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBahnhofMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bahnhof.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bahnhof))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bahnhof in the database
        List<Bahnhof> bahnhofList = bahnhofRepository.findAll();
        assertThat(bahnhofList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBahnhof() throws Exception {
        int databaseSizeBeforeUpdate = bahnhofRepository.findAll().size();
        bahnhof.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBahnhofMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bahnhof))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bahnhof in the database
        List<Bahnhof> bahnhofList = bahnhofRepository.findAll();
        assertThat(bahnhofList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBahnhof() throws Exception {
        int databaseSizeBeforeUpdate = bahnhofRepository.findAll().size();
        bahnhof.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBahnhofMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bahnhof)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bahnhof in the database
        List<Bahnhof> bahnhofList = bahnhofRepository.findAll();
        assertThat(bahnhofList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBahnhofWithPatch() throws Exception {
        // Initialize the database
        bahnhofRepository.saveAndFlush(bahnhof);

        int databaseSizeBeforeUpdate = bahnhofRepository.findAll().size();

        // Update the bahnhof using partial update
        Bahnhof partialUpdatedBahnhof = new Bahnhof();
        partialUpdatedBahnhof.setId(bahnhof.getId());

        partialUpdatedBahnhof.bahnhofsName(UPDATED_BAHNHOFS_NAME);

        restBahnhofMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBahnhof.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBahnhof))
            )
            .andExpect(status().isOk());

        // Validate the Bahnhof in the database
        List<Bahnhof> bahnhofList = bahnhofRepository.findAll();
        assertThat(bahnhofList).hasSize(databaseSizeBeforeUpdate);
        Bahnhof testBahnhof = bahnhofList.get(bahnhofList.size() - 1);
        assertThat(testBahnhof.getBahnhofsNr()).isEqualTo(DEFAULT_BAHNHOFS_NR);
        assertThat(testBahnhof.getBahnhofsName()).isEqualTo(UPDATED_BAHNHOFS_NAME);
    }

    @Test
    @Transactional
    void fullUpdateBahnhofWithPatch() throws Exception {
        // Initialize the database
        bahnhofRepository.saveAndFlush(bahnhof);

        int databaseSizeBeforeUpdate = bahnhofRepository.findAll().size();

        // Update the bahnhof using partial update
        Bahnhof partialUpdatedBahnhof = new Bahnhof();
        partialUpdatedBahnhof.setId(bahnhof.getId());

        partialUpdatedBahnhof.bahnhofsNr(UPDATED_BAHNHOFS_NR).bahnhofsName(UPDATED_BAHNHOFS_NAME);

        restBahnhofMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBahnhof.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBahnhof))
            )
            .andExpect(status().isOk());

        // Validate the Bahnhof in the database
        List<Bahnhof> bahnhofList = bahnhofRepository.findAll();
        assertThat(bahnhofList).hasSize(databaseSizeBeforeUpdate);
        Bahnhof testBahnhof = bahnhofList.get(bahnhofList.size() - 1);
        assertThat(testBahnhof.getBahnhofsNr()).isEqualTo(UPDATED_BAHNHOFS_NR);
        assertThat(testBahnhof.getBahnhofsName()).isEqualTo(UPDATED_BAHNHOFS_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingBahnhof() throws Exception {
        int databaseSizeBeforeUpdate = bahnhofRepository.findAll().size();
        bahnhof.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBahnhofMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bahnhof.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bahnhof))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bahnhof in the database
        List<Bahnhof> bahnhofList = bahnhofRepository.findAll();
        assertThat(bahnhofList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBahnhof() throws Exception {
        int databaseSizeBeforeUpdate = bahnhofRepository.findAll().size();
        bahnhof.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBahnhofMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bahnhof))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bahnhof in the database
        List<Bahnhof> bahnhofList = bahnhofRepository.findAll();
        assertThat(bahnhofList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBahnhof() throws Exception {
        int databaseSizeBeforeUpdate = bahnhofRepository.findAll().size();
        bahnhof.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBahnhofMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bahnhof)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bahnhof in the database
        List<Bahnhof> bahnhofList = bahnhofRepository.findAll();
        assertThat(bahnhofList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBahnhof() throws Exception {
        // Initialize the database
        bahnhofRepository.saveAndFlush(bahnhof);

        int databaseSizeBeforeDelete = bahnhofRepository.findAll().size();

        // Delete the bahnhof
        restBahnhofMockMvc
            .perform(delete(ENTITY_API_URL_ID, bahnhof.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bahnhof> bahnhofList = bahnhofRepository.findAll();
        assertThat(bahnhofList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package de.db.ems.adminclient.service;

import de.db.ems.adminclient.domain.Bahnhof;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Bahnhof}.
 */
public interface BahnhofService {
    /**
     * Save a bahnhof.
     *
     * @param bahnhof the entity to save.
     * @return the persisted entity.
     */
    Bahnhof save(Bahnhof bahnhof);

    /**
     * Updates a bahnhof.
     *
     * @param bahnhof the entity to update.
     * @return the persisted entity.
     */
    Bahnhof update(Bahnhof bahnhof);

    /**
     * Partially updates a bahnhof.
     *
     * @param bahnhof the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Bahnhof> partialUpdate(Bahnhof bahnhof);

    /**
     * Get all the bahnhofs.
     *
     * @return the list of entities.
     */
    List<Bahnhof> findAll();

    /**
     * Get the "id" bahnhof.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Bahnhof> findOne(Long id);

    /**
     * Delete the "id" bahnhof.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

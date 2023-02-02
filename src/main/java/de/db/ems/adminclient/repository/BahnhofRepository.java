package de.db.ems.adminclient.repository;

import de.db.ems.adminclient.domain.Bahnhof;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bahnhof entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BahnhofRepository extends JpaRepository<Bahnhof, Long> {}

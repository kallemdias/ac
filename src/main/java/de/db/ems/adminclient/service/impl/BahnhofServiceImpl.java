package de.db.ems.adminclient.service.impl;

import de.db.ems.adminclient.domain.Bahnhof;
import de.db.ems.adminclient.repository.BahnhofRepository;
import de.db.ems.adminclient.service.BahnhofService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Bahnhof}.
 */
@Service
@Transactional
public class BahnhofServiceImpl implements BahnhofService {

    private final Logger log = LoggerFactory.getLogger(BahnhofServiceImpl.class);

    private final BahnhofRepository bahnhofRepository;

    public BahnhofServiceImpl(BahnhofRepository bahnhofRepository) {
        this.bahnhofRepository = bahnhofRepository;
    }

    @Override
    public Bahnhof save(Bahnhof bahnhof) {
        log.debug("Request to save Bahnhof : {}", bahnhof);
        return bahnhofRepository.save(bahnhof);
    }

    @Override
    public Bahnhof update(Bahnhof bahnhof) {
        log.debug("Request to update Bahnhof : {}", bahnhof);
        return bahnhofRepository.save(bahnhof);
    }

    @Override
    public Optional<Bahnhof> partialUpdate(Bahnhof bahnhof) {
        log.debug("Request to partially update Bahnhof : {}", bahnhof);

        return bahnhofRepository
            .findById(bahnhof.getId())
            .map(existingBahnhof -> {
                if (bahnhof.getBahnhofsNr() != null) {
                    existingBahnhof.setBahnhofsNr(bahnhof.getBahnhofsNr());
                }
                if (bahnhof.getBahnhofsName() != null) {
                    existingBahnhof.setBahnhofsName(bahnhof.getBahnhofsName());
                }

                return existingBahnhof;
            })
            .map(bahnhofRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bahnhof> findAll() {
        log.debug("Request to get all Bahnhofs");
        return bahnhofRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Bahnhof> findOne(Long id) {
        log.debug("Request to get Bahnhof : {}", id);
        return bahnhofRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bahnhof : {}", id);
        bahnhofRepository.deleteById(id);
    }
}

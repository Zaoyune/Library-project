package org.sid.demo.Repositories;

import org.sid.demo.entities.Affectation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AffectationRepository extends JpaRepository<Affectation,Long> {
}

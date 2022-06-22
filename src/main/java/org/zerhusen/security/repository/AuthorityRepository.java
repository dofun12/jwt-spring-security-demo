package org.zerhusen.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerhusen.security.model.Authority;

import java.util.Optional;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
   Optional<Authority> findAuthorityByName(String name);
}

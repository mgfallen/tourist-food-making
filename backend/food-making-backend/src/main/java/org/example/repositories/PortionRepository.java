package org.example.repositories;

import org.example.entities.Portion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortionRepository extends JpaRepository<Portion, Long> {
}

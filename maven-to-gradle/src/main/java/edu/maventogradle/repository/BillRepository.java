package edu.maventogradle.repository;

import edu.maventogradle.model.Billionaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Billionaire, Integer> {
}

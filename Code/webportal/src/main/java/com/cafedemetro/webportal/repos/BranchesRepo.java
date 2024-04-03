package com.cafedemetro.webportal.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import com.cafedemetro.webportal.models.Branches;

@Component
/*
 * 1. extends the class that provide JPA function on
 * entity class Branches while the primary key type
 * is Long
 */
public interface BranchesRepo extends JpaRepository<Branches, Long> {
    /*
     * 2. the method with name "findByBranchCode" to find Branches by branchCode and
     * return a single Branches object
     */
    Branches findByBranchCode(String branchCode);

    /*
     * 3. the method with name "findBranchWithSeatsGreaterThanOrEqualTo", accept a
     * parameter "seats" and query the database and return a list of Branches by
     * using following JPQL "SELECT b FROM Branches b WHERE b.totalSeats >= :seats"
     */
    @Query("SELECT b FROM Branches b WHERE b.totalSeats >= :seats")
    List<Branches> findBranchWithSeatsGreaterThanOrEqualTo(int seats);
}

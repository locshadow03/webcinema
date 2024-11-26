package com.webcinema.repository;

import com.webcinema.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    void deleteById(Long id);

    @Query("select b.name from Branch b")
    List<String> findAllNameBranch();
}

package com.bugtracker.bugtracker;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BugRepository extends JpaRepository<Bug, Long> {
    Page<Bug> findByStatus(Bug.Status status, Pageable pageable);
}
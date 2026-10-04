package com.bugtracker.bugtracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bugs")
public class BugController {

    @Autowired
    private BugRepository bugRepository;

    // GET all bugs
        // GET all bugs, with optional status filter and pagination
    @GetMapping
    public Page<Bug> getAllBugs(
            @RequestParam(required = false) Bug.Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        if (status != null) {
            return bugRepository.findByStatus(status, pageable);
        }
        return bugRepository.findAll(pageable);
    }

    // GET a single bug by id
    @GetMapping("/{id}")
    public Bug getBugById(@PathVariable Long id) {
        return bugRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bug not found with id: " + id));
    }

    // POST create a new bug
    @PostMapping
    public Bug createBug(@RequestBody Bug bug) {
        return bugRepository.save(bug);
    }

    // PUT update an existing bug
    @PutMapping("/{id}")
    public ResponseEntity<Bug> updateBug(@PathVariable Long id, @RequestBody Bug updatedBug) {
        return bugRepository.findById(id).map(bug -> {
            bug.setTitle(updatedBug.getTitle());
            bug.setDescription(updatedBug.getDescription());
            bug.setStatus(updatedBug.getStatus());
            bug.setPriority(updatedBug.getPriority());
            bug.setReportedBy(updatedBug.getReportedBy());
            return ResponseEntity.ok(bugRepository.save(bug));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE a bug
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBug(@PathVariable Long id) {
        if (bugRepository.existsById(id)) {
            bugRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
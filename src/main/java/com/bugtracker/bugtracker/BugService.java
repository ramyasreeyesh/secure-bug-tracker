package com.bugtracker.bugtracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BugService {

    @Autowired
    private BugRepository bugRepository;

    public Page<Bug> getAllBugs(Bug.Status status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (status != null) {
            return bugRepository.findByStatus(status, pageable);
        }
        return bugRepository.findAll(pageable);
    }

    public Bug getBugById(Long id) {
        return bugRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bug not found with id: " + id));
    }

    public Bug createBug(Bug bug) {
        return bugRepository.save(bug);
    }

    public Bug updateBug(Long id, Bug updatedBug) {
        Bug bug = getBugById(id); // reuses the not-found check
        bug.setTitle(updatedBug.getTitle());
        bug.setDescription(updatedBug.getDescription());
        bug.setStatus(updatedBug.getStatus());
        bug.setPriority(updatedBug.getPriority());
        bug.setReportedBy(updatedBug.getReportedBy());
        return bugRepository.save(bug);
    }

    public void deleteBug(Long id) {
        if (!bugRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bug not found with id: " + id);
        }
        bugRepository.deleteById(id);
    }
}
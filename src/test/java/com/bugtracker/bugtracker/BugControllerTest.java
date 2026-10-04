package com.bugtracker.bugtracker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BugControllerTest {

    @Mock
    private BugRepository bugRepository;

    @InjectMocks
    private BugController bugController;

    @Test
    void getBugById_whenBugExists_returnsBug() {
        Bug bug = new Bug();
        bug.setId(1L);
        bug.setTitle("Login button broken");

        when(bugRepository.findById(1L)).thenReturn(Optional.of(bug));

        Bug result = bugController.getBugById(1L);

        assertNotNull(result);
        assertEquals("Login button broken", result.getTitle());
    }

    @Test
    void getBugById_whenBugDoesNotExist_throwsException() {
        when(bugRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            bugController.getBugById(99L);
        });
    }

    @Test
    void createBug_savesAndReturnsBug() {
        Bug newBug = new Bug();
        newBug.setTitle("New bug");

        when(bugRepository.save(any(Bug.class))).thenReturn(newBug);

        Bug result = bugController.createBug(newBug);

        assertNotNull(result);
        assertEquals("New bug", result.getTitle());
        verify(bugRepository, times(1)).save(newBug);
    }

    @Test
    void deleteBug_whenExists_deletesSuccessfully() {
        when(bugRepository.existsById(1L)).thenReturn(true);

        bugController.deleteBug(1L);

        verify(bugRepository, times(1)).deleteById(1L);
    }
}
package com.bugtracker.bugtracker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BugControllerTest {

    @Mock
    private BugService bugService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BugController bugController;

    @Test
    void getBugById_whenBugExists_returnsBug() {
        Bug bug = new Bug();
        bug.setId(1L);
        bug.setTitle("Login button broken");

        when(bugService.getBugById(1L)).thenReturn(bug);

        Bug result = bugController.getBugById(1L);

        assertNotNull(result);
        assertEquals("Login button broken", result.getTitle());
    }

    @Test
    void getBugById_whenBugDoesNotExist_throwsException() {
        when(bugService.getBugById(99L)).thenThrow(new ResourceNotFoundException("Bug not found with id: 99"));

        assertThrows(ResourceNotFoundException.class, () -> {
            bugController.getBugById(99L);
        });
    }

    @Test
    void createBug_savesAndReturnsBug() {
        User user = new User();
        user.setUsername("ramya");

        Bug newBug = new Bug();
        newBug.setTitle("New bug");

        Principal principal = () -> "ramya";

        when(userRepository.findByUsername("ramya")).thenReturn(Optional.of(user));
        when(bugService.createBug(any(Bug.class))).thenReturn(newBug);

        Bug result = bugController.createBug(newBug, principal);

        assertNotNull(result);
        assertEquals("New bug", result.getTitle());
        verify(bugService, times(1)).createBug(newBug);
    }

    @Test
    void deleteBug_whenExists_deletesSuccessfully() {
        doNothing().when(bugService).deleteBug(1L);

        bugController.deleteBug(1L);

        verify(bugService, times(1)).deleteBug(1L);
    }
}
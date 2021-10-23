package com.service;

import com.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.Instant;
import java.util.Arrays;

import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class UserServiceImplTest {

    private final Instant timestamp;

    private UserRepository userRepository;

    private UserService userService;

    public UserServiceImplTest(Instant timestamp, UserRepository userRepository, UserService userService) {
        this.timestamp = timestamp;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Parameterized.Parameters(name = "{index}: timestamp = {0}")
    public static Iterable<Object[]> data() {
        UserRepository repository1 = mock(UserRepository.class);
        UserRepository repository2 = mock(UserRepository.class);
        return Arrays.asList(new Object[][]{{Instant.now(), repository1, new UserServiceImpl(repository1)}, {Instant.now()
                , repository2, new UserServiceImpl(repository2)}});
    }

    @Test
    public void whenGetAllUsers_findAllShouldBeCalled() {
        userService.getAllUsers();
        verify(userRepository, times(1)).findAll();
    }
}
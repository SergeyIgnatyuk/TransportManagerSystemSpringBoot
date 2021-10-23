package com.rest;

import com.model.Role;
import com.model.Status;
import com.model.User;
import com.repository.UserRepository;
import com.security.UserDetailsServiceImpl;
import com.service.UserService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integrations tests of {@link UserRestController}.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@WebMvcTest
public class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @TestConfiguration
    static class DepartmentControllerTestConfig {
        @Bean
        protected UserRepository userRepository() {
            return mock(UserRepository.class);
        }


        @Bean("userDetailsServiceImpl")
        protected UserDetailsService userDetailsService() {
            return new UserDetailsServiceImpl(userRepository());
        }
    }

    @WithMockUser(authorities = "users:read")
    @Test
    public void givenUsers_whenGetAllUsers_thenReturnJsonArray() throws Exception {
        List<User> users = Stream.of(User.builder()
                .id(1L)
                .email("email")
                .firstName("firstName")
                .lastName("lastName")
                .password("password")
                .role(Role.ADMIN)
                .status(Status.ACTIVE)
                .build()).collect(Collectors.toList());

        User user = users.get(0);

        when(userService.getAllUsers()).thenReturn(users);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(user.getId().intValue())))
                .andExpect(jsonPath("$[0].email", Matchers.is(user.getEmail())))
                .andExpect(jsonPath("$[0].firstName", Matchers.is(user.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", Matchers.is(user.getLastName())))
                .andExpect(jsonPath("$[0].password", Matchers.is(user.getPassword())))
                .andExpect(jsonPath("$[0].role", Matchers.is(user.getRole().name())))
                .andExpect(jsonPath("$[0].status", Matchers.is(user.getStatus().name())));
    }
}
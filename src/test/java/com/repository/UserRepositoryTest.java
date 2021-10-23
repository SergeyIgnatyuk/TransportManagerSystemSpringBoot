package com.repository;

import com.model.Role;
import com.model.Status;
import com.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Integrations tests of {@link UserRepository}.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Rollback(value = true)
    public void whenFindAll_thenReturnUsers() {
        List<User> users = userRepository.findAll();

        User user = users.get(0);

        Assert.assertEquals(3, users.size());
        Assert.assertEquals(1, user.getId().intValue());
        Assert.assertEquals("sergeev89@gmail.com", user.getEmail());
        Assert.assertEquals("Sergey", user.getFirstName());
        Assert.assertEquals("Sergeev", user.getLastName());
        Assert.assertEquals("$2y$12$IeKfJrnYCEUbDL48jFPnbeai9YYG0cfqZfx/V8XTIMFcbXhwgg03q", user.getPassword());
        Assert.assertEquals(Role.ADMIN, user.getRole());
        Assert.assertEquals(Status.ACTIVE, user.getStatus());
    }
}
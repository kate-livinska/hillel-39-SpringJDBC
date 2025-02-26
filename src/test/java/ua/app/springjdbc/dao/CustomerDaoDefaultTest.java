package ua.app.springjdbc.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.app.springjdbc.config.DaoTestConfig;
import ua.app.springjdbc.mapper.CustomerRowMapper;
import ua.app.springjdbc.model.Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoTestConfig.class)
class CustomerDaoDefaultTest {
    protected Connection connection;
    private CustomerDao customerDao;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @AfterEach
    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }


    @BeforeEach
    void setUp() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        String url = "jdbc:h2:mem:test;INIT=runscript from 'classpath:init.sql'";

        connection = DriverManager.getConnection(url);
        customerDao = new CustomerDaoDefault(jdbcTemplate, new CustomerRowMapper());
    }

    @Test
    void findAllTest_returnsNonEmptyList() {
        List<Customer> all = customerDao.findAll();
        assertNotNull(all);
        assertFalse(all.isEmpty());
    }

    @Test
    void saveTest_returnsUserWithId() {
        Customer customer = new Customer();
        customer.setFullName("Test Test");
        customer.setEmail("test@test.com");
        customer.setSocialSecurityNumber(1234);

        Optional<Customer> result = customerDao.save(customer);

        assertTrue(result.isPresent());
        assertNotNull(result.get().getId());
        assertEquals(customer.getFullName(), result.get().getFullName());
        assertEquals(customer.getEmail(), result.get().getEmail());
        assertEquals(customer.getSocialSecurityNumber(), result.get().getSocialSecurityNumber());

    }

    @Test
    void findByIdTest_returnsUserWithId() {
        Long id = 1L;
        Optional<Customer> result = customerDao.findById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    void findByIdTest_noCustomer_returnsEmptyOptional() {
        Long id = 1000L;
        assertThrows(EmptyResultDataAccessException.class, () -> customerDao.findById(id));
    }

    @Test
    void updateTest_updatesData() {
        Long id = 1L;
        Optional<Customer> customer = customerDao.findById(id);

        if (customer.isPresent()) {
            Customer verifiedCustomer = customer.get();
            verifiedCustomer.setFullName("Updated Test");
            verifiedCustomer.setEmail("update@test.com");
            customerDao.update(verifiedCustomer);
        }

        Optional<Customer> result = customerDao.findById(id);
        assertTrue(result.isPresent());
        assertEquals("Updated Test", result.get().getFullName());
        assertEquals("update@test.com", result.get().getEmail());
    }

    @Test
    void deleteTest_customerDeleted() {
        Long id = 2L;
        customerDao.delete(id);

        assertThrows(EmptyResultDataAccessException.class, () -> customerDao.findById(id));
    }
}
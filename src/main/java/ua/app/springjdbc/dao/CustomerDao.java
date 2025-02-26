package ua.app.springjdbc.dao;

import ua.app.springjdbc.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    Optional<Customer> save(Customer customer);
    Optional<Customer> findById(Long id);
    void update(Customer customer);
    void delete(Long customerId);
    List<Customer> findAll();
}

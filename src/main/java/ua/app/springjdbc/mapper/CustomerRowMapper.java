package ua.app.springjdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.app.springjdbc.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerRowMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setId(resultSet.getLong("id"));

        customer.setFullName(resultSet.getString("full_name"));
        customer.setEmail(resultSet.getString("email"));
        customer.setSocialSecurityNumber(resultSet.getInt("social_sec_num"));

        return customer;
    }
}

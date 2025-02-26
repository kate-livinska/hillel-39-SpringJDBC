package ua.app.springjdbc.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.app.springjdbc.exception.DaoException;
import ua.app.springjdbc.mapper.CustomerRowMapper;
import ua.app.springjdbc.model.Customer;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerDaoDefault implements CustomerDao {
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper rowMapper;

    @Override
    public List<Customer> findAll() throws DaoException {
        return jdbcTemplate.query("""
                SELECT * FROM t_customer
                """, rowMapper);
    }

    @Override
    public Optional<Customer> save(Customer customer) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcTemplate.update(conn -> {
            PreparedStatement st = conn.prepareStatement("INSERT INTO t_customer(full_name, email, social_sec_num) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, customer.getFullName());
            st.setString(2, customer.getEmail());
            st.setString(3, customer.getSocialSecurityNumber().toString());

            return st;
        }, keyHolder);

        if (rowsAffected != 1) {
            throw new DaoException("Could not save customer.");
        }

        long id = (Long) keyHolder.getKeyList()
                .get(0)
                .get("ID");
        customer.setId(id);

        return Optional.of(customer);
    }

    @Override
    public Optional<Customer> findById(Long id) throws DaoException {
        Customer customer = jdbcTemplate.queryForObject("""
                SELECT * FROM t_customer WHERE id = ?
                """,
                new Object[]{id},
                new int[]{Types.BIGINT},
                rowMapper);
        return Optional.of(customer);
    }

    @Override
    public void update(Customer customer) throws DaoException {
        int rowsAffected = jdbcTemplate.update(con -> {
            PreparedStatement st = con.prepareStatement("UPDATE t_customer SET full_name = ?, email = ?, social_sec_num = ? WHERE ID = ?");
            st.setString(1, customer.getFullName());
            st.setString(2, customer.getEmail());
            st.setString(3, customer.getSocialSecurityNumber().toString());
            st.setLong(4, customer.getId());
            return st;
        });

        if (rowsAffected == 0) {
            throw new DaoException("Could not update customer.");
        }
    }

    @Override
    public void delete(Long customerId) throws DaoException {
        int rowsAffected = jdbcTemplate.update("DELETE FROM t_customer WHERE ID = ?", customerId);

        if (rowsAffected == 0) {
            throw new DaoException("Could not delete customer.");
        }
    }
}

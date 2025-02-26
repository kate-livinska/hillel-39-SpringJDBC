package ua.app.springjdbc.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Customer {
    private Long id;
    private String fullName;
    private String email;
    private Integer socialSecurityNumber;
}

package pl.michal_sobiech.engineering_thesis.employee;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Employee {

    @Id
    @Column(name = "employee_id")
    private final long employeeId;

    // TODO join column and lazy load?
    @Column(name = "user_id")
    private final long userId;

    @OneToOne(cascade = CascadeType.ALL)
    @Column(name = "enterprise_id")
    private final long enterpriseId;

    @Column(name = "first_name")
    private final String firstName;

    @Column(name = "last_name")
    private final String lastName;

    @Column(name = "password_hash")
    private final String passwordHash;

    @Column
    private final String username;

}
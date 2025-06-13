package com.test.iam.model.user;

import jakarta.persistence.*;
import com.test.iam.model.role.Role;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "IAM_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String email;
    private String department;
    private String gender;
    private String nationality;
    private String maritalStatus;
    private String position;
    private String jobTitle;
    private String departmentName;
    private LocalDate hireDate;
    private LocalDate terminationDate;
    private String workEmail;
    private String workPhone;
    private String mobilePhone;
    private String address;
    private String bankAccount;
    private String taxId;
    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}

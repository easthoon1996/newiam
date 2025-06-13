package com.test.iam.model.role;

import jakarta.persistence.*;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Generated
@Entity
@Table(name = "IAM_PRIV")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name; // 예: READ_USER, WRITE_ROLE

    public Privilege(String name) {
    }

    public Privilege() {

    }
}

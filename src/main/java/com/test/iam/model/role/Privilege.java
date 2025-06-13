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

    @Column(nullable = false, unique = true)
    private String name;

    public Privilege() {}

    public Privilege(String name) {
        this.name = name;
    }

    // getter, setter 생략
}


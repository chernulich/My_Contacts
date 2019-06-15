package com.telran.contacts.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "USERS")

public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "date_created", columnDefinition = "datetime")
    private LocalDateTime createdDate;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email", unique = true)
    private String email;
}

package com.telran.contacts.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

@Entity
@Table(name = "PHONE_NUMBERS")
public class PhoneNumber implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_number_id")
    private Long phoneNumberId;

    @Column(name = "date_created")
    @Past
    private LocalDateTime createdDate;

    @Column(name = "phone_number")//TODO unique = true
    @NotBlank
    private String phoneNumber;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}

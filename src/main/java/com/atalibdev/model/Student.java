package com.atalibdev.model;

import com.atalibdev.enumeration.Gender;
import com.atalibdev.enumeration.Major;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student {
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "student_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    @NotBlank(message = "First name is required")
    @Column(nullable = false)
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Column(nullable = false)
    private String lastName;
    @Email(message = "Please enter a valid email address")
    @Column(nullable = false, unique = true)
    private String email;
    @NotNull(message = "Gender can not be null")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotNull(message = "Major can not be null")
    @Enumerated(EnumType.STRING)
    private Major major;
}

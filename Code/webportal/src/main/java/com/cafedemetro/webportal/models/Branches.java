package com.cafedemetro.webportal.models;

import java.io.Serializable;
import java.util.List;
import org.hibernate.validator.constraints.Length;
import lombok.ToString;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 1. The annotation to make this class an JPA entity
@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Branches implements Serializable {
    // 2. The annotation to make this field a primary key
    @Id
    // 3. The annotation to make this field auto-incremented
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bId;

    // 4. The annotation to make this field an unique column with length = 10
    // and it is not nullable in DB
    @Column(nullable = false, length = 10, unique = true)
    // 5. The annotation for length validation, only accept up to 10 characters
    // and error message is "Branch code should be less than 10 characters" if
    // validation failed
    @Length(max = 10, message = "Branch code should be less than 10 characters")
    private String branchCode;

    // 6. The annotation to make this field not null in DB with length = 50
    @Column(nullable = false, length = 50)
    // 7. The annotation for length validation, only accept up to 50 characters
    // and error message is "Branch name should be less than 50 characters" if
    // validation failed
    @Length(max = 50, message = "Branch name should be less than 50 characters")
    private String branchName;

    // 8. The annotation for value validation, only accept value greater than 1
    // if validation failed, error message is "Seats should be greater than 0"
    @Min(value = 1, message = "Seats should be greater than 0")
    // 9. The annotation for value validation, only accept value at most 11
    // if validation failed, error message is "Seats should be less than 11"
    @Max(value = 10, message = "Seats should be less than 11")
    // 10. The annotation to make this field not null in DB
    @Column(nullable = false)
    private int totalSeats;

    @OneToMany(mappedBy = "branch", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Orders> orders;
}
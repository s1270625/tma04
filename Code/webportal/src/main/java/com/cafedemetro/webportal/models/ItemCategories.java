package com.cafedemetro.webportal.models;

import java.io.Serializable;
import java.util.List;
import org.hibernate.validator.constraints.Length;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemCategories implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long icId;

    @Column(nullable = false, length = 50)
    @Length(max = 50, message = "Item name should be less than 50 characters")
    private String icName;

    @Column(nullable = false, unique = true, length = 10)
    @Length(max = 10, message = "Item code should be less than 10 characters")
    private String icCode;

    @OneToMany(mappedBy = "itemCategory", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Items> items;
}

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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Items implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long itemId;

    @ManyToOne
    @JoinColumn(name = "icId")
    private ItemCategories itemCategory;

    @Column(nullable = false, length = 10, unique = true)
    @Length(max = 10, message = "Item code should be less than 10 characters")
    private String itemCode;

    @Column(nullable = false, length = 50)
    @Length(max = 50, message = "Item name should be less than 50 characters")
    private String itemName;

    @ToString.Exclude
    @OneToMany(mappedBy = "items", fetch = FetchType.EAGER)
    private List<OrderDetails> details;
}
package com.cafedemetro.webportal.models;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long detailsId;

    @ManyToOne
    @JoinColumn(name = "oId")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "itemId")
    private Items items;

    @Min(value = 1, message = "Quantity should be greater than 0")
    private int qty;
}

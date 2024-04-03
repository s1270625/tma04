package com.cafedemetro.webportal.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long oId;

    @ManyToOne
    @JoinColumn(name = "bId")
    private Branches branch;

    @Column(nullable = false, unique = true, length = 50)
    private String qrCode;

    @Column(nullable = false, length = 1)
    private String status; // A: Active ; P: Pending ; C: Completed

    @OneToMany(mappedBy = "orders", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<OrderDetails> details;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDtm;
}
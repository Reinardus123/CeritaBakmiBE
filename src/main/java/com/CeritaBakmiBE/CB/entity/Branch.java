package com.CeritaBakmiBE.CB.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "branch")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long branchId;

    @Column(nullable = false)
    private String branchName;

    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "branch")
    private List<Transaction> transaction;

    private boolean isActive = true;
}

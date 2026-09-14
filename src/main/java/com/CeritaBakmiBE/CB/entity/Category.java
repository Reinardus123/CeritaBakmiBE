package com.CeritaBakmiBE.CB.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "categories")
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long CategoryId;

    @Column(nullable = false)
    private String CategoryName;

    @Column(nullable = false)
    private boolean isActive = true;

    @OneToMany(mappedBy = "category")
    private List<Menu> menu;
}

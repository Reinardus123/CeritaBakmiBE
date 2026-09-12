package com.CeritaBakmiBE.CB.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Table(name = "categories")
@AllArgsConstructor
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

    @OneToMany(mappedBy = "category")
    private List<Menu> menu;
}

package com.CeritaBakmiBE.CB.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "menu")
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long menuId;

    @Column(nullable = false)
    private String menuTitle;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int price;

    private String imageUrl;

    private boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryID", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "menu")
    private List<CartItem> cartItem;



}

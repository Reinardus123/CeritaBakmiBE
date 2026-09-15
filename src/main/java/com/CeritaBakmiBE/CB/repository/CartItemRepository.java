package com.CeritaBakmiBE.CB.repository;

import com.CeritaBakmiBE.CB.entity.Cart;
import com.CeritaBakmiBE.CB.entity.CartItem;
import com.CeritaBakmiBE.CB.entity.Menu;
import com.CeritaBakmiBE.CB.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndMenu(Cart cart, Menu menu);

    List<CartItem> findByCart(Cart cart);
}

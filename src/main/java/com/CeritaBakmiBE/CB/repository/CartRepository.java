package com.CeritaBakmiBE.CB.repository;

import com.CeritaBakmiBE.CB.entity.Cart;
import com.CeritaBakmiBE.CB.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);
}

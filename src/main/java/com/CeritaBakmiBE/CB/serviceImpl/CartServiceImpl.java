package com.CeritaBakmiBE.CB.serviceImpl;

import com.CeritaBakmiBE.CB.entity.Cart;
import com.CeritaBakmiBE.CB.entity.CartItem;
import com.CeritaBakmiBE.CB.entity.Menu;
import com.CeritaBakmiBE.CB.entity.User;
import com.CeritaBakmiBE.CB.repository.CartItemRepository;
import com.CeritaBakmiBE.CB.repository.CartRepository;
import com.CeritaBakmiBE.CB.repository.MenuRepository;
import com.CeritaBakmiBE.CB.request.CartRequest;
import com.CeritaBakmiBE.CB.response.CartResponse;
import com.CeritaBakmiBE.CB.service.CartService;
import com.CeritaBakmiBE.CB.util.FindAuthenticationUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final FindAuthenticationUser findAuthenticationUser;
    private final MenuRepository menuRepository;

    @Override
    public CartResponse addCart(CartRequest cartRequest) throws IOException {

        User currentUser = findAuthenticationUser.getAuthenticatedUser();
        Optional<Cart> userCart =  cartRepository.findByUser(currentUser);

        Cart cart;
        Menu menu;
        CartItem cartItem;

        if(userCart.isPresent()){
            cart = userCart.get();
        } else{
            Cart newCart = new Cart();
            newCart.setUser(currentUser);

            cart = cartRepository.save(newCart);
        }

        Long menuId = cartRequest.getMenuId();
        Optional<Menu> findMenu = menuRepository.findById(menuId);

        if(findMenu.isEmpty()){
            throw new RuntimeException("Menu tidak ditemnukan");
        } else{
            menu = findMenu.get();
        }

       Optional<CartItem> findCartItem =  cartItemRepository.findByCartAndMenu(cart, menu);

        if(findCartItem.isPresent()){
           cartItem = findCartItem.get();
           cartItem.setQuantity(cartItem.getQuantity() + 1);
           cartItemRepository.save(cartItem);
        } else{
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setMenu(menu);
            newCartItem.setQuantity(cartRequest.getQuantity());
            cartItem = cartItemRepository.save(newCartItem);
        }

        return new CartResponse(
                cartItem.getCartItemId(),
                cartItem.getMenu().getMenuId(),
                cartItem.getMenu().getMenuTitle(),
                cartItem.getMenu().getPrice(),
                cartItem.getMenu().getImageUrl(),
                cartItem.getQuantity(),
                cartItem.getMenu().getPrice() * cartItem.getQuantity()
        ) ;
    }

    @Override
    public void removeFromCart(long id) {

        User currentUser = findAuthenticationUser.getAuthenticatedUser();
        Optional<Cart> findCart = cartRepository.findByUser(currentUser);

        Cart cart = findCart.orElseThrow( () ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cart tidak ditemukan"
                ));

        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Cart item tidak ada"
                        ));
        if(cartItem.getCart().getCartId() != cart.getCartId()){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Cart item bukan milik user"
            );
        }
        cartItemRepository.delete(cartItem);
    }

    @Override
    public List<CartResponse> getAllCart() {
        User currentUser = findAuthenticationUser.getAuthenticatedUser();
        Optional<Cart> findCart = cartRepository.findByUser(currentUser);

        Cart cart;

        if(findCart.isEmpty()){
            throw new RuntimeException("Cart tidak ada");
        } else{
          cart = findCart.get();
        }

        return cartItemRepository.findByCart(cart)
                .stream()
                .map(cartItem -> new CartResponse(
                        cartItem.getCartItemId(),
                        cartItem.getMenu().getMenuId(),
                        cartItem.getMenu().getMenuTitle(),
                        cartItem.getMenu().getPrice(),
                        cartItem.getMenu().getImageUrl(),
                        cartItem.getQuantity(),
                        cartItem.getMenu().getPrice() * cartItem.getQuantity()
                )) .toList();
    }
}

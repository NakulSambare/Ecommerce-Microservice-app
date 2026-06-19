package com.ecommerce.order.service;


import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.repository.CartItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class CartService {

//    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
//    private final UserRepository userRepository;

//    public CartService(ProductRepository productRepository, CartItemRepository cartItemRepository, UserRepository userRepository) {
//        this.productRepository = productRepository;
//        this.cartItemRepository = cartItemRepository;
//        this.userRepository = userRepository;
//    }


    public CartService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public void addToCart(String userId, CartItemRequest request) {
//        Product product = productRepository.findById(request.getProductId()).get();
//        if (product.getStockQuantity() < request.getQuantity()) {
//            throw new RuntimeException("Not enough stock for product: " + product.getName());
//        }
//        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new RuntimeException("User not found"));

        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(Long.valueOf(userId),request.getProductId());
        if(existingCartItem != null){
            //Update quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
           // existingCartItem.setPrice(product.getPrice().multiply(existingCartItem.getPrice()));
            existingCartItem.setPrice(BigDecimal.valueOf(1000));

            cartItemRepository.save(existingCartItem);
            return;
        }
        CartItem cartItem = new CartItem();
        cartItem.setProductId(request.getProductId());
        cartItem.setQuantity(request.getQuantity());
        cartItem.setUserId(Long.valueOf(userId));
//        cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
        cartItem.setPrice(BigDecimal.valueOf(1000));
        cartItemRepository.save(cartItem);

    }

    public boolean deleteItemFromCart(Long productId, String userId) {
//        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new RuntimeException("User not found"));
//        Product product = productRepository
//                .findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(Long.valueOf(userId), productId);
        if (cartItem != null) {
            cartItemRepository.delete(cartItem);
            return true;
        }
        return false;
    }

    public CartItem getCartItem(String userId, Long productId) {
//        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new RuntimeException("User not found"));
//        Product product = productRepository
//                .findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(Long.valueOf(userId), productId);
        if (cartItem != null) {
          return  cartItem;
        }
        return null;
    }

    public List<CartItem> getCart(String userId) {
        return cartItemRepository.findByUserId(Long.valueOf(userId));
    }

    public void clearCart(String userId) {

//        userRepository.findById(Long.valueOf(userId)).ifPresent((user -> {
//            cartItemRepository.deleteByUserId(userId);
//        }));
        cartItemRepository.deleteByUserId(Long.valueOf(userId));
    }
}

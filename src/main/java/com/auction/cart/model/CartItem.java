package com.auction.cart.model;


import com.auction.cart.dto.ProductRequest;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    //@ManyToOne
    //private ProductRequest product;
   private Long productId;

    private int quantity;

    private BigDecimal price;

    @ManyToOne
    private Cart cart;
}
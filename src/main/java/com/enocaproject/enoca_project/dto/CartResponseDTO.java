package com.enocaproject.enoca_project.dto;

import com.enocaproject.enoca_project.entity.CartItem;

import java.util.List;

public record CartResponseDTO(Long cartId,CustomerDTO customer,
                              List<CartItemDTO> cartItem,
                              double totalPrice) {
}

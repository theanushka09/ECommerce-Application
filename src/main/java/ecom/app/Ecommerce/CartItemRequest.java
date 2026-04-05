package ecom.app.Ecommerce.DTO;

import lombok.Data;

@Data
public class CartItemRequest {
    private Long productId;
    private Integer quantity;
}

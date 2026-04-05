package ecom.app.Ecommerce.Contolller;

import ecom.app.Ecommerce.DTO.CartItemRequest;
import ecom.app.Ecommerce.Model.CartItem;
import ecom.app.Ecommerce.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    @Autowired
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest cartItemRequest) {
        boolean addedtocart = cartService.addToCart(userId, cartItemRequest);
        if(addedtocart){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.badRequest().body("Product out of stock or user not found or product not found");
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long productId){
        boolean itemdeleted = cartService.deleteItemFromCart(userId, productId);
        if(itemdeleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("")
    public ResponseEntity<List<CartItem>> getCart(
            @RequestHeader("X-User-ID") String userId){
        return ResponseEntity.ok(cartService.getCart(userId));
    }
}

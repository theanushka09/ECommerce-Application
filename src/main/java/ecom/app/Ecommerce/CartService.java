package ecom.app.Ecommerce.Service;

import ecom.app.Ecommerce.DTO.CartItemRequest;
import ecom.app.Ecommerce.Model.CartItem;
import ecom.app.Ecommerce.Model.Product;
import ecom.app.Ecommerce.Model.User;
import ecom.app.Ecommerce.Repository.CartItemRepository;
import ecom.app.Ecommerce.Repository.ProductRepository;
import ecom.app.Ecommerce.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public boolean addToCart(String userId, CartItemRequest cartItemRequest) {
        //check if product exists or not
        Optional<Product> productOpt = productRepository.findById(cartItemRequest.getProductId());
        if(productOpt.isEmpty()) {
            return false;
        }

        //check if product is in stock or not
        Product product = productOpt.get();
        if(product.getStockquantity() < cartItemRequest.getQuantity()) {
            return false;
        }

        //check if any such user exists or not
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if(userOpt.isEmpty()) {
            return false;
        }

        //for user perform operations
        User user = userOpt.get();

        //check if this cartitem already exist for the user with same product
        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);

        if(existingCartItem != null) {
            //if exist-> add requested quantity and price to existing ones
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        }else{
            //create a new cart item
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getQuantity())));
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public boolean deleteItemFromCart(String userId, Long productId) {
        //check if any such user exists or not
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

        //check if product exists or not
        Optional<Product> productOpt = productRepository.findById(productId);

        if(productOpt.isPresent() && userOpt.isPresent()) {
            cartItemRepository.deleteByUserAndProduct(userOpt.get(), productOpt.get());
            return true;
        }
        return false;
    }

    public List<CartItem> getCart(String userId) {
        return userRepository.findById(Long.valueOf(userId))
                .map(cartItemRepository::findByUser)
                .orElseGet(List::of);
    }
}

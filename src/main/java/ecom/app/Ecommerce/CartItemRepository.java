package ecom.app.Ecommerce.Repository;

import ecom.app.Ecommerce.Model.CartItem;
import ecom.app.Ecommerce.Model.Product;
import ecom.app.Ecommerce.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);

    List<CartItem> findByUser(User user);
}

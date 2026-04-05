package ecom.app.Ecommerce.Repository;

import ecom.app.Ecommerce.DTO.ProductResponse;
import ecom.app.Ecommerce.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByActiveTrue();

    @Query("SELECT p From product_table p WHERE p.active = true AND p.stockquantity > 0 AND LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchProducts(@Param("keyword") String keyword);
}

package ecom.app.Ecommerce.Contolller;

import ecom.app.Ecommerce.DTO.ProductRequest;
import ecom.app.Ecommerce.DTO.ProductResponse;
import ecom.app.Ecommerce.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> GetAllProducts(){
        return ResponseEntity.ok(productService.fetchAllProducts());
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
        return new ResponseEntity<ProductResponse>(productService.addProduct(productRequest),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest){
        return productService.updateProduct(id, productRequest)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable Long id){
        boolean deleted = productService.deleteProduct(id);
        if(deleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String keyword){
        return ResponseEntity.ok(productService.findProducts(keyword));
    }
}

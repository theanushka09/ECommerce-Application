package ecom.app.Ecommerce.Service;

import ecom.app.Ecommerce.DTO.ProductRequest;
import ecom.app.Ecommerce.DTO.ProductResponse;
import ecom.app.Ecommerce.Model.Product;
import ecom.app.Ecommerce.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse addProduct(ProductRequest productRequest){
        Product product = new Product();
        updateProductFromRequest(product, productRequest);
        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    private void  updateProductFromRequest(Product product, ProductRequest productRequest){
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setCategory(productRequest.getCategory());
        product.setDescription(productRequest.getDescription());
        product.setStockquantity(productRequest.getStockquantity());
        product.setImageurl(productRequest.getImageurl());
    }

    private ProductResponse mapToProductResponse(Product savedproduct){
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(savedproduct.getId());
        productResponse.setName(savedproduct.getName());
        productResponse.setDescription(savedproduct.getDescription());
        productResponse.setPrice(savedproduct.getPrice());
        productResponse.setCategory(savedproduct.getCategory());
        productResponse.setImageurl(savedproduct.getImageurl());
        productResponse.setStockquantity(savedproduct.getStockquantity());
        productResponse.setActive(savedproduct.getActive());
        return productResponse;
    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
        return productRepository.findById(id)
                .map(existingProduct->{
                    updateProductFromRequest(existingProduct, productRequest);
                    Product savedProduct = productRepository.save(existingProduct);
                    return mapToProductResponse(savedProduct);
                });
    }

    public List<ProductResponse> fetchAllProducts() {
        return productRepository.findByActiveTrue().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public boolean deleteProduct(Long id) {
        return productRepository.findById(id)
                .map(product-> {
                    product.setActive(false);
                    productRepository.save(product);
                    return true;
                }).orElse(false);
    }

    public List<ProductResponse> findProducts(String keyword) {
        return productRepository.searchProducts(keyword).stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }
}

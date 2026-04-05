package ecom.app.Ecommerce.DTO;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockquantity;
    private String category;
    private String imageurl;
    private Boolean active;
}

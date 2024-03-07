package et.com.gebeya.inventory_management.vendorProduct.dto;

import et.com.gebeya.inventory_management.Models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDto {
    private Long id;
    private String productName;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.productName = product.getName();
    }
}

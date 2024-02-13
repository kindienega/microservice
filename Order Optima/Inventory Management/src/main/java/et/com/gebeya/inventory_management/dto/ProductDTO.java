package et.com.gebeya.inventory_management.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDTO {
    private Long id;
    private String name;
    private int quantity;
    private Double price;
    private String Description;
    private String productDetail;
    private String imageUrl;
    private Long categoryId;
}
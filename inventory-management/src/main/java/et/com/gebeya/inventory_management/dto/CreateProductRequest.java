package et.com.gebeya.inventory_management.dto;

import et.com.gebeya.inventory_management.Models.Category;
import lombok.Data;

@Data
public class CreateProductRequest {
    private String name;
    private int quantity;
    private Double price;
    private String Description;
    private String productDetail;
    private String imageUrl;
    private String discount;
    private Category category;
   // private Long categoryId;
}

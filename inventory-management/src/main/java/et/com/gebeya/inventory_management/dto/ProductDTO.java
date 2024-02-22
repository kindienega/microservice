package et.com.gebeya.inventory_management.dto;

import et.com.gebeya.inventory_management.Models.Category;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDTO {
    //private Long id;
    private String name;
    private int quantity;
    private Double price;
    private String Description;
    private String productDetail;
    private String imageUrl;
    private String discount;
    private Category category;

   // private MultipartFile photo;
}
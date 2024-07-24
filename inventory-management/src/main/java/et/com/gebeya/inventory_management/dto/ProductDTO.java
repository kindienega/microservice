package et.com.gebeya.inventory_management.dto;

import et.com.gebeya.inventory_management.Models.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private int quantity;
    private Double price;
    private String Description;
    private String imageUrl;
    private String discount;
    private Category category;
    private String calories;
    private String fat;
    private String protein;
    private String weight;
    private String size;
    private String volume;
    private String brands;

   // private MultipartFile photo;
}
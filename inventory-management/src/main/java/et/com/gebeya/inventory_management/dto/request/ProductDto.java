package et.com.gebeya.inventory_management.dto.request;

import et.com.gebeya.inventory_management.Models.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class ProductDto {
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

    public ProductDto(Long id, String name, int quantity, Double price, String description,
                      String imageUrl, String discount, Category category, String calories,
                      String fat, String protein, String weight, String size, String volume, String brands) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        Description = description;
        this.imageUrl = imageUrl;
        this.discount = discount;
        this.category = category;
        this.calories = calories;
        this.fat = fat;
        this.protein = protein;
        this.weight = weight;
        this.size = size;
        this.volume = volume;
        this.brands = brands;
    }
}

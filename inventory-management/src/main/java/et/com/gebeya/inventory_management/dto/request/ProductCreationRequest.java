package et.com.gebeya.inventory_management.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductCreationRequest {
    private String name;
    private Double price;
    private Integer quantity;
    private String description;
    private String imageUrl;
    private String calories;
    private String fat;
    private String protein;
    private String weight;
    private String size;
    private String volume;
    private String brands;
    private Long categoryId;

}

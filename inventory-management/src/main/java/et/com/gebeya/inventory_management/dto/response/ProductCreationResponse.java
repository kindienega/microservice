package et.com.gebeya.inventory_management.dto.response;

import et.com.gebeya.inventory_management.dto.request.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductCreationResponse {
    private Long id;
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
    private CategoryDto category;
    //private Admins admins;
}

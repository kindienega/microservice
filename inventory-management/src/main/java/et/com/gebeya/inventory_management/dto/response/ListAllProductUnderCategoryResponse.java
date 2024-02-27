package et.com.gebeya.inventory_management.dto.response;

import et.com.gebeya.inventory_management.dto.request.ProductDto;
import lombok.Data;

import java.util.List;
@Data
public class ListAllProductUnderCategoryResponse {
    private List<ProductDto> products;
}

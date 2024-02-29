package et.com.gebeya.inventory_management.dto.response;

import et.com.gebeya.inventory_management.dto.request.ProductDto;
import lombok.Data;

import java.util.List;

@Data
public class ListOfAllProductUnderAdminResponse {
    private List<ProductDto> products;
}

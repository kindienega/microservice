package et.com.gebeya.inventory_management.dto.request;

import lombok.Data;

@Data
public class CategoryRegistrationRequest {
    private String name;
    private String Tittle;;
    private String metaTittle;
    private String description;
    private String imageUrl;
}

package et.com.gebeya.inventory_management.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuantityResponse {
    private int totalQuantity;

    public QuantityResponse(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
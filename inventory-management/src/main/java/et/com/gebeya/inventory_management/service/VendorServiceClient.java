package et.com.gebeya.inventory_management.service;

import et.com.gebeya.inventory_management.dto.VendorDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class VendorServiceClient {
    private final WebClient webClient;

    public VendorServiceClient( @Qualifier("vendorWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public VendorDto getVendorById(Long vendorId) {
        return this.webClient.get()
                .uri("/api/v1/user/vendor/get/{vendorId}", vendorId)
                .retrieve()
                .bodyToMono(VendorDto.class)
                .block();
    }
}

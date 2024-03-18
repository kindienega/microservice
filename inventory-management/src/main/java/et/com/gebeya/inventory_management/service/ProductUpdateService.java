package et.com.gebeya.inventory_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductUpdateService {

//    private UpdateRequestRepository vendorRequestRepository;
//    private final VendorRepository vendorRepository;
//    private ProductRepository productRepository;
//
//
//    public void processUpdateRequest(VendorProductUpdateRequestDto requestDto) {
//        if (requestDto.getStatus() == Status.APPROVED) {
//            Vendor vendor = vendorRepository.findById(requestDto.getVendorId())
//                    .orElseThrow(() -> new EntityNotFoundException("Vendor not found with id: " + requestDto.getVendorId()));
//
//            Product product = productRepository.findByNameAndVendorId(requestDto.getNameOfProduct(), vendor.getId())
//                    .orElseThrow(() -> new EntityNotFoundException("Product not found with name: " + requestDto.getNameOfProduct() + " for given vendor"));
//
//            int newQuantity = product.getQuantity() + requestDto.getVendorQuantity();
//            product.setQuantity(newQuantity);
//            productRepository.save(product);
//        }
//    }
}

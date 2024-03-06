package et.com.gebeya.inventory_management.vendorProduct.servicess;

import et.com.gebeya.inventory_management.Models.Product;
import et.com.gebeya.inventory_management.repos.ProductRepository;
import et.com.gebeya.inventory_management.vendor.Vendor;
import et.com.gebeya.inventory_management.vendor.VendorRepository;
import et.com.gebeya.inventory_management.vendorProduct.Status;
import et.com.gebeya.inventory_management.vendorProduct.VendorProduct;
import et.com.gebeya.inventory_management.vendorProduct.VendorProductRepository;
import et.com.gebeya.inventory_management.vendorProduct.dto.ProductDto;
import et.com.gebeya.inventory_management.vendorProduct.dto.VendorDto;
import et.com.gebeya.inventory_management.vendorProduct.request.ProductVendorCreationRequest;
import et.com.gebeya.inventory_management.vendorProduct.response.VendorProductCreationsResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VendorProductService {

    private VendorProductRepository vendorProductRepository;
    private ProductRepository productRepository;
    private VendorRepository vendorRepository;
    @Transactional
    public VendorProductCreationsResponse createVendorProduct(ProductVendorCreationRequest request) {
        Vendor vendor = vendorRepository.findById(request.getVendorId()).orElseThrow(() -> new RuntimeException("Vendor not found"));
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));

        VendorProduct vendorProduct = new VendorProduct();
        vendorProduct.setVendor(vendor);
        vendorProduct.setProduct(product);
        vendorProduct.setQuantity(request.getQuantity());
        vendorProduct.setPrice(request.getPrice());
        vendorProduct.setStatus(Status.PENDING);
        vendorProductRepository.save(vendorProduct);
        ProductDto productDto = new ProductDto(product);
        VendorDto vendorDto = new VendorDto(vendor);

        return new VendorProductCreationsResponse(productDto, vendorDto,
                request.getQuantity(), request.getPrice(), Status.PENDING);
    }

    @Transactional
    public VendorProduct updateStatus(Long vendorProductId, Status status) {
        VendorProduct vendorProduct = vendorProductRepository.findById(vendorProductId)
                .orElseThrow(() -> new EntityNotFoundException("VendorProduct not found with id " + vendorProductId));
        vendorProduct.setStatus(status);
        if (status == Status.APPROVED) {
            Product product = vendorProduct.getProduct();
            product.setQuantity(product.getQuantity() + vendorProduct.getQuantity());
            productRepository.save(product);
        }
        return vendorProductRepository.save(vendorProduct);
    }
}

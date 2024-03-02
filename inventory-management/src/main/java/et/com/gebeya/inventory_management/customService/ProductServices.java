package et.com.gebeya.inventory_management.customService;
//
//import et.com.gebeya.inventory_management.Models.Category;
//import et.com.gebeya.inventory_management.Models.Product;
//import et.com.gebeya.inventory_management.dto.request.ProductCreationRequest;
//import et.com.gebeya.inventory_management.dto.request.ProductDto;
//import et.com.gebeya.inventory_management.dto.response.ListAllProductUnderCategoryResponse;
//import et.com.gebeya.inventory_management.dto.response.ProductCreationResponse;
//import et.com.gebeya.inventory_management.repos.CategoryRepository;
//import et.com.gebeya.inventory_management.repos.ProductRepository;
//import et.com.gebeya.inventory_management.utility.CustomMappingFunctions;
//import jakarta.persistence.EntityNotFoundException;
//import jakarta.transaction.Transactional;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//import java.util.stream.Collectors;
//@AllArgsConstructor
//@Service
//public class ProductServices {
//    private final CategoryRepository categoryRepository;
//    private final ProductRepository productRepository;
//    private final CustomMappingFunctions customMappingFunctions;
//    //private final AdminRepository adminRepository;
//
//    public ListAllProductUnderCategoryResponse listAllProductsUnderCategory(Long categoryId) {
//        Category category = categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
//        List<Product> products = productRepository.findAllByCategory(category);
//        List<ProductDto> productDtos = products.stream()
//                .map(product -> new ProductDto(product.getId(),
//                        product.getName(),
//                        product.getPrice(),
//                        product.getQuantity(),
//                        product.getDescription()))
//                .collect(Collectors.toList());
//        ListAllProductUnderCategoryResponse response = new ListAllProductUnderCategoryResponse();
//        response.setProducts(productDtos);
//
//        return response;
//    }
//    public ListOfAllProductUnderAdminResponse listAllProductsUnderAdmin(Long adminId) {
//        Admins admins = adminRepository.findById(adminId)
//                .orElseThrow(() -> new RuntimeException("Admin not found with ID: " + adminId));
//        List<Product> products = productRepository.findAllByAdmin(admins);
//        List<ProductDto> productDtos = products.stream()
//                .map(product -> new ProductDto(product.getId(),
//                        product.getName(),
//                        product.getPrice(),
//                        product.getQuantity(),
//                        product.getDescription()))
//                .collect(Collectors.toList());
//        ListOfAllProductUnderAdminResponse response = new ListOfAllProductUnderAdminResponse();
//        response.setProducts(productDtos);
//
//        return response;
//    }
//
//    public int getTotalQuantity(Long productId) {
//        return productRepository.findById(productId)
//                .map(Product::getQuantity)
//                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));
//    }
//    @Transactional
//    public Product restockProduct(Long productId, int quantityToAdd) {
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));
//        product.setQuantity(product.getQuantity() + quantityToAdd);
//        return productRepository.save(product);
//    }
//    @Transactional
//    public Product decreaseStock(Long productId, int quantityToDecrease) {
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));
//        int newQuantity = product.getQuantity() - quantityToDecrease;
//
//        if (newQuantity < 0) {
//            throw new IllegalArgumentException("Insufficient stock for product ID: " + productId);
//        }
//
//        product.setQuantity(newQuantity);
//        return productRepository.save(product);
//    }
//
//    public ProductCreationResponse createProduct(ProductCreationRequest request, MultipartFile imageFile) {
//        return customMappingFunctions.createProductAndConvertToResponse(request, imageFile);
//
//    }
//}

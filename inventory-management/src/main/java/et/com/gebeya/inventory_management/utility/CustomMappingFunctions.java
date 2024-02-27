package et.com.gebeya.inventory_management.utility;

import et.com.gebeya.inventory_management.Models.Category;
import et.com.gebeya.inventory_management.Models.Product;
import et.com.gebeya.inventory_management.dto.request.CategoryDto;
import et.com.gebeya.inventory_management.dto.request.ProductCreationRequest;
import et.com.gebeya.inventory_management.dto.response.ProductCreationResponse;
import et.com.gebeya.inventory_management.cloudinary.ImageModel;
import et.com.gebeya.inventory_management.cloudinary.ImageServiceImpl;
import et.com.gebeya.inventory_management.repos.CategoryRepository;
import et.com.gebeya.inventory_management.repos.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Component
public class CustomMappingFunctions {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageServiceImpl imageService;

    public CustomMappingFunctions(ProductRepository productRepository,
                                  CategoryRepository categoryRepository,
                                  ImageServiceImpl imageService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.imageService = imageService;
    }
    //private final VendorRepository vendorRepository;


    public ProductCreationResponse createProductAndConvertToResponse(ProductCreationRequest request, MultipartFile imageFile) {
 //       Vendor vendor = vendorRepository.findById(request.getVendorId())
  //              .orElseThrow(()->new RuntimeException("vendor with vendor id :" + request.getVendorId()));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + request.getCategoryId()));

        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setDescription(request.getDescription());
        product.setCalories(request.getCalories());
        product.setFat(request.getFat());
        product.setProtein(request.getProtein());
        product.setWeight(request.getWeight());
        product.setSize(request.getSize());
        product.setVolume(request.getVolume());
        product.setBrands(request.getBrands());
        product.setCategory(category);
        ResponseEntity<Map> imageResponse = imageService.uploadImage(new ImageModel(imageFile.getOriginalFilename(), imageFile));
        if (imageResponse.getStatusCode() == HttpStatus.OK) {
            String imageUrl = (String) imageResponse.getBody().get("url");
            product.setImageUrl(imageUrl);
        } else {
            throw new RuntimeException("Image upload failed");
        }
       // product.setVendor(vendor);

        Product savedProduct = productRepository.save(product);

        return convertProductToProductCreationResponse(savedProduct);
    }

    private ProductCreationResponse convertProductToProductCreationResponse(Product product) {
        ProductCreationResponse response = new ProductCreationResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setQuantity(product.getQuantity());
        response.setDescription(product.getDescription());
        response.setImageUrl(product.getImageUrl());
        response.setCalories(product.getCalories());
        response.setFat(product.getFat());
        response.setProtein(product.getProtein());
        response.setWeight(product.getWeight());
        response.setSize(product.getSize());
        response.setVolume(product.getVolume());
        response.setBrands(product.getBrands());
        response.setCategory(new CategoryDto(product.getCategory().getId(), product.getCategory().getName()));

        return response;
    }
}


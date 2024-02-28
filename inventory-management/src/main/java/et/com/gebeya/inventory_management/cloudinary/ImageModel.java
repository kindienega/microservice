package et.com.gebeya.inventory_management.cloudinary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageModel {
    private String name;
    private MultipartFile file;
}

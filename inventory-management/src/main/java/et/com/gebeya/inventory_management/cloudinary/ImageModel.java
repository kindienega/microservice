package et.com.gebeya.inventory_management.cloudinary;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImageModel {
    private String name;
    private MultipartFile file;
}

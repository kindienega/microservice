package et.com.gebeya.inventory_management.service.imp;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@AllArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;


    public String uploadFile(MultipartFile file) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, String> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return result.get("url");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
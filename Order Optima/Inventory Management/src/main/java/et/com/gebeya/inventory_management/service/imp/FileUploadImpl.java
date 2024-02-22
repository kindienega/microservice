package et.com.gebeya.inventory_management.service.imp;

//import et.com.gebeya.inventory_management.config.Cloudinary;
import com.cloudinary.Cloudinary;
//import et.com.gebeya.inventory_management.config.Cloudinary;
import et.com.gebeya.inventory_management.service.listOfMethods.FileUpload;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class FileUploadImpl implements FileUpload{
    private final Cloudinary cloudinary;

    public FileUploadImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        return cloudinary.uploader()
                .upload(multipartFile.getBytes(),
                        Map.of("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();
    }
}

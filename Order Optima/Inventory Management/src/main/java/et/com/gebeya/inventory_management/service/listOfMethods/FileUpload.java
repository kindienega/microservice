package et.com.gebeya.inventory_management.service.listOfMethods;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Component
public interface FileUpload {
    String uploadFile(MultipartFile multipartFile) throws IOException;

}

package et.com.gebeya.inventory_management.vendor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public Vendor createVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    public Optional<Vendor> getVendorById(Long id) {
        return vendorRepository.findById(id);
    }

    public Vendor updateVendor(Long id, Vendor vendorDetails) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found with id " + id));
        vendor.setName(vendorDetails.getName());
        vendor.setDescription(vendorDetails.getDescription());
        vendor.setContactEmail(vendorDetails.getContactEmail());
        vendor.setPhoneNumber(vendorDetails.getPhoneNumber());
        return vendorRepository.save(vendor);
    }

    public void deleteVendor(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found with id " + id));
        vendorRepository.delete(vendor);
    }
}

package et.com.gebeya.user_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass

public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "created_on")
    private Instant createdOn;

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "updated_on")
    private Instant updatedOn;

    @JsonIgnore
    @Column(name = "is_active")

    private Boolean isActive;
}
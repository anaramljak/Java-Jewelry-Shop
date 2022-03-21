package app.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
@Setter
public class ProductDTO {
    private UUID id;
    private String name;
    private String description;
    private double price;
    private UUID categoryId;
    private String imageName;
}

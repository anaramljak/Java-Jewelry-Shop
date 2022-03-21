package app.DTO;

import app.Persistance.Entities.Category;
import app.Persistance.Entities.Product;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Data
@Getter
@Setter
public class PersonalizedJewelryDTO {
    private UUID id;
    private String text;
    private Double price;
    private Boolean withCristals;
    private Product product;
    private String imageName;
    private String material;
}

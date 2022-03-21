package app.Persistance.Entities;

import javax.persistence.*;
import java.util.*;
import lombok.*;
import org.hibernate.annotations.Type;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(type = "uuid-char")
	private UUID id;
	private String name;
	private String description;
	private Double price;
	private String imageName;
	
	@OneToMany (mappedBy = "product", cascade = CascadeType.ALL)
	private Set<CartProduct> cartProducts;

	@OneToMany
	private Set<OrderProduct> orderProducts;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

}

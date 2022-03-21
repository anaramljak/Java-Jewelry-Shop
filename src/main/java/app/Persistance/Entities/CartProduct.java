package app.Persistance.Entities;

import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CartProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(type = "uuid-char")
	private UUID id;
	private int qty;
	private Double total;

	@ManyToOne
	@JoinColumn(name="cart_id")
	private Cart cart;

	@OneToOne
	private Product product;

}

package app.Persistance.Entities;

import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(type = "uuid-char")
	private UUID id;
	private Double total;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;

	@OneToMany (mappedBy = "cart")
	@JsonIgnore
	private List<CartProduct> cartProducts;

	@OneToMany (mappedBy = "cart")
	@JsonIgnore
	private List<CartPers> cartPers;



}

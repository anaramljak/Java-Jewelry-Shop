package app.Persistance.Entities;

import javax.persistence.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(type = "uuid-char")
	private UUID id;
	private Date orderDate;
	private double total;


	@OneToMany (mappedBy = "orders")
	@JsonIgnore
	private List<OrderProduct> orderProducts;

	@OneToMany (mappedBy = "orders")
	@JsonIgnore
	private List<OrderPers> orderPers;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;


	
}

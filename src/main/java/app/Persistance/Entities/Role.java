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
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(type = "uuid-char")
	private UUID id;
	private String name;

	public Role(String name){
		super();
		this.name=name;
	}

}

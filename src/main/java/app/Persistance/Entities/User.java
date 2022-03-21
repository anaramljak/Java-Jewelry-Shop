package app.Persistance.Entities;

import java.util.*;
import javax.persistence.*;

import app.Validators.ValidEmail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Getter
@Setter
public class User {
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	@Type(type = "uuid-char")
	private UUID id;
	private String name;
	private String surname;

	@NotEmpty(message = "Email may not be empty")
	@Email(message= "{errors.invalid_email}")
	private String email;

	@NotEmpty(message = "Password may not be empty")
	private String password;

	@OneToOne(mappedBy = "user")
	private Cart cart;

	@OneToMany(mappedBy = "user",orphanRemoval = true,
			cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Orders> orders;

	@OneToOne(mappedBy = "user")
	private Address address;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
			name="user_role",
			joinColumns = @JoinColumn(name= "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
	)
	private Collection<Role> roles;

	public User(String name, String surname, String email, String password, Collection<Role> roles)
	{
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}

}

package app.Persistance.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table

public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String addressLine;
    private String city;
    private String postalCode;


    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


}

package app.Persistance.Repositories;

import app.Persistance.Entities.PersonalizedJewelry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonalizedJewelryRepository extends JpaRepository<PersonalizedJewelry, UUID>{

}

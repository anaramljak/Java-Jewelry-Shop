package app.Persistance.Repositories;


import app.Persistance.Entities.Address;
import app.Persistance.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    public Address findByUser(User user);
}

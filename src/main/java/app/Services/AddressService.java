package app.Services;

import app.Persistance.Entities.Address;
import app.Persistance.Entities.User;
import app.Persistance.Repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {
    @Autowired
    private AddressRepository repository;

    public void save(Address address){
        repository.save(address);
    }
    public void delete(UUID id,UUID userId){
        repository.deleteById(id);
    }

    public Address getAddress(User user) {
        return repository.findByUser(user);
    }
    public Optional<Address> get(UUID id){
        return repository.findById(id);
    }


}

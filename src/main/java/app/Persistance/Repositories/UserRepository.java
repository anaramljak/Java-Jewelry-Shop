package app.Persistance.Repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import app.Persistance.Entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>{
        User findByEmail(String email);
}

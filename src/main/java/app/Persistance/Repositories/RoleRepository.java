package app.Persistance.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import app.Persistance.Entities.Role;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID>{

}
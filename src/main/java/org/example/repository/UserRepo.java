package org.example.repository;

import lombok.NonNull;
import org.example.authentication.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);

}

package org.kamogelofoundations.repository;
 
import org.kamogelofoundations.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<User, Long> {


}

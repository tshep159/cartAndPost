package org.kamogelofoundations.repository;

import org.kamogelofoundations.dto.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByRole(String role);
}

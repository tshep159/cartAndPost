package org.kamogelofoundations.service;

import java.util.Optional;
import org.kamogelofoundations.dto.User;

public interface AdminService {

    public Iterable<User> listUsers();

    Optional<User> getByUsername(String username);

    User getUserById(Long id);

    public void saveUser(User user);

    public void updateUser(User user);

    long TotalUsers();

    public User get(Long id);

    public void listWinners();
}
